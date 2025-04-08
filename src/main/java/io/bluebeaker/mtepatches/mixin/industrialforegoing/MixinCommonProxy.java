package io.bluebeaker.mtepatches.mixin.industrialforegoing;

import com.buuz135.industrial.proxy.CommonProxy;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Mixin(value = CommonProxy.class,remap = false)
public class MixinCommonProxy {
    @Redirect(method = "readUrl(Ljava/lang/String;)Ljava/lang/String;",at = @At(value = "INVOKE", target = "Ljava/net/URL;openStream()Ljava/io/InputStream;"))
    private static InputStream addTimeout(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        if(MTEPatchesConfig.connectionTimeout.industrialForegoing){
            Utils.setTimeout(urlConnection);
        }
        return urlConnection.getInputStream();
    }
}

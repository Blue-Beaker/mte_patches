package io.bluebeaker.mtepatches.mixin.biomesoplenty;

import biomesoplenty.common.remote.TrailManager;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.net.HttpURLConnection;

@Mixin(value = TrailManager.class,remap = false)
public abstract class MixinTrailManager {
    @ModifyVariable(method = "retrieveTrails()V",at = @At("STORE"),ordinal = 0)
    private static HttpURLConnection addTimeout(HttpURLConnection connection){
        if(MTEPatchesConfig.connectionTimeout.bop){
            Utils.setTimeout(connection);
        }
        return connection;
    }
}

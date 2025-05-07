package io.bluebeaker.mtepatches.mixin.ic2;

import ic2.core.Platform;
import ic2.core.PlatformClient;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlatformClient.class,remap = false)
public abstract class MixinPlatformClient extends Platform {
    @Inject(method = "messagePlayer(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;[Ljava/lang/Object;)V",at = @At("HEAD"),cancellable = true)
    public void messagePlayer(EntityPlayer player, String message, Object[] args, CallbackInfo ci){
        if(!MTEPatchesConfig.ic2.lanMessageFix) return;
        TextComponentTranslation textComponentTranslation;
        if (args.length > 0) {
            textComponentTranslation = new TextComponentTranslation(message, (Object[])(this.getMessageComponents(args)));
        } else {
            textComponentTranslation = new TextComponentTranslation(message);
        }
        ((EntityPlayerMP)player).sendMessage(textComponentTranslation);
        ci.cancel();
    }
}

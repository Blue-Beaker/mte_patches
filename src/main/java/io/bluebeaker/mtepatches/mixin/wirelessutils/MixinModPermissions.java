package io.bluebeaker.mtepatches.mixin.wirelessutils;

import com.lordmau5.wirelessutils.utils.mod.ModPermissions;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModPermissions.class)
public abstract class MixinModPermissions {
    @Inject(method = "hasPermission(Lnet/minecraft/command/ICommandSender;)Z",at = @At("HEAD"),cancellable = true,remap = false)
    public void checkPermission(ICommandSender sender, CallbackInfoReturnable<Boolean> cir){
        if (sender instanceof EntityPlayerMP) return;

        if (!MTEPatchesConfig.wirelessUtils.fixCommandPermissionCheck) return;
        cir.setReturnValue(false);
    }
}

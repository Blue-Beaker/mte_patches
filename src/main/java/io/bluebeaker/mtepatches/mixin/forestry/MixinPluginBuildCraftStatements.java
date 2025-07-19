package io.bluebeaker.mtepatches.mixin.forestry;

import forestry.core.config.Constants;
import forestry.core.utils.ModUtil;
import forestry.plugins.PluginBuildCraftStatements;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PluginBuildCraftStatements.class,remap = false)
public abstract class MixinPluginBuildCraftStatements {
    @Inject(method = "isAvailable",at = @At("HEAD"),cancellable = true)
    public void redirectBCVersionCheck(CallbackInfoReturnable<Boolean> cir){
        if(MTEPatchesConfig.forestry.bc8Compat){
            cir.setReturnValue(ModUtil.isModLoaded(Constants.BCLIB_MOD_ID));
        }
    }
}

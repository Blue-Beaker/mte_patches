package io.bluebeaker.mtepatches.mixin.ic2cropplugin;

import crops.gui.BreedingContainer;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BreedingContainer.class)
public abstract class MixinBreedingContainer extends Container {
    @Inject(method = "<init>",at = @At("RETURN"))
    public void setWindowID(CallbackInfo ci){
        if(!MTEPatchesConfig.categoryIC2CropCalc.fixCalcWindowID) return;
       this.windowId=-2147483648;
    }
}

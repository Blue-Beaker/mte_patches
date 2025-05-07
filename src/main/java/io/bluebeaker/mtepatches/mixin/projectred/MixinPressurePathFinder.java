package io.bluebeaker.mtepatches.mixin.projectred;

import codechicken.multipart.TMultiPart;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.projectred.Utils;
import mrtjp.projectred.transportation.PressurePathFinder$;
import mrtjp.projectred.transportation.TPressureTube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = PressurePathFinder$.class,remap = false)
public abstract class MixinPressurePathFinder {
    @ModifyVariable(method = "iterate",at = @At(value = "STORE"),ordinal = 0)
    private TMultiPart iterate(TMultiPart multiPart){
        if(MTEPatchesConfig.projectred.pressureTubeOverflowLimit>0 && multiPart instanceof TPressureTube){
            TPressureTube tube = (TPressureTube) multiPart;
            if(Utils.countWanderingItems(tube) >= MTEPatchesConfig.projectred.pressureTubeOverflowLimit) return null;
        }
        return multiPart;
    }
}

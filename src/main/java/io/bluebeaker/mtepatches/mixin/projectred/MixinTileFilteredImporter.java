package io.bluebeaker.mtepatches.mixin.projectred;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mrtjp.projectred.expansion.TileFilteredImporter;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileFilteredImporter.class)
public abstract class MixinTileFilteredImporter {
    //Fix slots for filtered importer
    @Inject(method = "getSlotsForFace(Lnet/minecraft/util/EnumFacing;)[I",at = @At("RETURN"),remap = true, cancellable = true)
    public void getSlotsForFace(EnumFacing s, CallbackInfoReturnable<int[]> cir){
        if(!MTEPatchesConfig.projectred.fixMachineInventorySizes) return;
        int[] returnValue = cir.getReturnValue();
        if(returnValue.length>0) {
            cir.setReturnValue(new int[]{0,1,2,3,4,5,6,7,8});
        }
//        MTEPatchesMod.getLogger().info("original value {}, new value {}",returnValue,cir.getReturnValue());
    }
}

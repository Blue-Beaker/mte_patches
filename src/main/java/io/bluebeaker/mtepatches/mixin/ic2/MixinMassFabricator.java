package io.bluebeaker.mtepatches.mixin.ic2;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;

import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.tileentity.TileEntityMassFabricator;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.ItemName;

@Mixin(value = TileEntityMassFabricator.class,remap = false)
public abstract class MixinMassFabricator {
    @ModifyVariable(remap = false, method = "updateEntityServer()V", at = @At(value = "LOAD"),ordinal = 0)
    private double ifScrapFull(double scrapConversion) {
        if (MTEPatchesConfig.ic2.massFabStuckFix) {
            if(scrapConversion<=0
                    &&(this.consumedScrap >= REQUIRED_SCRAP
                    && this.outputSlot.canAdd(ItemName.misc_resource.getItemStack(MiscResourceType.matter)))){
                return Double.MIN_VALUE;
            }
        }
        return scrapConversion;
    }

    @Shadow
    public int consumedScrap;

    @Shadow
    @Final
    private static int REQUIRED_SCRAP;

    @Shadow
    @Final
    public InvSlotOutput outputSlot;
}

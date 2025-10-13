package io.bluebeaker.mtepatches.mixin.ic2.generators;

import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TileEntitySolarGenerator.class,remap = false)
public abstract class MixinSolarGenerator {
    @Shadow @Final private static double energyMultiplier;

    @ModifyConstant(method = "<init>",constant = @Constant(intValue = 2))
    private static int increaseStorageIfNeeded(int constant){
        if(MTEPatchesConfig.ic2.scaleGeneratorBuffers)
            return (int)Math.ceil(energyMultiplier*constant);
        return constant;
    }
}

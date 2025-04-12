package io.bluebeaker.mtepatches.mixin.projectred;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.multipart.TMultiPart;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.projectred.FakeBlocks;
import mrtjp.projectred.transportation.SubcorePipePart;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SubcorePipePart.class,remap = false)
public abstract class MixinPipePart extends TMultiPart {
    @Inject(method = "getStrength",at = @At("HEAD"),cancellable = true)
    public void getStrength(EntityPlayer player, CuboidRayTraceResult hit, CallbackInfoReturnable<Float> cir){
        if(!MTEPatchesConfig.projectred.fixMiningSpeed) return;
        cir.setReturnValue(FakeBlocks.getStrength(FakeBlocks.PIPE.getDefaultState(),player, this.world()));
    }
}

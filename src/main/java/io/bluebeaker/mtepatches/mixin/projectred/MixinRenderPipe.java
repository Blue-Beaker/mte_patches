package io.bluebeaker.mtepatches.mixin.projectred;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Vector3;
import io.bluebeaker.mtepatches.render.RenderUtils;
import mrtjp.projectred.transportation.PayloadPipePart;
import mrtjp.projectred.transportation.RenderPipe;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderPipe.class,remap = false)
public abstract class MixinRenderPipe {
    @Inject(method = "renderItemFlow",at = @At("HEAD"),cancellable = true)
    private static void skipRenderContentsWhenFar(PayloadPipePart par1, Vector3 par2, float par3, CCRenderState par4, CallbackInfo ci){
        BlockPos pos = par1.pos();
        if(!render.projectred) return;
        if(RenderUtils.isOutOfRenderDistance(pos, render.renderDistance)) ci.cancel();
    }
}

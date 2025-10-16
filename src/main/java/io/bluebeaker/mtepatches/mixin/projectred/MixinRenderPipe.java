package io.bluebeaker.mtepatches.mixin.projectred;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Vector3;
import io.bluebeaker.mtepatches.render.RenderUtils;
import io.bluebeaker.mtepatches.render.ShadersAccessor;
import mrtjp.projectred.transportation.PayloadPipePart;
import mrtjp.projectred.transportation.RenderPipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderPipe.class,remap = false)
public abstract class MixinRenderPipe {
    @Inject(method = "renderItemFlow",at = @At("HEAD"),cancellable = true)
    private static void skipRenderContentsWhenFar(PayloadPipePart pipe, Vector3 par2, float par3, CCRenderState par4, CallbackInfo ci){
        if(!render.enableRenderCulling) return;
        if(render.shadowCulling.projectred && ShadersAccessor.getIsRenderingShadowPass()) ci.cancel();
        if(render.farCulling.projectred && RenderUtils.isOutOfRenderDistance(
                pipe.tile(), render.cullingDistance)) ci.cancel();
    }
}

package io.bluebeaker.mtepatches.mixin.thermaldynamics;

import cofh.thermaldynamics.duct.tiles.TileDuctFluid;
import cofh.thermaldynamics.render.RenderDuctFluids;
import io.bluebeaker.mtepatches.render.RenderUtils;
import io.bluebeaker.mtepatches.render.ShadersAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderDuctFluids.class,remap = false)
public abstract class MixinRenderDuctFluids {
    @Inject(method = "render",at = @At("HEAD"),cancellable = true)
    private void skipRenderWhenFar(TileDuctFluid tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci){
        if(render.shadowCulling.thermaldynamics && ShadersAccessor.getIsRenderingShadowPass()) ci.cancel();
        if(render.farCulling.thermaldynamics && RenderUtils.isOutOfRenderDistance(tile, render.cullingDistance)) ci.cancel();
    }
}

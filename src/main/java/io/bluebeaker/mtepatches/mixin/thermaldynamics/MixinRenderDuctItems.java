package io.bluebeaker.mtepatches.mixin.thermaldynamics;

import cofh.thermaldynamics.duct.tiles.TileGrid;
import cofh.thermaldynamics.render.RenderDuctItems;
import io.bluebeaker.mtepatches.render.RenderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderDuctItems.class,remap = false)
public abstract class MixinRenderDuctItems {
    @Inject(method = "render",at = @At("HEAD"),cancellable = true)
    private void skipRenderWhenFar(TileGrid tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci){
        if(!render.thermaldynamics) return;
        if(RenderUtils.isOutOfRenderDistance(tile, render.renderDistance)) ci.cancel();
    }
}

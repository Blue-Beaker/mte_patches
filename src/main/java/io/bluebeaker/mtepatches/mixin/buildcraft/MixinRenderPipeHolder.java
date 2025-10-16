package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.transport.client.render.RenderPipeHolder;
import buildcraft.transport.tile.TilePipeHolder;
import io.bluebeaker.mtepatches.render.RenderUtils;
import io.bluebeaker.mtepatches.render.ShadersAccessor;
import net.minecraft.client.renderer.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderPipeHolder.class,remap = false)
public abstract class MixinRenderPipeHolder {

    // Skip rendering items/fluid/energy flow in pipes far away
    @Inject(method = "renderContents",at = @At("HEAD"),cancellable = true)
    private static void skipRenderContentsWhenFar(TilePipeHolder pipe, double x, double y, double z, float partialTicks, BufferBuilder bb, CallbackInfo ci){
        if(render.shadowCulling.buildcraft && ShadersAccessor.getIsRenderingShadowPass()) ci.cancel();
        if(render.farCulling.buildcraft && RenderUtils.isOutOfRenderDistance(pipe, render.cullingDistance)) ci.cancel();
    }
}

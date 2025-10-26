package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.api.transport.pipe.PipeFlow;
import buildcraft.transport.client.render.RenderPipeHolder;
import buildcraft.transport.pipe.Pipe;
import buildcraft.transport.pipe.flow.PipeFlowFluids;
import buildcraft.transport.pipe.flow.PipeFlowItems;
import buildcraft.transport.tile.TilePipeHolder;
import com.llamalad7.mixinextras.sugar.Local;
import io.bluebeaker.mtepatches.render.RenderUtils;
import io.bluebeaker.mtepatches.render.ShadersAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderPipeHolder.class,remap = false)
public abstract class MixinRenderPipeHolder {

    // Skip rendering items/fluid flow in pipes far away
    @Redirect(method = "renderContents",at = @At(value = "FIELD", target = "Lbuildcraft/transport/pipe/Pipe;flow:Lbuildcraft/api/transport/pipe/PipeFlow;"))
    private static PipeFlow skipItemAndFluidRendering(Pipe instance, @Local(argsOnly = true) TilePipeHolder pipeHolder){
        PipeFlow flow = instance.flow;

        if(render.enableRenderCulling && flow instanceof PipeFlowItems || flow instanceof PipeFlowFluids){
            if (render.shadowCulling.buildcraft && ShadersAccessor.getIsRenderingShadowPass()) {
                return null;
            } else if (render.farCulling.buildcraft && RenderUtils.isOutOfRenderDistance(pipeHolder)) {
                return null;
            }
        }
        return instance.flow;
    }
}

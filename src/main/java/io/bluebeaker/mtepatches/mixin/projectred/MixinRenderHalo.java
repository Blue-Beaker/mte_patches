package io.bluebeaker.mtepatches.mixin.projectred;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Transformation;
import com.llamalad7.mixinextras.sugar.Local;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.render.ShadersAccessor;
import io.bluebeaker.mtepatches.render.WhiteColorUV;
import mrtjp.projectred.core.RenderHalo$;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderHalo$.class,remap = false)
public class MixinRenderHalo {

    @Redirect(method = "renderHalo(Lcodechicken/lib/vec/Cuboid6;ILcodechicken/lib/vec/Transformation;)V",at = @At(value = "INVOKE", target = "Lcodechicken/lib/render/CCRenderState;setPipeline([Lcodechicken/lib/render/pipeline/IVertexOperation;)V"))
    public void setDrawMode(CCRenderState instance, IVertexOperation[] ops, @Local(argsOnly = true) Transformation transformation){

        if(MTEPatchesConfig.projectred.lampHaloShaders && ShadersAccessor.getIsShadersOn()) {
            instance.setPipeline(transformation, new WhiteColorUV());
            return;
        }
        instance.setPipeline(transformation);
    }
}

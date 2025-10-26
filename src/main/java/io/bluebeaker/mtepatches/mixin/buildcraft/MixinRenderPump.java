package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.factory.client.render.RenderPump;
import buildcraft.factory.tile.TilePump;
import com.llamalad7.mixinextras.sugar.Local;
import io.bluebeaker.mtepatches.render.RenderUtils;
import io.bluebeaker.mtepatches.render.ShadersAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderPump.class,remap = false)
public abstract class MixinRenderPump {

    @ModifyConstant(method = "renderTileEntityFast(Lbuildcraft/factory/tile/TilePump;DDDFIFLnet/minecraft/client/renderer/BufferBuilder;)V",constant = @Constant(intValue = 4))
    public int pumpRenderCulling(int constant, @Local(argsOnly = true) TilePump tile){
        if(!render.enableRenderCulling) return constant;
        if((render.shadowCulling.buildcraft && ShadersAccessor.getIsRenderingShadowPass()) || (render.farCulling.buildcraft && RenderUtils.isOutOfRenderDistance(tile))){
            // Set loop count to 0 to skip rendering lights
            return 0;
        }
        return constant;
    }
}

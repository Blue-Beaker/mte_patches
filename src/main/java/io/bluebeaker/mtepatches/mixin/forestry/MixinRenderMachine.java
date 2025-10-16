package io.bluebeaker.mtepatches.mixin.forestry;

import com.llamalad7.mixinextras.sugar.Local;
import forestry.core.render.RenderMachine;
import forestry.core.render.TankRenderInfo;
import forestry.core.tiles.TileBase;
import io.bluebeaker.mtepatches.render.RenderUtils;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(value = RenderMachine.class,remap = false)
public abstract class MixinRenderMachine {
    @Shadow protected abstract void render(TankRenderInfo resourceTankInfo, TankRenderInfo productTankInfo, EnumFacing orientation, double x, double y, double z, int destroyStage);

    // Skip rendering fluids in machine far away
    @Inject(method = "render(Lforestry/core/tiles/TileBase;DDDFIF)V",at = @At(value = "INVOKE", target = "Lforestry/core/render/RenderMachine;render(Lforestry/core/render/TankRenderInfo;Lforestry/core/render/TankRenderInfo;Lnet/minecraft/util/EnumFacing;DDDI)V",ordinal = 0),cancellable = true)
    private void skipRenderContentsWhenFar(TileBase tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci, @Local EnumFacing facing){
        if(!render.buildcraft) return;
        if(RenderUtils.isOutOfRenderDistance(tile, render.renderDistance)){
            render(TankRenderInfo.EMPTY,TankRenderInfo.EMPTY,facing,x,y,z,destroyStage);
            ci.cancel();
        }
    }
}

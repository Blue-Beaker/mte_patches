package io.bluebeaker.mtepatches.mixin_core;

import com.llamalad7.mixinextras.sugar.Local;
import io.bluebeaker.mtepatches.render.RenderSkipRegistry;
import io.bluebeaker.mtepatches.render.RenderUtils;
import io.bluebeaker.mtepatches.render.ShadersAccessor;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

@Mixin(TileEntityRendererDispatcher.class)
public abstract class MixinTERendererDispatcher {
    @Shadow @Nullable public abstract <T extends TileEntity> TileEntitySpecialRenderer<T> getRenderer(@org.jetbrains.annotations.Nullable TileEntity tileEntityIn);

    // Skip configured generic rendering of tileentities far away
    @Inject(method = "render(Lnet/minecraft/tileentity/TileEntity;DDDFIF)V",at = @At(value = "INVOKE_ASSIGN",target = "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;getRenderer(Lnet/minecraft/tileentity/TileEntity;)Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;"),cancellable = true)
    private void skipRenderContentsWhenFar(TileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage, float p_192854_10_, CallbackInfo ci, @Local TileEntitySpecialRenderer renderer){
        if(!render.enableRenderCulling) return;
        if (renderer != null && RenderUtils.isOutOfRenderDistance(tileEntityIn, render.cullingDistance)
                && (RenderSkipRegistry.INSTANCE.skipFar.contains(renderer.getClass()) || RenderSkipRegistry.INSTANCE.skipFarTiles.contains(tileEntityIn.getClass()))) {
            ci.cancel();
        }
        if (renderer != null && ShadersAccessor.getIsRenderingShadowPass() && (RenderSkipRegistry.INSTANCE.skipShadows.contains(renderer.getClass()) || RenderSkipRegistry.INSTANCE.skipShadowsTiles.contains(tileEntityIn.getClass()))) {
            ci.cancel();
        }
    }
}

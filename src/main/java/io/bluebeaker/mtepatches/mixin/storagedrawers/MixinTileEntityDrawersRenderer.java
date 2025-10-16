package io.bluebeaker.mtepatches.mixin.storagedrawers;

import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import com.jaquadro.minecraft.storagedrawers.client.renderer.TileEntityDrawersRenderer;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityDrawersRenderer.class,remap = false)
public abstract class MixinTileEntityDrawersRenderer {
    // Skip rendering items in drawers when not facing it
    @Inject(method = "render",at = @At(value = "HEAD"),cancellable = true)
    private void skipRenderContentsWhenFar(TileEntityDrawers tile, double x, double y, double z, float partialTickTime, int destroyStage, float par7, CallbackInfo ci){
        if(!MTEPatchesConfig.storageDrawers.cullDrawers) return;
        // Drawer facing vector
        Vec3d dirVector = new Vec3d(EnumFacing.byIndex(tile.getDirection()).getDirectionVec());
        // Drawer -> Player vector
        Vec3d deltaVector = TileEntityRendererDispatcher.instance.entity.getPositionVector().subtract(new Vec3d(tile.getPos()).add(0.5,0.5,0.5));
        // Dot product 2 vectors to determine whether the player can see the items
        double v = deltaVector.dotProduct(dirVector);
        if(v < 0) ci.cancel();
    }
}

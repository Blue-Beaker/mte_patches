package io.bluebeaker.mtepatches.render;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUtils {
    public static boolean isOutOfRenderDistance(BlockPos pos, double distance){
        TileEntityRendererDispatcher instance = TileEntityRendererDispatcher.instance;
        double d0 = pos.getX() + 0.5D - instance.entityX;
        double d1 = pos.getY() + 0.5D - instance.entityY;
        double d2 = pos.getZ() + 0.5D - instance.entityZ;
        return d0 * d0 + d1 * d1 + d2 * d2 > distance * distance;
    }

    public static boolean isOutOfRenderDistance(TileEntity tile){

        return isOutOfRenderDistance(tile, MTEPatchesConfig.render.cullingDistance);
    }

    public static boolean isOutOfRenderDistance(TileEntity tile, double distance){
        TileEntityRendererDispatcher instance = TileEntityRendererDispatcher.instance;
        return instance.world==tile.getWorld()
                && tile.getDistanceSq(instance.entityX, instance.entityY, instance.entityZ) > distance * distance;
    }
}

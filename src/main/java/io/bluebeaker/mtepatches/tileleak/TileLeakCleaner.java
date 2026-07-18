package io.bluebeaker.mtepatches.tileleak;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TileLeakCleaner {
    @SubscribeEvent
    public static void clean(TickEvent.ClientTickEvent event) {
        if(MTEPatchesConfig.tileLeakFix.purgeInterval <=0) return;

        if (event.phase != TickEvent.Phase.END) return;

        World world = Minecraft.getMinecraft().world;
        if (world == null) return;
        if(world.getWorldTime()% MTEPatchesConfig.tileLeakFix.purgeInterval !=0) return;
        world.loadedTileEntityList.removeIf(TileEntity::isInvalid);
    }
}

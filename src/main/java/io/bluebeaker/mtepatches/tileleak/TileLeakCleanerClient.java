package io.bluebeaker.mtepatches.tileleak;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.MTEPatchesMod;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TileLeakCleanerClient {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        clean();
        log();
    }

    private static void clean() {
        if(MTEPatchesConfig.tileLeakFix.purgeInterval <=0) return;

        World world = Minecraft.getMinecraft().world;
        if (world == null) return;
        if(world.getWorldTime()% MTEPatchesConfig.tileLeakFix.purgeInterval !=0) return;

        int origSize = world.loadedTileEntityList.size();
        world.loadedTileEntityList.removeIf(TileEntity::isInvalid);
        int cleaned = origSize - world.loadedTileEntityList.size();

        if(MTEPatchesConfig.tileLeakFix.logs){
            MTEPatchesMod.getLogger().info("Cleaned {} invalid tiles", cleaned);
        }
    }

    public static int logTimer = 0;

    private static void log() {
        if(!MTEPatchesConfig.tileLeakFix.logs) return;

        logTimer++;
        if (logTimer < 100){
            return;
        }

        logTimer = 0;
        int sum = TileLeakCounters.MARKED_COUNTER.sum();

        if(sum>0){
            MTEPatchesMod.getLogger().info("MTEPatchesMod: Patched {} tile leaks in last 100 ticks", sum);
        }
        TileLeakCounters.MARKED_COUNTER.clear();
    }
}

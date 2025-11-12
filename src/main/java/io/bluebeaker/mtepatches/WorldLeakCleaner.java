package io.bluebeaker.mtepatches;

import io.bluebeaker.mtepatches.railcraft.WorldCleanerRC;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.memoryLeakFix;

@Mod.EventBusSubscriber
public class WorldLeakCleaner {
    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event){
        if(memoryLeakFix.railcraft && LoadedModChecker.railcraft.isLoaded()){
            WorldCleanerRC.cleanWorldReference(event.getWorld());
        }
    }
    @Mod.EventHandler
    public static void onServerStopped(FMLServerStoppedEvent event){
        if(memoryLeakFix.railcraft && LoadedModChecker.railcraft.isLoaded()){
            WorldCleanerRC.removeAllWorldReferences();
        }
    }
}

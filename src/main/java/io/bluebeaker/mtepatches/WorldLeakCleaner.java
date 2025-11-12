package io.bluebeaker.mtepatches;

import io.bluebeaker.mtepatches.worldcleaners.IWorldCleaner;
import io.bluebeaker.mtepatches.worldcleaners.WorldCleanerEnergyControl;
import io.bluebeaker.mtepatches.worldcleaners.WorldCleanerRC;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber
public class WorldLeakCleaner {
    public static List<IWorldCleaner> cleaners = new ArrayList<>();

    public static WeakHashMap<World,Boolean> unloadedWorlds = new WeakHashMap<>();

    public static void updateCleaners(){
        cleaners.clear();
        if(MTEPatchesConfig.memoryLeakFix.railcraft && LoadedModChecker.railcraft.isLoaded()){
            cleaners.add(new WorldCleanerRC());
        }
        if(MTEPatchesConfig.memoryLeakFix.energyControl && LoadedModChecker.energyControl.isLoaded()){
            cleaners.add(new WorldCleanerEnergyControl());
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event){
        for (IWorldCleaner cleaner : cleaners) {
            cleaner.cleanWorldReference(event.getWorld());
        }
        unloadedWorlds.put(event.getWorld(),true);
    }
    @Mod.EventHandler
    public static void onServerStopped(FMLServerStoppedEvent event){
        for (IWorldCleaner cleaner : cleaners) {
            cleaner.removeAllWorldReferences();
        }
    }
}

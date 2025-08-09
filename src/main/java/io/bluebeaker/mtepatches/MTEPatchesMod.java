package io.bluebeaker.mtepatches;

import io.bluebeaker.mtepatches.buildcraft.BCCapabilityAdapter;
import io.bluebeaker.mtepatches.buildcraft.BCUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,acceptableRemoteVersions = "*")
public class MTEPatchesMod
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    
    public MinecraftServer server;

    private static Logger logger;
    
    public MTEPatchesMod() {
        MinecraftForge.EVENT_BUS.register(this);
        if(LoadedModChecker.buildcraftcore.isLoaded()){
            BCCapabilityAdapter.INSTANCE.init();
            MinecraftForge.EVENT_BUS.register(BCCapabilityAdapter.INSTANCE);
        }
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        for (LoadedModChecker checker: LoadedModChecker.values()){
            MTEPatchesMod.getLogger().info("Mod '{}' loaded: {}",checker.modid,checker.isLoaded());
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        if(LoadedModChecker.buildcraftcore.isLoaded()){
            BCUtils.updateValues();
        }
        if(LoadedModChecker.railcraft.isLoaded()){
            try {
                Class.forName("mods.railcraft.common.blocks.multi.TileMultiBlock");
                MinecraftForge.EVENT_BUS.register(RCMultiblockPatch.class);
            } catch (ClassNotFoundException e) {
                logger.error("Could not load RCMultiblockPatch: ", e);
            }
        }
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
            if(LoadedModChecker.buildcraftcore.isLoaded()){
                BCUtils.updateValues();
            }
        }
    }
    public static @Nullable Logger getLogger(){return logger;}
    public static void logInfo(String log){
        logger.info(log);
    }
    public static void logDebug(String log,Object... params){
        if(MTEPatchesConfig.debugOutput)
            logger.info(log,params);
    }
}

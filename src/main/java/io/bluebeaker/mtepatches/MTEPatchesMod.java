package io.bluebeaker.mtepatches;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class MTEPatchesMod
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    
    public MinecraftServer server;

    private static Logger logger;
    
    public MTEPatchesMod() {
        MinecraftForge.EVENT_BUS.register(this);
        if(LoadedModChecker.railcraft.isLoaded()){
            MinecraftForge.EVENT_BUS.register(RCMultiblockPatch.class);
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
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
        }
    }
    public static Logger getLogger(){return logger;}
    public static void logInfo(String log){
        logger.info(log);
    }
}

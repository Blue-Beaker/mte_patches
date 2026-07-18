package io.bluebeaker.mtepatches;

import io.bluebeaker.mtepatches.buildcraft.BCCapabilityAdapter;
import io.bluebeaker.mtepatches.buildcraft.BCUtils;
import io.bluebeaker.mtepatches.railcraft.RCMultiblockPatch;
import io.bluebeaker.mtepatches.render.RenderSkipRegistry;
import io.bluebeaker.mtepatches.tileleak.TileLeakCleanerClient;
import io.bluebeaker.mtepatches.tileleak.TileLeakHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.versioning.ComparableVersion;
import net.minecraftforge.fml.relauncher.Side;
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
            ComparableVersion rcVersion = new ComparableVersion(LoadedModChecker.railcraft.getVersion().replace("-beta-",".0."));
            logger.info("Railcraft version: {}", rcVersion);

            int compared = rcVersion.compareTo(new ComparableVersion("12.1"));
            if(compared < 0){
                try {
                    Class.forName("mods.railcraft.common.blocks.multi.TileMultiBlock");
                    MinecraftForge.EVENT_BUS.register(RCMultiblockPatch.class);
                } catch (ClassNotFoundException e) {
                    logger.error("Could not load RCMultiblockPatch: ", e);
                }
            }else {
                logger.info("RCMultiblockPatch is not needed for Railcraft version {}, not loading it",LoadedModChecker.railcraft.getVersion());
            }
        }
        if(event.getSide() == Side.CLIENT){
            MinecraftForge.EVENT_BUS.register(TileLeakCleanerClient.class);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        if(event.getSide()== Side.CLIENT){
            RenderSkipRegistry.INSTANCE.reloadConfigs();
        }
        WorldLeakCleaner.updateCleaners();
        TileLeakHandler.updateConfig();
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }

    @EventHandler
    public void onServerStopped(FMLServerStoppedEvent event){
        this.server=null;
        WorldLeakCleaner.onServerStopped(event);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
            if(LoadedModChecker.buildcraftcore.isLoaded()){
                BCUtils.updateValues();
            }
            if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
                RenderSkipRegistry.INSTANCE.reloadConfigs();
            }
            WorldLeakCleaner.updateCleaners();
            TileLeakHandler.updateConfig();
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

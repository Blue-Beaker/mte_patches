package io.bluebeaker.mtepatches;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = MTEPatchesMod.MODID,type = Type.INSTANCE,category = "general")
public class MTEPatchesConfig {

    @Comment({"Client-side render optimizations."})
    @LangKey("config.mtepatches.render.name")
    public static CategoryRender render = new CategoryRender();
    public static class CategoryRender{

        public CategoryRender(){
            farCulling.extra_tesrs=new String[]{
                    "li.cil.oc.client.renderer.tileentity.PrinterRenderer$",
                    "li.cil.oc.client.renderer.tileentity.CaseRenderer$"
            };

            shadowCulling.buildcraft=false;
            shadowCulling.thermaldynamics=false;
            shadowCulling.projectred=false;
            shadowCulling.extra_tesrs= new String[]{
                    "com.jaquadro.minecraft.storagedrawers.client.renderer.TileEntityDrawersRenderer#Storage Drawers Label",
                    "cofh.thermalexpansion.render.RenderCache#Cache Label",
                    "li.cil.oc.client.renderer.tileentity.PrinterRenderer$",
                    "com.zuxelus.energycontrol.renderers.TEAdvancedInfoPanelRenderer",
                    "vazkii.psi.client.render.tile.RenderTileProgrammer",
                    "li.cil.oc.client.renderer.tileentity.CaseRenderer$",
                    "li.cil.oc.client.renderer.tileentity.ScreenRenderer$"};
        }

        @Config.RangeInt(min = 0)
        @Comment({"Culls some complex rendering in tileentities, like items in pipes, when further than this distance."})
        @LangKey("config.mtepatches.render.renderDistance.name")
        public int cullingDistance = 24;

        @Comment({"Main switch to distance-based and shadow culling optimization of this mod"})
        @LangKey("config.mtepatches.render.enableRenderCulling.name")
        public boolean enableRenderCulling = true;

        @Comment({"Culls certain renderings when far away from the player."})
        @LangKey("config.mtepatches.render.farCulling.name")
        public RenderSkipConfig farCulling = new RenderSkipConfig();
        @Comment({"Culls certain renderings in shadow pass of shaders.",
                "Overlays on some surface, like drawer labels, computer screens, are basically safe to be culled for shadows.",
                "Only effective when using shaders."})
        @LangKey("config.mtepatches.render.shadowCulling.name")
        public RenderSkipConfig shadowCulling = new RenderSkipConfig();

        public static class RenderSkipConfig {
            @Comment({"Items and fluid flow in BC pipes"})
            public boolean buildcraft = true;
            @Comment({"Items and fluid flow in TD pipes"})
            public boolean thermaldynamics = true;
            @Comment({"Items in PR pipes"})
            public boolean projectred = true;
            @Comment({"Fluid tanks in FR machines"})
            public boolean forestry = true;
            @Comment({"Additional TileEntity special renderer or TileEntity classes to be culled for this section.",
                    "Comments can be added after a '#'"})
            @LangKey("config.mtepatches.render.extra_tesrs.name")
            public String[] extra_tesrs = {};
        }
    }

    @LangKey("config.mtepatches.connectionTimeout.name")
    public static CategoryConnectionTimeout connectionTimeout = new CategoryConnectionTimeout();
    public static class CategoryConnectionTimeout{
        @Config.RequiresMcRestart
        @Comment({"Add a timeout when it's retrieving info for some mods, prevent it from sticking the loading progress forever on a lossy internet connection.",
                "Set to 0 for infinite timeout."})
        @LangKey("config.mtepatches.connectionTimeout.timeout.name")
        @Config.RangeInt(min = 0)
        public int timeout = 5000;

        @Comment({"Enable timeout for Biomes o' Plenty when it's checking trail info."})
        public boolean bop = true;
        @Comment({"Enable timeout for Industrial Foregoing when it's checking contributors."})
        public boolean industrialForegoing = true;
    }

    @LangKey("config.mtepatches.vanilla.name")
    public static CategoryVanilla vanilla = new CategoryVanilla();
    public static class CategoryVanilla{
        @Comment({"Stops receiving new item entities on client when player is dead. Item entities doesn't remove correctly on client-side when player is dead, so they can get more and more over time, causing huge lags."})
        @LangKey("config.mtepatches.vanilla.dontReceiveItemsWhenDead.name")
        public boolean dontReceiveItemsWhenDead = true;


        @Comment({"Stops vanilla falling block duplication with end portal,",
                "by blocking it from being teleported by the portal."})
        @LangKey("config.mtepatches.vanilla.fallingBlockDupeFix.name")
        public boolean fallingBlockDupeFix = false;
    }
    @LangKey("config.mtepatches.railcraft.name")
    public static CategoryRailcraft railcraft = new CategoryRailcraft();

    public static class CategoryRailcraft{

        @Comment({"Fix a desync bug of multiblock that when the multiblock is across chunks. ",
        "When desync, client can get its inventory scrambled when right-clicking at the bugged multiblock. "})
        @LangKey("config.mtepatches.railcraft.multiblockfix.name")
        public boolean multiblockSyncFix = true; 

        @Comment("Fix turbine being fixed for free when putting in crafting slot with a blade and take it out.")
        @LangKey("config.mtepatches.railcraft.turbineCraftingFix.name")
        public boolean turbineRepairingFix = true;

        @Comment("Fix outfitted tracks drops only the kit but not the track when destroyed.")
        @LangKey("config.mtepatches.railcraft.outfittedDropsFix.name")
        public boolean outfittedDropsFix = true;
        @Comment("Allow track relayer to replace outfitted track.")
        @LangKey("config.mtepatches.railcraft.replaceOutfittedTrack.name")
        public boolean replaceOutfittedTrack = true;

        @Comment({"This fix improves stability of charge network by a lot, but it's still a bit buggy yet.",
                "Try to place and break cables when ran into problem with charge network."})
        @LangKey("config.mtepatches.railcraft.chargeNetworkFix.name")
        public boolean chargeNetworkFix = true;

        @Comment({"Show debug particles and message for the charge network fix."})
        @LangKey("config.mtepatches.railcraft.chargeNetworkFixDebug.name")
        public boolean chargeNetworkFixDebug = false;

        @Comment({"Optimize Item Loader/Unloaders by moving more multiple items at a time, then wait for a cooldown, reducing lag. ",
        "This option changes the interval to move items. Also affects maximum moved stacksize.","Set to 0 to disable the optimization."})
        @LangKey("config.mtepatches.railcraft.itemMoveInterval.name")
        @Config.RangeInt(min = 0)
        public int itemMoveInterval = 16;

        @Comment({"Tile caches can miss some updates occuring in neighbouring tileentities, like multiblock formation. ",
        "This patch adds an interval(in ticks) to purge the cache.",
        "Even when the interval is reached, the cache only will be purged when it's accessed, like when item unloader unloading items.",
        "Set to 0 to disable."})
        @LangKey("config.mtepatches.railcraft.tileCachePurgeInterval.name")
        @Config.RangeInt(min = 0)
        public int tileCachePurgeInterval = 200;
    }

    @LangKey("config.mtepatches.ic2.name")
    public static CategoryIC2 ic2 = new CategoryIC2();
    public static class CategoryIC2{

        @Comment("Make IC2 crop stick able to put on any BlockFarmland, including the ones added by mods.")
        @LangKey("config.mtepatches.ic2.cropOnAllFarmlands.name")
        public boolean cropOnAllFarmlands = true;

        @Comment("Prevent breaking crop sticks when walking over it")
        @LangKey("config.mtepatches.ic2.noTrampleCrops.name")
        public boolean noTrampleCrops = true;


        @Comment("Fix mass fabricator stuck permanently after it's output is blocked.")
        @LangKey("config.mtepatches.ic2.massFabStuckFix.name")
        public boolean massFabStuckFix = true;

        @Comment({"Prevent IC2 keybinds in inventory GUIs.",
                "Fixes jetpack being activated when pressing jump with the inventory open."})
        @LangKey("config.mtepatches.ic2.blockKeybindInGUI.name")
        public boolean blockKeybindInGUI = true;

        @Comment({"Fixes a bug that on a LAN server, all IC2 messages are sent to the host, instead of the player that should receive the message."})
        @LangKey("config.mtepatches.ic2.lanMessageFix.name")
        public boolean lanMessageFix = true;

        @Comment({"Fixes crops detecting additional dirt under farmland."})
        @LangKey("config.mtepatches.ic2.cropDirtDetectionFix.name")
        public boolean cropDirtDetectionFix = true;

        @Comment({"Increase buffer size of generators that had very small buffers with energy generation factors in config,",
                "to prevent output capping of them."})
        @LangKey("config.mtepatches.ic2.scaleGeneratorBuffers.name")
        public boolean scaleGeneratorBuffers = true;
    }

    @LangKey("config.mtepatches.ic2cropcalc.name")
    public static CategoryIC2CropCalc categoryIC2CropCalc = new CategoryIC2CropCalc();
    public static class CategoryIC2CropCalc{
        @Comment({"Fix wrong items appearing in crop calculator GUI on inventory update"})
        @LangKey("config.mtepatches.ic2cropcalc.fixCalcWindowID.name")
        public boolean fixCalcWindowID = true;
    }

    @LangKey("config.mtepatches.thermal.name")
    public static CategoryThermal thermal = new CategoryThermal();
    public static class CategoryThermal{

        @Config.RequiresMcRestart
        @Comment({"Don't match input NBT for recycling recipes of smelter and sawmill.",
                "Allows recycling enchanted items with them."})
        @LangKey("config.mtepatches.thermal.recycleRecipesFuzzyNBT.name")
        public boolean recycleRecipesFuzzyNBT = true;

    }
    @LangKey("config.mtepatches.forestry.name")
    public static CategoryForestry forestry = new CategoryForestry();
    public static class CategoryForestry{
        @Comment({"Prevent faulty bee queen without mated tag from crashing the server when put into beekeeping.",
                "They will be replaced with a princess with the same NBT."})
        @LangKey("config.mtepatches.forestry.faultyQueenFix.name")
        public boolean faultyQueenFix = true;
        @Comment({"Fix multi-farm does not return dirt or sand when replacing soil."})
        @LangKey("config.mtepatches.forestry.multiFarmSoilReplaceFix.name")
        public boolean multiFarmSoilReplaceFix = true;

        @Config.RequiresMcRestart
        @Comment({"Remove Buildcraft version check for the compat modules. Fixes the module not loading with BC 8.0."})
        @LangKey("config.mtepatches.forestry.bc8Compat.name")
        public boolean bc8Compat = true;

        @Comment({"Fix collision box for bio generator."})
        @LangKey("config.mtepatches.forestry.collisionBoxFix.name")
        public boolean collisionBoxFix = true;
    }

    @LangKey("config.mtepatches.rftools.name")
    public static CategoryRFTools rftools = new CategoryRFTools();
    public static class CategoryRFTools{
        @Comment({"In modular storage GUI, when pulling items with shift,",
                "don't refresh the list until mouse is released.",
                "Improves compatibility with MouseTweaks."})
        @LangKey("config.mtepatches.rftools.storageGuiShiftTweak.name")
        public boolean storageGuiShiftTweak = true;

        @Comment({"In modular storage GUI, when putting your held item into the storage, prevent swapping the hovered item out from the storage."})
        @LangKey("config.mtepatches.rftools.storageGuiInsertTweak.name")
        public boolean storageGuiInsertTweak = true;
    }

    @LangKey("config.mtepatches.buildcraft.name")
    public static CategoryBC buildcraft = new CategoryBC();
    public static class CategoryBC{

        @Comment({"Allows BuildCraft item pipes to accept items ejected from hoppers and machines from other mods"})
        @LangKey("config.mtepatches.buildcraft.itemPipeAcceptEjection.name")
        @Config.RequiresWorldRestart
        public boolean itemPipeAcceptEjection = true;

        @Comment({"Make BuildCraft compatible with Forge Energy and Redstone Flux."})
        @LangKey("config.mtepatches.buildcraft.mjToForgeEnergy.name")
        @Config.RequiresWorldRestart
        public boolean mjToForgeEnergy = false;
        @Comment({"Conversion ratio from MJ to FE. Should not be zero."})
        @LangKey("config.mtepatches.buildcraft.mjToForgeEnergyRatio.name")
        @Config.RangeDouble(min = 0, max = 2147483647)
        public double mjToForgeEnergyRatio = 10.0;

        @Comment({"Apply the transfer rate limits to kinesis pipes.","Also limits how much power can be stored in it."})
        @LangKey("config.mtepatches.buildcraft.limitPipePower.name")
        public boolean limitPipePower = false;

        @Comment({"Cache adaptor capabilities created by this mod. Only disable this for debug!"})
        @LangKey("config.mtepatches.buildcraft.cacheAdaptors.name")
        public boolean cacheAdaptors = true;
    }

    @LangKey("config.mtepatches.moarTinkers.name")
    public static CategoryMoarTinkers moarTinkers = new CategoryMoarTinkers();
    public static class CategoryMoarTinkers{
        @Comment({"Disables server-side mining speed boost of traits when sneaking"})
        @LangKey("config.mtepatches.moarTinkers.sneakDisablesMiningBoost.name")
        public boolean sneakDisablesMiningBoost = true;
    }

    @LangKey("config.mtepatches.industrialforegoing.name")
    public static CategoryIF industrialforegoing = new CategoryIF();
    public static class CategoryIF{
        @Comment({"Fixes a item duplication bug for the insertion conveyor upgrade"})
        @LangKey("config.mtepatches.industrialforegoing.fixConveyorInsertionDuplication.name")
        public boolean fixConveyorInsertionDuplication = true;
        @Comment({"Do null check before meat feeder drains fluid from containers. Prevents potential NullPointerException."})
        @LangKey("config.mtepatches.industrialforegoing.fixMeatFeederTickNull.name")
        public boolean fixMeatFeederTickNull = true;
    }

    @LangKey("config.mtepatches.projectred.name")
    public static CategoryProjectRed projectred = new CategoryProjectRed();
    public static class CategoryProjectRed{
        @Comment({"Fix mining speed for machines and parts on ProjectRed"})
        @LangKey("config.mtepatches.projectred.fixMiningSpeed.name")
        public boolean fixMiningSpeed = true;
        @Comment({"Fix inventory size for certain machines to fix crashes"})
        @LangKey("config.mtepatches.projectred.fixMachineInventorySizes.name")
        public boolean fixMachineInventorySizes = true;
        @Comment({"Prevent overflow items cramming in pressure tubes.",
                "Pressure tubes with stacks more than this value will be considered unpassable when pathfinding, so items won't cram up infinitely and cause huge lags.",
                "Set to 0 to disable this feature."})
        @LangKey("config.mtepatches.projectred.pressureTubeOverflowLimit.name")
        public int pressureTubeOverflowLimit = 64;
    }

    @LangKey("config.mtepatches.storageDrawers.name")
    public static CategoryStorageDrawers storageDrawers = new CategoryStorageDrawers();
    public static class CategoryStorageDrawers{
        @Comment({"QoL feature: Unmark an slot from an formerly-locked drawer with a left-click."})
        @LangKey("config.mtepatches.storageDrawers.clearEmptySlot.name")
        public boolean clearEmptySlot = true;

        @Comment({"Fix trim picked with pick block key",
                "Also fixes TOP info"})
        @LangKey("config.mtepatches.storageDrawers.fixTrimPickBlock.name")
        public boolean fixTrimPickBlock = true;
        @Comment({"Fix extra trim subtypes, fix the silk touch drop"})
        @LangKey("config.mtepatches.storageDrawers.extraTrimSubtypes.name")
        public boolean extraTrimSubtypes = true;

        @Comment({"Fix extra drawers translation key"})
        @LangKey("config.mtepatches.storageDrawers.extraDrawersTranslation.name")
        public boolean extraDrawersTranslation = true;

        @Comment({"Culls drawers rendering when it's backwards towards the player."})
        @LangKey("config.mtepatches.storageDrawers.cullDrawers.name")
        public boolean cullDrawers = true;
    }

    @LangKey("config.mtepatches.bop.name")
    public static CategoryBOP bop = new CategoryBOP();
    public static class CategoryBOP{
        @Comment({"The range for BOP to check fog. BOP Default is 20. Reduce for better performance but worse custom fog quality."})
        @LangKey("config.mtepatches.bop.fogCheckRange.name")
        public int fogCheckRange = 5;
        @Comment({"Disables BOP custom fog completely."})
        @LangKey("config.mtepatches.bop.disableFog.name")
        public boolean disableFog = false;
    }

    @LangKey("config.mtepatches.steveCarts.name")
    public static CategorySteveCarts steveCarts = new CategorySteveCarts();
    public static class CategorySteveCarts{
        @Comment({"Allow using any opaque, solid, non-falling block in bridger."})
        @LangKey("config.mtepatches.steveCarts.moreBridgingBlocks.name")
        public boolean moreBridgingBlocks = true;
    }

    @Comment("Enable debug output for some patches. May cause log spamming!")
    public static boolean debugOutput = false;
}
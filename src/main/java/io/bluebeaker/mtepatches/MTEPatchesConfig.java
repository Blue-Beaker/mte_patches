package io.bluebeaker.mtepatches;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = MTEPatchesMod.MODID,type = Type.INSTANCE,category = "general")
public class MTEPatchesConfig {
    
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
    }

    @LangKey("config.mtepatches.ic2.name")
    public static CategoryIC2 ic2 = new CategoryIC2();
    public static class CategoryIC2{

        @Comment("Make IC2 crop stick able to put on any BlockFarmland, including the ones added by mods.")
        @LangKey("config.mtepatches.ic2.cropOnAllFarmlands.name")
        public boolean cropOnAllFarmlands = true;

        @Comment("Fix mass fabricator stuck permanently after it's output is blocked.")
        @LangKey("config.mtepatches.ic2.massFabStuckFix.name")
        public boolean massFabStuckFix = true;

        @Comment({"Prevent IC2 keybinds in inventory GUIs.",
                "Fixes jetpack being activated when pressing jump with the inventory open."})
        @LangKey("config.mtepatches.ic2.blockKeybindInGUI.name")
        public boolean blockKeybindInGUI = true;
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

    @LangKey("config.mtepatches.bop.name")
    public static CategoryBOP bop = new CategoryBOP();
    public static class CategoryBOP{

        @Config.RequiresMcRestart
        @Comment({"Add a timeout when it's retrieving trail info, prevent it from sticking the loading progress forever on a lossy internet connection.",
                "Set to 0 for infinite timeout (original behaviour)."})
        @LangKey("config.mtepatches.bop.connectionTimeout.name")
        @Config.RangeInt(min = 0)
        public int connectionTimeout = 5000;
    }

    @LangKey("config.mtepatches.buildcraft.name")
    public static CategoryBC buildcraft = new CategoryBC();
    public static class CategoryBC{

        @Comment({"Allows BuildCraft item pipes to accept items ejected from hoppers and machines from other mods"})
        @LangKey("config.mtepatches.buildcraft.itemPipeAcceptEjection.name")
        public boolean itemPipeAcceptEjection = true;
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
    }
}
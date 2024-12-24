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
}
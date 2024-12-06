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
    }
}
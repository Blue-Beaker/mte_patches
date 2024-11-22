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

        @Comment({"Fixes a bug of multiblock that client doesn't load them properly when not loaded at the same time.",
        "For example client inventory is scrambled when right-clicked at partially-loaded multiblock tanks."})
        @LangKey("config.mtepatches.railcraft.multiblockfix.name")
        public boolean multiblockFix = true; 

        @Comment("Fixes turbine being fixed for free when putting in crafting slot with a blade and take it out.")
        @LangKey("config.mtepatches.railcraft.turbineCraftingFix.name")
        public boolean turbineCraftingFix = true; 
    }
}
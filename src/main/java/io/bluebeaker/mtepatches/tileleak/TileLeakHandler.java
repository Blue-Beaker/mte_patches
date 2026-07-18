package io.bluebeaker.mtepatches.tileleak;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TileLeakHandler {
    public static List<ITileChecker> checkers = new ArrayList<>();

    public static boolean canLeak(Block block) {
        for (ITileChecker checker : checkers) {
            if (checker.checkTile(block)) {
                return true;
            }
        }
        return false;
    }
    public static void updateConfig() {
        checkers.clear();
        if (MTEPatchesConfig.tileLeakFix.blockIDs.length>0) {
            CheckerByID checkerByID = new CheckerByID();
            for (String additionalLeakBlock : MTEPatchesConfig.tileLeakFix.blockIDs) {
                String id = additionalLeakBlock.split("#", 2)[0];
                checkerByID.add(new ResourceLocation(id));
            }
        }
        if(MTEPatchesConfig.tileLeakFix.blockClasses.length>0){
            CheckerByClass checkerByClass = new CheckerByClass();
            for (String blockClass : MTEPatchesConfig.tileLeakFix.blockClasses) {
                checkerByClass.add(blockClass);
            }
            checkers.add(checkerByClass);
        }
    }
}

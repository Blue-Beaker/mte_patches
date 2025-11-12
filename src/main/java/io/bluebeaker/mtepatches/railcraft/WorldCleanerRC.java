package io.bluebeaker.mtepatches.railcraft;

import io.bluebeaker.mtepatches.mixin.railcraft.AccessorTrainManager;
import net.minecraft.world.World;

public class WorldCleanerRC {
    public static void cleanWorldReference(World world){
        AccessorTrainManager.getInstances().remove(world);
    }
    public static void removeAllWorldReferences(){
        AccessorTrainManager.getInstances().clear();
    }
}

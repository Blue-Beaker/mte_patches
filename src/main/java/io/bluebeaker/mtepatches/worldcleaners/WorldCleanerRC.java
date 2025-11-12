package io.bluebeaker.mtepatches.worldcleaners;

import io.bluebeaker.mtepatches.mixin.railcraft.AccessorTrainManager;
import net.minecraft.world.World;

public class WorldCleanerRC implements IWorldCleaner {
    @Override
    public void cleanWorldReference(World world){
        AccessorTrainManager.getInstances().remove(world);
    }
    @Override
    public void removeAllWorldReferences(){
        AccessorTrainManager.getInstances().clear();
    }
}

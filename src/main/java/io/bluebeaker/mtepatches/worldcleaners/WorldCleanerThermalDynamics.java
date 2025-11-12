package io.bluebeaker.mtepatches.worldcleaners;

import cofh.thermaldynamics.duct.item.SimulatedInv;
import net.minecraft.world.World;

public class WorldCleanerThermalDynamics implements IWorldCleaner {
    @Override
    public void cleanWorldReference(World world) {

    }

    @Override
    public void removeAllWorldReferences() {
        // Re-create an instance
        SimulatedInv.INSTANCE=new SimulatedInv();
    }
}

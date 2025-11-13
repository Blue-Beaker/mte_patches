package io.bluebeaker.mtepatches.worldcleaners;

import cofh.thermaldynamics.duct.item.SimulatedInv;
import io.bluebeaker.mtepatches.utils.DummyItemHandler;
import net.minecraft.world.World;

public class WorldCleanerThermalDynamics implements IWorldCleaner {
    @Override
    public void cleanWorldReference(World world) {
    }

    @Override
    public void removeAllWorldReferences() {
        // Set to a dummy handler
        SimulatedInv.wrapHandler(new DummyItemHandler());
    }

}

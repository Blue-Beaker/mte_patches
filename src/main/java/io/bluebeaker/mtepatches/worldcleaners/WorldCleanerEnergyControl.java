package io.bluebeaker.mtepatches.worldcleaners;

import com.zuxelus.energycontrol.EnergyControl;
import com.zuxelus.energycontrol.hooks.IC2Hooks;
import net.minecraft.world.World;

public class WorldCleanerEnergyControl implements IWorldCleaner{
    @Override
    public void cleanWorldReference(World world) {
        IC2Hooks.map.keySet().removeIf((tile)->tile.getWorld()==world);
    }

    @Override
    public void removeAllWorldReferences() {
        IC2Hooks.map.clear();
        EnergyControl.altPressed.clear();
    }
}

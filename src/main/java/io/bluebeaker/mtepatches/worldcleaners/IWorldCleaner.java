package io.bluebeaker.mtepatches.worldcleaners;

import net.minecraft.world.World;

public interface IWorldCleaner {
    void cleanWorldReference(World world);

    void removeAllWorldReferences();
}

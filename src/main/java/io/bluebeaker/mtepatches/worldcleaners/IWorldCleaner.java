package io.bluebeaker.mtepatches.worldcleaners;

import net.minecraft.world.World;

public interface IWorldCleaner {
    /** Called after a world is unloaded
     * @param world The world to be cleaned after unloading
     */
    void cleanWorldReference(World world);

    /** Called after the server is shut down, when player save and quit to title screen
     *
     */
    void removeAllWorldReferences();
}

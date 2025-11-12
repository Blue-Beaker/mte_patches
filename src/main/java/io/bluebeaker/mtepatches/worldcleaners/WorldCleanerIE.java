package io.bluebeaker.mtepatches.worldcleaners;

import blusunrize.immersiveengineering.common.EventHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldCleanerIE implements IWorldCleaner{
    @Override
    public void cleanWorldReference(World world) {
        EventHandler.interdictionTiles.removeIf((tile)-> tile instanceof TileEntity && ((TileEntity) tile).getWorld()==world);
    }

    @Override
    public void removeAllWorldReferences() {
        EventHandler.interdictionTiles.clear();
    }
}

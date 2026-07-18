package io.bluebeaker.mtepatches.tileleak;

import net.minecraft.block.Block;

public interface ITileChecker  {
    boolean checkTile(Block block);
}

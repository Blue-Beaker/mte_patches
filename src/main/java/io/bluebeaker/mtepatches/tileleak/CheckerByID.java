package io.bluebeaker.mtepatches.tileleak;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CheckerByID implements ITileChecker {
    private static final List<Block> blocks = new ArrayList<>();

    @Override
    public boolean checkTile(Block block) {
        return blocks.contains(block);
    }
    public void add(ResourceLocation id) {
        Block block = Block.REGISTRY.getObject(id);
        if (block != Blocks.AIR) {
            blocks.add(block);
        }
    }
}

package io.bluebeaker.mtepatches.projectred;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class FakeBlocks {
    public static final Material FAKE_MATERIAL = new Material(MapColor.AIR);
    public static final FakeBlock WIRE = new FakeBlock(0.5F, FAKE_MATERIAL);
    public static final FakeBlock PIPE = new FakeBlock(0.5F, FAKE_MATERIAL);
    public static final FakeBlock GATE = new FakeBlock(0.5F, FAKE_MATERIAL);
    public static final FakeBlock LIGHT = new FakeBlock(0.5F, FAKE_MATERIAL);

    public static class FakeBlock extends Block {

        public FakeBlock(float hardness, Material material) {
            super(material);
            this.setHardness(hardness);
        }
    }
}

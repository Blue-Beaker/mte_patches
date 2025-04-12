package io.bluebeaker.mtepatches.projectred;

import io.bluebeaker.mtepatches.MTEPatchesMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;

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
    public static float getStrength(@Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull World world){
        try {
            return ForgeHooks.blockStrength(state, player, world, new BlockPos(0, -1, 0));
        } catch (Exception e) {
            if (MTEPatchesMod.getLogger() != null) {
                MTEPatchesMod.getLogger().error("Exception getting strength: ",e);
            }
            // Fallback
            return 2/30F;
        }
    }
}

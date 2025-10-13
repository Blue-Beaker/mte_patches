package io.bluebeaker.mtepatches.mixin.storagedrawersextra;

import com.jaquadro.minecraft.storagedrawersextra.block.BlockTrimExtra;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.storageDrawers;

@Mixin(BlockTrimExtra.class)
public abstract class MixinBlockTrimExtra extends Block {
    public MixinBlockTrimExtra(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Intrinsic
    public int damageDropped(IBlockState state) {
        if(!storageDrawers.fixTrimPickBlock) return super.damageDropped(state);
        return state.getBlock().getMetaFromState(state);
    }
}

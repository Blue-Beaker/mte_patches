package io.bluebeaker.mtepatches.mixin.storagedrawers;

import com.jaquadro.minecraft.storagedrawers.block.BlockTrim;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.storageDrawers;

@Mixin(BlockTrim.class)
public abstract class MixinBlockTrim extends Block {
    public MixinBlockTrim(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Intrinsic
    public int damageDropped(IBlockState state) {
        if(!storageDrawers.fixTrimPickBlock) return super.damageDropped(state);
        return state.getBlock().getMetaFromState(state);
    }
}

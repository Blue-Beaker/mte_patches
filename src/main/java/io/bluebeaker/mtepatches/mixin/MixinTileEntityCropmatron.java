package io.bluebeaker.mtepatches.mixin;

import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TileEntityCropmatron.class)
public abstract class MixinTileEntityCropmatron {
    //Ignore the 'Cannot resolve any target instructions in target class' error
    @Redirect(method = "tryHydrateFarmland(Lnet/minecraft/util/math/BlockPos;)Z",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"))
    public Block replaceFarmland(IBlockState state){
        if(MTEPatchesConfig.ic2.cropOnAllFarmlands
                && state.getBlock() instanceof BlockFarmland){
            return Blocks.FARMLAND;
        }
        else{
            return state.getBlock();
        }
    }
}

package io.bluebeaker.mtepatches.mixin;

import ic2.core.crop.ItemCrop;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemCrop.class,remap = true)
public abstract class MixinItemCrop {
    //Ignore the 'Cannot resolve any target instructions in target class' error
    @Redirect(method = "onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"))
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

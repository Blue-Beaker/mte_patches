package io.bluebeaker.mtepatches.mixin.mrtjpcore;

import mrtjp.core.block.MTBlockTile;
import mrtjp.core.block.MultiTileBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiTileBlock.class)
public abstract class MixinMultiTileBlock extends Block {

    public MixinMultiTileBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Inject(method = "getPlayerRelativeBlockHardness(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F",at=@At("HEAD"),cancellable = true)
    public void getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos, CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(super.getPlayerRelativeBlockHardness(state, player, world, pos));
    }

    @Inject(method = "getBlockHardness(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F",at=@At("HEAD"),cancellable = true)
    public void getHardness(IBlockState state, World world, BlockPos pos, CallbackInfoReturnable<Float> cir){
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof MTBlockTile){
            cir.setReturnValue(((MTBlockTile)tile).getHardness());
        }
    }
}

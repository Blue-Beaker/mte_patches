package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.MTEPatchesMod;
import mods.railcraft.common.blocks.tracks.outfitted.BlockTrackOutfitted;
import mods.railcraft.common.blocks.tracks.outfitted.TileTrackOutfitted;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockTrackOutfitted.class)
public class MixinBlockTrackOutfitted {
    @Inject(method = "getDrops(Lnet/minecraft/util/NonNullList;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V",at = @At("HEAD"))
    public void addMissingTrack(NonNullList<ItemStack> items, IBlockAccess world, BlockPos pos, IBlockState state, int fortune, CallbackInfo ci){

        TileEntity tile = world.getTileEntity(pos);
        try {
            if (tile instanceof TileTrackOutfitted)
                ((TileTrackOutfitted) tile).getTrackType().getBaseBlock().getDrops(items,world,pos,state,fortune);
        } catch (Error e) {
            MTEPatchesMod.getLogger().error("Exception in MixinBlockTrackOutfitted:",e);
        }
    }
}

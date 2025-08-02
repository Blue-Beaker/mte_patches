package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.MTEPatchesMod;
import mods.railcraft.common.blocks.tracks.BlockTrackTile;
import mods.railcraft.common.blocks.tracks.outfitted.BlockTrackOutfitted;
import mods.railcraft.common.blocks.tracks.outfitted.TileTrackOutfitted;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockTrackOutfitted.class,remap = false)
public abstract class MixinBlockTrackOutfitted extends BlockTrackTile<TileTrackOutfitted> {
    @Inject(method = "getDrops(Lnet/minecraft/util/NonNullList;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V",at = @At("HEAD"))
    public void addMissingTrack(NonNullList<ItemStack> items, IBlockAccess world, BlockPos pos, IBlockState state, int fortune, CallbackInfo ci){
        if(!MTEPatchesConfig.railcraft.outfittedDropsFix || mtepatches_isBeingRemovedByPlayer.get()) return;
        TileEntity tile = world.getTileEntity(pos);
        try {
            if (tile instanceof TileTrackOutfitted)
                ((TileTrackOutfitted) tile).getTrackType().getBaseBlock().getDrops(items,world,pos,state,fortune);
        } catch (Error e) {
            MTEPatchesMod.getLogger().error("Exception in MixinBlockTrackOutfitted:",e);
        }
    }

    @Unique
    ThreadLocal<Boolean> mtepatches_isBeingRemovedByPlayer = new ThreadLocal<>();

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if(MTEPatchesConfig.railcraft.outfittedDropsFix){
            //noinspection ConstantConditions
            player.addStat(StatList.getBlockStats(this));
            player.addExhaustion(0.005F);
            if (world.isRemote) return true;

            mtepatches_isBeingRemovedByPlayer.set(true);
            dropBlockAsItem(world,pos,state,0);
            boolean result = clearBlock(state, world, pos, player);
            mtepatches_isBeingRemovedByPlayer.set(false);
            return result;
        }else {
            return super.removedByPlayer(state, world, pos, player, willHarvest);
        }
    }

    public void breakRail(World world, BlockPos pos) {
        if(!MTEPatchesConfig.railcraft.outfittedDropsFix){
            super.breakRail(world, pos);
            return;
        }
        if (world.isRemote) return;
        this.dropBlockAsItem(world,pos,world.getBlockState(pos),0);
        world.destroyBlock(pos, false);
    }
}

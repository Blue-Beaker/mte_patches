package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mods.railcraft.api.carts.CartToolsAPI;
import mods.railcraft.api.tracks.IBlockTrackOutfitted;
import mods.railcraft.api.tracks.TrackToolsAPI;
import mods.railcraft.api.tracks.TrackType;
import mods.railcraft.common.blocks.tracks.TrackTools;
import mods.railcraft.common.blocks.tracks.outfitted.TileTrackOutfitted;
import mods.railcraft.common.carts.CartBaseMaintenancePattern;
import mods.railcraft.common.carts.EntityCartTrackRelayer;
import mods.railcraft.common.plugins.forge.WorldPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityCartTrackRelayer.class,remap = false)
public abstract class MixinCartTrackRelayer extends CartBaseMaintenancePattern {
    @Shadow @Final private static int SLOT_EXIST;
    @Shadow @Final private static int SLOT_STOCK;

    @Shadow protected abstract void replace();

    protected MixinCartTrackRelayer(World world) {
        super(world);
    }

    @Inject(method = "replace()V",at = @At(value = "TAIL"))
    public void replaceOutfittedTrack(CallbackInfo ci){
        if(!MTEPatchesConfig.railcraft.replaceOutfittedTrack) return;
        BlockPos pos = getPosition();

        if (TrackTools.isRailBlockAt(world, pos.down()))
            pos = pos.down();

        Block block = WorldPlugin.getBlock(world, pos);
        TileEntity tile = world.getTileEntity(pos);
        if(block instanceof IBlockTrackOutfitted && tile instanceof TileTrackOutfitted){
            IBlockTrackOutfitted outfitted = (IBlockTrackOutfitted) block;
            TileTrackOutfitted tileOutfitted = (TileTrackOutfitted) tile;
            //Test track matches outfitted track
            ItemStack trackToMatch = patternInv.getStackInSlot(SLOT_EXIST);
            TrackType trackTypeOriginal = outfitted.getTrackType(world, pos);
            if(TrackToolsAPI.getTrackType(trackToMatch)!=trackTypeOriginal) return;

            ItemStack trackStock = getStackInSlot(SLOT_STOCK);
            if(trackStock.isEmpty()) return;
            //Replace with new track and return old track
            TrackType trackTypeNew = TrackToolsAPI.getTrackType(trackStock);
            if(!trackTypeNew.getFlexStack().isItemEqual(trackStock)) return;

            decrStackSize(SLOT_STOCK,1);
            blink();
            CartToolsAPI.transferHelper().offerOrDropItem(this, trackTypeOriginal.getFlexStack());
            tileOutfitted.setTrackType(trackTypeNew);
            tileOutfitted.sendUpdateToClient();

            //Play replacement sound
            IBlockState state = world.getBlockState(pos);
            SoundType soundType = block.getSoundType(state, world, pos, null);
            world.playSound(null, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
        }
    }
}

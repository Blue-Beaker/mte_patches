package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mods.railcraft.common.blocks.tracks.BlockTrack;
import mods.railcraft.common.blocks.tracks.BlockTrackTile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockTrackTile.class,remap = false)
public abstract class MixinBlockTrackTile extends BlockTrack {
    @Inject(method = "dropBlockAsItemWithChance(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;FI)V",at= @At(value = "HEAD"),cancellable = true,remap = true)
    public void cancelBlockDropping(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune, CallbackInfo ci){
        if(!MTEPatchesConfig.railcraft.outfittedDropsFix) return;
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        ci.cancel();
    }
}

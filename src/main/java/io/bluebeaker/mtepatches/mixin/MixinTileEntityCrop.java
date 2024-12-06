package io.bluebeaker.mtepatches.mixin;

import ic2.core.crop.TileEntityCrop;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityCrop.class,remap = false)
public abstract class MixinTileEntityCrop {
    @Inject(method = "onNeighborChange",at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lic2/core/block/TileEntityBlock;onNeighborChange(Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V"),cancellable = true)
    public void onNeighborChange(Block neighbor, BlockPos neighborPos, CallbackInfo ci){
        if(!MTEPatchesConfig.ic2.cropOnAllFarmlands)
            return;
        if (((TileEntityCrop)(Object)this).getWorld().getBlockState(((TileEntityCrop)(Object)this).getPos().down()).getBlock() instanceof BlockFarmland){
            ci.cancel();
        }
    }
}

package io.bluebeaker.mtepatches.mixin.ic2;

import ic2.core.crop.TileEntityCrop;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

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

    @Redirect(method = "onEntityCollision",at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    public int preventTrample(Random instance, int i){
        if(MTEPatchesConfig.ic2.noTrampleCrops) return 1;
        return instance.nextInt(i);
    }

    @Redirect(method = "updateTerrainNutrients",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"))
    public Block fixDirtDetection(IBlockState state){
        Block block = state.getBlock();
        if(MTEPatchesConfig.ic2.cropDirtDetectionFix && block instanceof BlockFarmland){
            return Blocks.DIRT;
        }
        return block;
    }
}

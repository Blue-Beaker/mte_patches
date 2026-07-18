package io.bluebeaker.mtepatches.mixin_core;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.MTEPatchesMod;
import io.bluebeaker.mtepatches.tileleak.TileLeakHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Chunk.class)
public abstract class MixinChunk {
    @WrapOperation(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;hasTileEntity(Lnet/minecraft/block/state/IBlockState;)Z",ordinal = 0))
    private boolean onSetBlockState(Block instance, IBlockState state, Operation<Boolean> original){
        if(MTEPatchesConfig.tileLeakFix.enable &&
                (MTEPatchesConfig.tileLeakFix.forceAll || TileLeakHandler.canLeak(instance))){
            MTEPatchesMod.logDebug("MTEPatchesMod: Patching tile leak " + state.getBlock().getRegistryName());
            return true;
        }
        return original.call(instance,state);
    }
}

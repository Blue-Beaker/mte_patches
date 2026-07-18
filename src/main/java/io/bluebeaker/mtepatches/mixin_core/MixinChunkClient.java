package io.bluebeaker.mtepatches.mixin_core;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.tileleak.TileLeakCounters;
import io.bluebeaker.mtepatches.tileleak.TileLeakHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Chunk.class)
public abstract class MixinChunkClient {
    @WrapOperation(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;hasTileEntity(Lnet/minecraft/block/state/IBlockState;)Z",ordinal = 0))
    private boolean onSetBlockState(Block instance, IBlockState state, Operation<Boolean> original){
        boolean result = original.call(instance,state);
        if (result) {return true;}

        if(MTEPatchesConfig.tileLeakFix.enable &&
                (MTEPatchesConfig.tileLeakFix.forceAll || TileLeakHandler.canLeak(instance))){
            if(MTEPatchesConfig.tileLeakFix.logs){
                TileLeakCounters.MARKED_COUNTER.add(instance.getClass());
            }
            return true;
        }
        return false;
    }
}

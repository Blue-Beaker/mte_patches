package io.bluebeaker.mtepatches.mixin_core;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFenceGate.class)
public abstract class MixinBlockFenceGate extends Block {
    public MixinBlockFenceGate(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Inject(method = "canPlaceBlockAt",at=@At("HEAD"),cancellable = true)
    public void setCanPlace(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if(MTEPatchesConfig.vanilla.floatingFenceGate) {
            cir.setReturnValue(super.canPlaceBlockAt(worldIn, pos));
        }
    }
}

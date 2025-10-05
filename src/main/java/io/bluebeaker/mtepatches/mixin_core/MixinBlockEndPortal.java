package io.bluebeaker.mtepatches.mixin_core;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEndPortal.class)
public abstract class MixinBlockEndPortal extends BlockContainer {

    protected MixinBlockEndPortal(Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "onEntityCollision",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;changeDimension(I)Lnet/minecraft/entity/Entity;",ordinal = 0), cancellable = true)
    public void preventDropItemWhenDied(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, CallbackInfo ci){
        if(!MTEPatchesConfig.vanilla.fallingBlockDupeFix) return;
        if(entityIn instanceof EntityFallingBlock){
            ci.cancel();
        }
    }
}

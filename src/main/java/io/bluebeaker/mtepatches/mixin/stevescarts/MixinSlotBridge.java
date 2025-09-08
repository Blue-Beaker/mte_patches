package io.bluebeaker.mtepatches.mixin.stevescarts;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vswe.stevescarts.containers.slots.SlotBridge;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.steveCarts;

@Mixin(value = SlotBridge.class,remap = false)
public abstract class MixinSlotBridge {
    @Inject(method = "isBridgeMaterial",at = @At("HEAD"),cancellable = true)
    private static void allowAllBlocks(ItemStack itemstack, CallbackInfoReturnable<Boolean> cir){
        if(!steveCarts.moreBridgingBlocks) return;
        final Block b = Block.getBlockFromItem(itemstack.getItem());
        if(b instanceof BlockFalling) return;
        IBlockState state = b.getStateFromMeta(itemstack.getItemDamage());
        if(state.getMaterial().isOpaque() && state.isFullCube()){
            cir.setReturnValue(true);
        }
    }
}

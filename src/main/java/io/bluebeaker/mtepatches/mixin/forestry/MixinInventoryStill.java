package io.bluebeaker.mtepatches.mixin.forestry;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import forestry.core.fluids.FluidHelper;
import forestry.factory.inventory.InventoryStill;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = InventoryStill.class,remap = false)
public abstract class MixinInventoryStill {
    @WrapOperation(method = "canSlotAccept(ILnet/minecraft/item/ItemStack;)Z",at = @At(value = "INVOKE", target = "Lforestry/core/fluids/FluidHelper;isFillableEmptyContainer(Lnet/minecraft/item/ItemStack;)Z",ordinal = 0))
    private boolean allowPartiallyFilledContainers(ItemStack contents, Operation<Boolean> original){
        if(MTEPatchesConfig.forestry.acceptPartialContainers)
            return FluidHelper.isFillableContainerWithRoom(contents);
        return original.call(contents);
    }
}

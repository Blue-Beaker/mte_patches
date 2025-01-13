package io.bluebeaker.mtepatches.mixin.thermal;

import cofh.thermalexpansion.util.managers.machine.SmelterManager;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.thermal.RecycleRecipeHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SmelterManager.class,remap = false)
public abstract class MixinSmelterManager {
    @ModifyVariable(method = "convertInput(Lnet/minecraft/item/ItemStack;)Lcofh/core/inventory/ComparableItemStackValidatedNBT;", at = @At("HEAD"), argsOnly = true)
    private static ItemStack fuzzyNBT(ItemStack stack){
        if(MTEPatchesConfig.thermal.recycleRecipesFuzzyNBT && RecycleRecipeHelper.SMELTER.matches(stack)){
            ItemStack copy = stack.copy();
            copy.setTagCompound(null);
            return copy;
        }
        return stack;
    }

    @Inject(method = "addRecycleRecipe(ILnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;IZ)V",at=@At("HEAD"))
    private static void processRecycleRecipe(int energy, ItemStack input, ItemStack output, int outputSize, boolean wildcard, CallbackInfo ci){
        if(MTEPatchesConfig.thermal.recycleRecipesFuzzyNBT && input.getTagCompound()==null)
            RecycleRecipeHelper.SMELTER.add(input);
    }
}

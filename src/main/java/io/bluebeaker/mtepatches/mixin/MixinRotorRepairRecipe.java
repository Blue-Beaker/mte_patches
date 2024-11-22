package io.bluebeaker.mtepatches.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mods.railcraft.common.util.crafting.RotorRepairRecipe;
import net.minecraft.item.ItemStack;

@Mixin(RotorRepairRecipe.class)
public class MixinRotorRepairRecipe {
    @ModifyVariable(remap = true, at = @At(value = "STORE"), ordinal = 0, method = "getCraftingResult")
    public ItemStack copyItemStack(ItemStack stack) {
        if (MTEPatchesConfig.railcraft.turbineRepairingFix)
            return stack.copy();
        else
            return stack;
    }
}

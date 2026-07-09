package io.bluebeaker.mtepatches.mixin.rftools;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mcjty.rftools.craftinggrid.CraftingGridProvider;
import mcjty.rftools.craftinggrid.GuiCraftingGrid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = GuiCraftingGrid.class,remap = false)
public abstract class MixinGuiCraftingGrid {

    @Unique
    private ItemStack[] mte_patches$cachedCraftingGrid;

    @Shadow(remap = false)
    private CraftingGridProvider provider;

    @Inject(method = "testRecipe()V", at = @At("HEAD"),remap = false, cancellable = true)
    private void cachedTestRecipe(CallbackInfo ci) {
        if(!MTEPatchesConfig.rftools.craftingGUIOptimization){
            return;
        }

        if (mte_patches$cachedCraftingGrid == null) {
            mte_patches$cachedCraftingGrid = new ItemStack[9];
            Arrays.fill(mte_patches$cachedCraftingGrid, ItemStack.EMPTY);
        }
        IInventory craftingGridInventory = ((IInventory)(provider.getCraftingGrid().getCraftingGridInventory()));
        // Skip recipe checking if the crafting grid hasn't changed since the last check
        boolean isSame = true;
        for (int i = 0; i < 9; i++) {
            ItemStack currentStack = craftingGridInventory.getStackInSlot(i + 1);
            if (!ItemStack.areItemStacksEqual(currentStack, mte_patches$cachedCraftingGrid[i])) {
                isSame = false;
                break;
            }
        }
        // Do checking when the grid is changed
        if (!isSame) {
            // Capture the grid before actually checking the recipe
            for (int i = 0; i < 9; i++) {
                mte_patches$cachedCraftingGrid[i] = craftingGridInventory.getStackInSlot(i+1).copy();
            }
        }else {
            // When not changed, cancel
            ci.cancel();
        }
    }
}

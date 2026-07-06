package io.bluebeaker.mtepatches.mixin.rftools;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mcjty.rftools.craftinggrid.CraftingGridProvider;
import mcjty.rftools.craftinggrid.GuiCraftingGrid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Mixin(value = GuiCraftingGrid.class,remap = false)
public abstract class MixinGuiCraftingGrid {

    @Unique
    private ItemStack[] mte_patches$cachedCraftingGrid;

    @Shadow(remap = false)
    private CraftingGridProvider provider;

    @WrapOperation(method = "draw()V", at = @At(value="INVOKE", target = "Lmcjty/rftools/craftinggrid/GuiCraftingGrid;testRecipe()V"),remap = false)
    private void cachedTestRecipe(GuiCraftingGrid instance, Operation<Void> original) {
        if(!MTEPatchesConfig.rftools.craftingGUIOptimization){
            original.call(instance);
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
            original.call(instance);
            for (int i = 0; i < 9; i++) {
                mte_patches$cachedCraftingGrid[i] = craftingGridInventory.getStackInSlot(i+1).copy();
            }
        }
    }
}

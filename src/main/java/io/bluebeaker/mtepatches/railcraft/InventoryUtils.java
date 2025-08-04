package io.bluebeaker.mtepatches.railcraft;

import io.bluebeaker.mtepatches.MTEPatchesMod;
import mods.railcraft.common.util.inventory.*;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

import static mods.railcraft.common.util.inventory.InvTools.emptyStack;
import static mods.railcraft.common.util.inventory.InvTools.isEmpty;

public class InventoryUtils {
    public static ItemStack redirectMoveOneItem(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter){
        MTEPatchesMod.getLogger().info("Redirect move item: {} {}",instance,dest);
        return instance.stream().map(src -> src.moveOneItemTo(dest, filter))
                .filter(InvTools::nonEmpty)
                .findFirst().orElseGet(InvTools::emptyStack);
    }
    public static ItemStack redirectMoveOneItemAdaptor(InventoryAdaptor src, IInventoryComposite dest, Predicate<ItemStack> filter) {
        for (IInvSlot slot : InventoryIterator.get(src)) {
            if (slot.hasStack() && slot.canTakeStackFromSlot() && slot.matches(filter)) {
                ItemStack stack = slot.getStack();
                stack = InvTools.copyOne(stack);
                stack = dest.addStack(stack);
                if (isEmpty(stack))
                    return slot.decreaseStack();
            }
        }
        return emptyStack();
    }
}

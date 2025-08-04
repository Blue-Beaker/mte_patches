package io.bluebeaker.mtepatches.railcraft;

import io.bluebeaker.mtepatches.MTEPatchesMod;
import mods.railcraft.common.util.inventory.*;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;
import static mods.railcraft.common.util.inventory.InvTools.emptyStack;

public class InventoryUtils {
    public static ItemStack redirectMoveOneItem(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter){
        MTEPatchesMod.getLogger().info("Redirect move item: {} {}",instance,dest);
        return instance.stream().map(src -> redirectMoveOneItemAdaptor(src,dest, filter))
                .filter(InvTools::nonEmpty)
                .findFirst().orElseGet(InvTools::emptyStack);
    }
    public static ItemStack redirectMoveOneItemAdaptor(InventoryAdaptor src, IInventoryComposite dest, Predicate<ItemStack> filter) {
        for (IInvSlot slot : InventoryIterator.get(src)) {
            if (slot.hasStack() && slot.canTakeStackFromSlot() && slot.matches(filter)) {
                ItemStack stackToMove = slot.getStack().copy();
                if(stackToMove.getCount()> railcraft.itemMoveInterval){
                    stackToMove.setCount(railcraft.itemMoveInterval);
                }
                ItemStack stackLeft = dest.addStack(stackToMove);
                int movedAmount=stackToMove.getCount()-stackLeft.getCount();
                if(movedAmount>0){
                    return slot.removeFromSlot(movedAmount,InvOp.EXECUTE);
                }
            }
        }
        return emptyStack();
    }
}

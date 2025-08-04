package io.bluebeaker.mtepatches.railcraft;

import mods.railcraft.common.util.inventory.*;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;
import static mods.railcraft.common.util.inventory.InvTools.emptyStack;

public class InventoryUtils {
    public static ItemStack moveStackInternal(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter){
//        MTEPatchesMod.getLogger().info("Redirect move item: {} {}",instance,dest);
        return instance.stream().map(src -> moveStackInternal(src,dest, filter))
                .filter(InvTools::nonEmpty)
                .findFirst().orElseGet(InvTools::emptyStack);
    }
    protected static ItemStack moveStackInternal(InventoryAdaptor src, IInventoryComposite dest, Predicate<ItemStack> filter) {
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
    public static int getCooldownFromMovedStack(ItemStack stack){
        return (stack.isEmpty()?railcraft.itemMoveInterval:Math.min(railcraft.itemMoveInterval, stack.getCount()))-1;
    }
}

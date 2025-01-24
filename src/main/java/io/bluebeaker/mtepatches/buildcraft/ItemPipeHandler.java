package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.inventory.IItemTransactor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class ItemPipeHandler implements IItemHandlerModifiable {
    protected final IItemTransactor transactor;

    public ItemPipeHandler(IItemTransactor transactor) {
        this.transactor = transactor;
    }

    @Override
    public int getSlots()
    {
        return 1;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot)
    {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        ItemStack insert = this.transactor.insert(stack, false, simulate);
        return insert;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        // nothing to do here
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
        return this.transactor.canPartiallyAccept(stack);
    }
}

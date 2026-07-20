package io.bluebeaker.mtepatches.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConsumableFluidProvider implements ICapabilityProvider {

    public final FluidStack FLUID_STACK;

    public ConsumableFluidProvider(FluidStack fluidStack) {
        FLUID_STACK = fluidStack;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY){
            return (T) new WaterGelFluidHandler(FLUID_STACK);
        }
        return null;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    public static class WaterGelFluidHandler implements IFluidHandlerItem {
        public final FluidStack FLUID_STACK;

        public WaterGelFluidHandler(FluidStack fluidStack) {
            FLUID_STACK = fluidStack;
        }

        @Override
        public ItemStack getContainer() {
            return ItemStack.EMPTY;
        }

        @Override
        public IFluidTankProperties[] getTankProperties() {
            return new IFluidTankProperties[]{new FluidTankProperties(FLUID_STACK, 1000)};
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            return 0;
        }

        @Override
        public @Nullable FluidStack drain(FluidStack resource, boolean doDrain) {
            if(resource.isFluidEqual(FLUID_STACK) && resource.amount >= 1000) {
                return FLUID_STACK.copy();
            }
            return null;
        }

        @Override
        public @Nullable FluidStack drain(int maxDrain, boolean doDrain) {
            if(maxDrain >= 1000){
                return FLUID_STACK.copy();
            }
            return null;
        }
    }
}

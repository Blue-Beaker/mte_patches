package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.MjAPI;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergyAdaptorProviderFEtoMJ implements ICapabilityProvider {
    public final TileEntity tile;
    public EnergyAdaptorProviderFEtoMJ(TileEntity tile) {
        this.tile=tile;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        if(this.shouldAddCapability(capability,facing)){
            return tile.hasCapability(CapabilityEnergy.ENERGY,facing);
        }
        return false;
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if(this.shouldAddCapability(capability,facing)){
            return (T) new EnergyAdaptorFEtoMJ(tile.getCapability(CapabilityEnergy.ENERGY,facing));
        }
        return null;
    }
    
    protected boolean shouldAddCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
        return capability == MjAPI.CAP_RECEIVER;
    }
}

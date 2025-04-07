package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.MjAPI;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergyAdaptorProviderFEtoMJ extends CachingAdaptorProvider<IEnergyStorage,EnergyAdaptorFEtoMJ> implements ICapabilityProvider {
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
            IEnergyStorage capability1 = tile.getCapability(CapabilityEnergy.ENERGY, facing);
            if(capability1!=null)
                return (T) getOrCreateAdaptor(capability1);
        }
        return null;
    }

    @Override
    protected EnergyAdaptorFEtoMJ createNewAdaptor(IEnergyStorage cap) {
        return new EnergyAdaptorFEtoMJ(cap);
    }

    protected boolean shouldAddCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
        return capability == MjAPI.CAP_RECEIVER
                || capability == MjAPI.CAP_CONNECTOR
                || capability == MjAPI.CAP_PASSIVE_PROVIDER
                || capability == MjAPI.CAP_READABLE;
    }

}

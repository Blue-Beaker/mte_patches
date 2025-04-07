package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.MjAPI;
import buildcraft.lib.tile.TileBC_Neptune;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergyAdaptorProviderMJtoFE extends CachingAdaptorProvider<IMjConnector,EnergyAdaptorMJtoFE> implements ICapabilityProvider {
    public final TileBC_Neptune tile;

    public EnergyAdaptorProviderMJtoFE(TileBC_Neptune tile) {
        this.tile=tile;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        if(this.shouldAddCapability(capability,facing)){
            return tile.hasCapability(MjAPI.CAP_CONNECTOR,facing);
        }
        return false;
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if(this.shouldAddCapability(capability,facing) && tile.hasCapability(MjAPI.CAP_CONNECTOR,facing)){
            IMjConnector capability1 = tile.getCapability(MjAPI.CAP_CONNECTOR, facing);
            if(capability1!=null)
                return (T) getOrCreateAdaptor(capability1,facing);
        }
        return null;
    }

    @Override
    protected EnergyAdaptorMJtoFE createNewAdaptor(IMjConnector cap) {
        return new EnergyAdaptorMJtoFE(cap);
    }

    protected boolean shouldAddCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
        return capability == CapabilityEnergy.ENERGY;
    }
}

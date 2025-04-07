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
import java.util.HashMap;

public class EnergyAdaptorProviderMJtoFE implements ICapabilityProvider {
    public final TileBC_Neptune tile;

    public final HashMap<IMjConnector,EnergyAdaptorMJtoFE> adaptors = new HashMap<>();

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
            if(capability1==null) return null;
            return (T) getOrCreateAdaptor(capability1);
        }
        return null;
    }

    private EnergyAdaptorMJtoFE getOrCreateAdaptor(IMjConnector capability1) {
        // Save adaptor for the tile
        if(adaptors.containsKey(capability1)){
            return adaptors.get(capability1);
        }
        EnergyAdaptorMJtoFE energyAdaptorMJtoFE = new EnergyAdaptorMJtoFE(capability1);
        adaptors.put(capability1,energyAdaptorMJtoFE);
        return energyAdaptorMJtoFE;
    }

    protected boolean shouldAddCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
        return capability == CapabilityEnergy.ENERGY;
    }
}

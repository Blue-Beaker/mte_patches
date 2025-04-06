package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.lib.tile.TileBC_Neptune;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergyAdaptorProviderMJtoFE implements ICapabilityProvider {
    public final TileBC_Neptune tile;
    public EnergyAdaptorProviderMJtoFE(TileBC_Neptune tile) {
        this.tile=tile;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        if(this.shouldAddCapability(capability,facing)){
            return tile.hasCapability(MjAPI.CAP_RECEIVER,facing);
        }
        return false;
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if(this.shouldAddCapability(capability,facing)){
            return (T) new EnergyAdaptorMJtoFE(tile.getCapability(MjAPI.CAP_RECEIVER,facing));
        }
        return null;
    }
    
    protected boolean shouldAddCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
        return capability == CapabilityEnergy.ENERGY;
    }
}

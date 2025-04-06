package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjPassiveProvider;
import buildcraft.api.mj.IMjReadable;
import buildcraft.api.mj.IMjReceiver;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

/** Exposes MJ interface on FE machines */
public class EnergyAdaptorFEtoMJ implements IMjReceiver, IMjReadable, IMjPassiveProvider {
    public final IEnergyStorage feStorage;

    public EnergyAdaptorFEtoMJ(IEnergyStorage feStorage) {
        this.feStorage = feStorage;
    }

    @Override
    public long getPowerRequested() {
        return BCUtils.convertFEtoMJ(feStorage.getMaxEnergyStored()-feStorage.getEnergyStored());
    }

    @Override
    public long receivePower(long max, boolean simulate) {
        return max - BCUtils.convertFEtoMJ(feStorage.receiveEnergy(BCUtils.convertMJtoFE(max),simulate));
    }

    @Override
    public boolean canReceive() {
        return feStorage.canReceive();
    }

    @Override
    public boolean canConnect(@NotNull IMjConnector iMjConnector) {
        return true;
    }

    @Override
    public long getStored() {
        return BCUtils.convertFEtoMJ(feStorage.getEnergyStored());
    }

    @Override
    public long getCapacity() {
        return BCUtils.convertFEtoMJ(feStorage.getMaxEnergyStored());
    }

    @Override
    public long extractPower(long min, long max, boolean simulate) {
        long canExtract = BCUtils.convertFEtoMJ(feStorage.extractEnergy(BCUtils.convertMJtoFE(max), true));
        if(canExtract<min) return 0;

        return BCUtils.convertFEtoMJ(feStorage.extractEnergy(BCUtils.convertMJtoFE(max), simulate));
    }
}

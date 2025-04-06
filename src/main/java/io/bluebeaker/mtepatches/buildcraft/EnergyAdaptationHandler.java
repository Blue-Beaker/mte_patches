package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjReadable;
import buildcraft.api.mj.IMjReceiver;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyAdaptationHandler implements IEnergyStorage {
    public final IMjConnector mjConnector;
    public EnergyAdaptationHandler(IMjConnector mjConnector){
        this.mjConnector=mjConnector;
    }
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(mjConnector instanceof IMjReceiver)
            return BCUtils.convertMJtoFE (((IMjReceiver)mjConnector).receivePower(BCUtils.convertFEtoMJ(maxReceive),simulate));
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        if(mjConnector instanceof IMjReadable)
            return BCUtils.convertMJtoFE(((IMjReadable)mjConnector).getStored());
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        if(mjConnector instanceof IMjReadable)
            return BCUtils.convertMJtoFE(((IMjReadable)mjConnector).getCapacity());
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return mjConnector instanceof IMjReceiver;
    }
}

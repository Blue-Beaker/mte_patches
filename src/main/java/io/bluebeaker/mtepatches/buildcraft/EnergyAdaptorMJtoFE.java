package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjReadable;
import buildcraft.api.mj.IMjReceiver;
import net.minecraftforge.energy.IEnergyStorage;

/** Exposes FE interface on MJ machines */
public class EnergyAdaptorMJtoFE implements IEnergyStorage {
    public final IMjConnector mjConnector;
    public final boolean canReceive;
    public final boolean canRead;
    private long mjRemaining = 0;

    public EnergyAdaptorMJtoFE(IMjConnector mjConnector){
        this.mjConnector=mjConnector;
        canReceive=(mjConnector instanceof IMjReceiver && ((IMjReceiver) mjConnector).canReceive());
        canRead=mjConnector instanceof IMjReadable;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(canReceive) {
            IMjReceiver receiver = (IMjReceiver) mjConnector;
            long mjReceive = Math.min(getMaxInsertMJ(), BCUtils.convertFEtoMJ(maxReceive)+mjRemaining);
//            mjReceive=Math.min(mjReceive, receiver.getPowerRequested());
            // Check how many MJ we can convert to precisely
            // Be careful that BC returns remaining energy, but we need the inserted energy

//            long actualMJtoSend = BCUtils.convertFEtoMJ(
//                    BCUtils.convertMJtoFE(
//                            BCUtils.receiveMJGetTransfered(receiver,mjReceive,true)));

            long mjTransfered = BCUtils.receiveMJGetTransfered(receiver,mjReceive, simulate);

            int leftFE = BCUtils.convertMJtoFE(mjTransfered);
            mjRemaining=mjTransfered-BCUtils.convertFEtoMJ(leftFE);
            return leftFE;
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    public long getMaxInsertMJ(){
        if(mjConnector instanceof IMjReadable){
            IMjReadable readable = (IMjReadable) mjConnector;
            return readable.getCapacity()-readable.getStored();
        }
        return Long.MAX_VALUE;
    }

    @Override
    public int getEnergyStored() {
        if(canRead)
            return BCUtils.convertMJtoFE(((IMjReadable)mjConnector).getStored());
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        if(canRead)
            return BCUtils.convertMJtoFE(((IMjReadable)mjConnector).getCapacity());
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return canReceive;
    }
}

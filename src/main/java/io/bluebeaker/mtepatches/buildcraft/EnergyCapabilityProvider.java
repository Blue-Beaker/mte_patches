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

public class EnergyCapabilityProvider implements ICapabilityProvider {
    public final TileBC_Neptune tile;
    public EnergyCapabilityProvider(TileBC_Neptune tile) {
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
            return (T) new EnergyAdaptationHandler(tile.getCapability(MjAPI.CAP_RECEIVER,facing));
        }
        return null;
    }
    
    protected boolean shouldAddCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
        if(capability != CapabilityEnergy.ENERGY) return false;
        if(facing==null) return false;
//        TileEntity neighbourTile = this.tile.getNeighbourTile(facing);
//
//        MTEPatchesMod.getLogger().info("Getting capability of {} from side {} : {}",tile.getPos(),facing,tile.getNeighbourTile(facing));
//
//        if(neighbourTile instanceof IPipeHolder) return false;
        return true;
    }
}

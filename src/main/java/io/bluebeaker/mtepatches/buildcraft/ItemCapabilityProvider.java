package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.inventory.IItemTransactor;
import buildcraft.lib.misc.CapUtil;
import buildcraft.transport.tile.TilePipeHolder;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemCapabilityProvider implements ICapabilityProvider {
    public final @Nonnull TilePipeHolder tile;

    public ItemCapabilityProvider(@Nonnull TilePipeHolder tile) {
        this.tile = tile;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(shouldAddCapability(capability,facing)) {
            return tile.hasCapability(CapUtil.CAP_ITEM_TRANSACTOR, facing);
        }
        return false;
    }

    protected boolean shouldAddCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing){
        if(!(MTEPatchesConfig.buildcraft.itemPipeAcceptEjection && (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))) return false;
        if(facing==null) return false;
//        TileEntity neighbourTile = this.tile.getNeighbourTile(facing);

//        MTEPatchesMod.getLogger().info("Getting capability of {} from side {} : {}",tile.getPos(),facing,tile.getNeighbourTile(facing));

//        if(neighbourTile instanceof IPipeHolder) return false;
        return true;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(shouldAddCapability(capability,facing)){
            IItemTransactor transactor = tile.getCapability(CapUtil.CAP_ITEM_TRANSACTOR, facing);
            if(transactor!=null)
                return (T) new ItemPipeHandler(transactor);
        }
        return null;
    }
}

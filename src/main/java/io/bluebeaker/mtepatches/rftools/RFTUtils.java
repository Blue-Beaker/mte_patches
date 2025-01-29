package io.bluebeaker.mtepatches.rftools;

import mcjty.rftools.blocks.storage.ModularStorageItemInventory;
import mcjty.rftools.blocks.storage.ModularStorageTileEntity;
import mcjty.rftools.blocks.storage.RemoteStorageItemInventory;
import net.minecraft.inventory.Slot;

public class RFTUtils {
    public static boolean isSlotInStorageGUI(Slot slot){
        return slot != null
                && (slot.inventory instanceof ModularStorageTileEntity
                || slot.inventory instanceof ModularStorageItemInventory
                || slot.inventory instanceof RemoteStorageItemInventory);
    }
}

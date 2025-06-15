package io.bluebeaker.mtepatches.mixin.storagedrawers;

import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawer;
import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerAttributesModifiable;
import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerGroup;
import com.jaquadro.minecraft.storagedrawers.api.storage.attribute.LockAttribute;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityDrawers.class,remap = false)
public abstract class MixinTileEntityDrawers {

    @Shadow private IDrawerAttributesModifiable drawerAttributes;

    @Shadow public abstract IDrawerGroup getGroup();

    @Inject(method = "Lcom/jaquadro/minecraft/storagedrawers/block/tile/TileEntityDrawers;takeItemsFromSlot(II)Lnet/minecraft/item/ItemStack;",at = @At("HEAD"),cancellable = true)
    public void clearMarkedItem(int slot, int count, CallbackInfoReturnable<ItemStack> cir){
        if(!MTEPatchesConfig.storageDrawers.clearEmptySlot) return;
        if(drawerAttributes.isItemLocked(LockAttribute.LOCK_EMPTY)
                || drawerAttributes.isItemLocked(LockAttribute.LOCK_POPULATED)) return;

        IDrawer drawer = getGroup().getDrawer(slot);

        if(drawer.getStoredItemCount()==0 && !drawer.isEmpty()){
            // Set count to 0 to trigger slot reset
            drawer.setStoredItem(ItemStack.EMPTY);
        }

    }
}

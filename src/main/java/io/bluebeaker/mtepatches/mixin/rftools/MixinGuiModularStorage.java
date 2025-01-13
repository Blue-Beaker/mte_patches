package io.bluebeaker.mtepatches.mixin.rftools;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mcjty.rftools.blocks.storage.GuiModularStorage;
import mcjty.rftools.blocks.storage.ModularStorageTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GuiModularStorage.class,remap = false)
public abstract class MixinGuiModularStorage {

    @Unique
    private boolean mte_patches$isShiftClickedItem = false;

    @Inject(method = "mouseClicked(III)V",at = @At("RETURN"),remap = true)
    public void onMouseDown(int x, int y, int button, CallbackInfo ci){
        GuiModularStorage guiModularStorage = (GuiModularStorage) (Object)this;
        if(!MTEPatchesConfig.rftools.storageGuiShiftTweak) return;

        if(!guiModularStorage.shiftClickedSlot.isEmpty()){
            if (guiModularStorage.lastClickSlot != null && guiModularStorage.lastClickSlot.inventory instanceof ModularStorageTileEntity)
                mte_patches$isShiftClickedItem = true;
        }
    }

    @Inject(method = "mouseReleased(III)V",at = @At("RETURN"),remap = true)
    public void onMouseUp(int x, int y, int state, CallbackInfo ci){
        if(!MTEPatchesConfig.rftools.storageGuiShiftTweak) return;
        mte_patches$isShiftClickedItem = false;
    }
    // Workaround for storage GUI
    @Inject(method = "updateList()V",at = @At("HEAD"),cancellable = true)
    public void preventUpdate(CallbackInfo ci){
        if(!MTEPatchesConfig.rftools.storageGuiShiftTweak) return;
        if(mte_patches$isShiftClickedItem){
            ci.cancel();
        }
    }

    @Shadow
    protected abstract Slot findEmptySlot();
    @Inject(method = "getSlotAtPosition(II)Lnet/minecraft/inventory/Slot;",at = @At("RETURN"),remap = true,cancellable = true)
    public void insertToEmptySlot(int x, int y, CallbackInfoReturnable<Slot> cir){
        if(!MTEPatchesConfig.rftools.storageGuiInsertTweak) return;
        ItemStack heldStack = Minecraft.getMinecraft().player.inventory.getItemStack();
        Slot hoveredSlot = cir.getReturnValue();
        if(heldStack.isEmpty() || hoveredSlot==null) return;

        ItemStack hoveredStack = hoveredSlot.getStack();
        if(!GuiScreen.isShiftKeyDown()
                && !(Container.canAddItemToSlot(hoveredSlot,heldStack,true)
                && hoveredStack.getCount() < hoveredStack.getMaxStackSize())){
            cir.setReturnValue(this.findEmptySlot());
        }
    }
}

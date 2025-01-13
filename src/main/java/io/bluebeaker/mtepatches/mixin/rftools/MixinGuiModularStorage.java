package io.bluebeaker.mtepatches.mixin.rftools;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mcjty.rftools.blocks.storage.GuiModularStorage;
import mcjty.rftools.blocks.storage.ModularStorageTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiModularStorage.class,remap = false)
public abstract class MixinGuiModularStorage {

    @Unique
    private boolean mte_patches$isShiftClickedItem = false;

    @Inject(method = "mouseClicked(III)V",at = @At("RETURN"),remap = true)
    public void onMouseDown(int x, int y, int button, CallbackInfo ci){
        GuiModularStorage guiModularStorage = (GuiModularStorage) (Object)this;
        if(!MTEPatchesConfig.rftools.storageGuiFix) return;
        if(!guiModularStorage.shiftClickedSlot.isEmpty()){
            if (guiModularStorage.lastClickSlot != null && guiModularStorage.lastClickSlot.inventory instanceof ModularStorageTileEntity)
                mte_patches$isShiftClickedItem = true;
        }
    }

    @Inject(method = "mouseReleased(III)V",at = @At("RETURN"),remap = true)
    public void onMouseUp(int x, int y, int state, CallbackInfo ci){
        if(!MTEPatchesConfig.rftools.storageGuiFix) return;
        mte_patches$isShiftClickedItem = false;
    }
    @Inject(method = "updateList()V",at = @At("HEAD"),cancellable = true)
    public void preventUpdate(CallbackInfo ci){
        if(!MTEPatchesConfig.rftools.storageGuiFix) return;
        if(mte_patches$isShiftClickedItem){
            ci.cancel();
        }
    }
}

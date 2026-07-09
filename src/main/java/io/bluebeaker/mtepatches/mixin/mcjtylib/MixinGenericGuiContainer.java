package io.bluebeaker.mtepatches.mixin.mcjtylib;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mcjty.lib.gui.GenericGuiContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GenericGuiContainer.class,remap = false)
public abstract class MixinGenericGuiContainer extends GuiContainer {
    public MixinGenericGuiContainer(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

//    @Unique
//    private int mte_patches$lastFrameIndex=0;
//    @Unique
//    private int mte_patches$lastCulledCount=0;

    @Inject(method = "drawSlot(Lnet/minecraft/inventory/Slot;)V",at = @At("HEAD"),cancellable = true,remap = true)
    public void drawSlot(Slot slotIn, CallbackInfo ci){
//        MTEPatchesMod.getLogger().info("Slot {} at {},{}",slotIn.slotNumber,slotIn.xPos,slotIn.yPos);
        if (!MTEPatchesConfig.rftools.GUISlotsCulling) return;

        if(slotIn.xPos+this.guiLeft<-16 || slotIn.yPos+this.guiTop<-16
        || slotIn.xPos+this.guiLeft>this.width || slotIn.yPos+this.guiTop>this.height){
            ci.cancel();
//            mte_patches$lastCulledCount++;
        }

//        int frameIndex = mc.getFrameTimer().getIndex();
//        if (frameIndex != mte_patches$lastFrameIndex) {
//            MTEPatchesMod.getLogger().info("Culled {} slots in last frame",mte_patches$lastCulledCount);
//            mte_patches$lastCulledCount=0;
//        };
//        mte_patches$lastFrameIndex=frameIndex;
    }
}

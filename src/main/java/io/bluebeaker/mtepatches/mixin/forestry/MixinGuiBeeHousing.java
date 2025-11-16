package io.bluebeaker.mtepatches.mixin.forestry;

import forestry.apiculture.gui.GuiBeeHousing;
import forestry.apiculture.gui.IContainerBeeHousing;
import forestry.core.gui.ContainerForestry;
import forestry.core.gui.GuiAnalyzerProvider;
import forestry.core.tiles.ITitled;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.forestry;

@Mixin(value = GuiBeeHousing.class,remap = false)
public abstract class MixinGuiBeeHousing<C extends ContainerForestry & IContainerBeeHousing> extends GuiAnalyzerProvider<C> {
    public MixinGuiBeeHousing(String texture, C container, ITitled titled, int buttonX, int buttonY, int screenDistance, boolean hasBoarder, int slots, int firstSlot) {
        super(texture, container, titled, buttonX, buttonY, screenDistance, hasBoarder, slots, firstSlot);
    }

    @Inject(method = "hasErrors",at = @At("HEAD"),cancellable = true)
    private void allowAnalyzerOnErrors(CallbackInfoReturnable<Boolean> cir){
        if(forestry.allowAnalyzerWithErrors)
            cir.setReturnValue(false);
    }
}

package io.bluebeaker.mtepatches.mixin.forestry;

import forestry.core.gui.GuiAnalyzerProvider;
import forestry.core.gui.GuiForestryTitled;
import forestry.core.tiles.ITitled;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.forestry;

@Mixin(value = GuiAnalyzerProvider.class,remap = false)
public abstract class MixinGuiAnalyzerProvider<C extends Container> extends GuiForestryTitled<C> {

    protected MixinGuiAnalyzerProvider(String texture, C container, ITitled titled) {
        super(texture, container, titled);
    }

    @ModifyVariable(method = "drawScreen(IIF)V",at = @At(value = "STORE",ordinal = 0),remap = true)
    private boolean allowAnalyzerOnErrors(boolean errors){
        if(forestry.allowAnalyzerWithErrors) return false;
        return errors;
    }
}

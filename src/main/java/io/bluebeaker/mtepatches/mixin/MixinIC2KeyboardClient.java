package io.bluebeaker.mtepatches.mixin;

import ic2.core.util.KeyboardClient;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = KeyboardClient.class)
public class MixinIC2KeyboardClient {
    @Redirect(method = "sendKeyUpdate()V",at= @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiScreen;allowUserInput:Z",opcode = 180))
    private boolean blockKeyUpdate(GuiScreen screen){
        if(MTEPatchesConfig.ic2.blockKeybindInGUI && !KeyConflictContext.IN_GAME.isActive()){
            return false;
        }
        return screen.allowUserInput;
    }
}

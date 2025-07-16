package io.bluebeaker.mtepatches.mixin.biomesoplenty;

import biomesoplenty.common.handler.FogEventHandler;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FogEventHandler.class,remap = false)
public class MixinFogEventHandler {
    @ModifyVariable(method = "onRenderFog(Lnet/minecraftforge/client/event/EntityViewRenderEvent$RenderFogEvent;)V", at = @At("STORE"), ordinal = 3)
    public int setDistance(int dist){
        return MTEPatchesConfig.bop.fogCheckRange;
    }
    @Inject(method = "onRenderFog(Lnet/minecraftforge/client/event/EntityViewRenderEvent$RenderFogEvent;)V",at = @At("HEAD"),cancellable = true)
    public void removeCustomFog(CallbackInfo ci){
        if(MTEPatchesConfig.bop.disableFog) ci.cancel();
    }
}

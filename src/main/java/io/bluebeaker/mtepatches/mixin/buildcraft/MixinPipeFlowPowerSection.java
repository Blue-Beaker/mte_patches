package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.transport.pipe.flow.PipeFlowPower;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PipeFlowPower.Section.class,remap = false)
public class MixinPipeFlowPowerSection {
    @Final
    @Shadow
    PipeFlowPower this$0;
    @Shadow
    long debugPowerOffered;
    @Shadow
    public long internalNextPower;
    @Inject(method = "receivePowerInternal(J)J", at = @At("HEAD"),cancellable = true)
    private void applyPowerCap(long sent, CallbackInfoReturnable<Long> cir){
        if(!MTEPatchesConfig.buildcraft.limitPipePower || sent==0) return;

        long cappedPower = Math.min(((AccessorPipeFlowPower) this.this$0).getMaxPower(), sent);

        debugPowerOffered+=cappedPower;
        internalNextPower+=cappedPower;
        cir.setReturnValue(sent-cappedPower);
    }
}

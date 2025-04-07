package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.transport.pipe.flow.PipeFlowPower;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Unique
    private long mte_patches$powerLimitRemaining = 0;

    @Inject(method = "receivePowerInternal(J)J", at = @At("HEAD"),cancellable = true)
    private void applyPowerCap(long sent, CallbackInfoReturnable<Long> cir){
        if(!MTEPatchesConfig.buildcraft.limitPipePower || sent==0) return;
        // The limit is an average
        long cappedPower = Math.min(Math.min(mte_patches$powerLimitRemaining, sent)
        // Also add a cap on the internal power
                ,((AccessorPipeFlowPower) this.this$0).getMaxPower()*20-internalNextPower);

        mte_patches$powerLimitRemaining -=cappedPower;

        debugPowerOffered+=cappedPower;
        internalNextPower+=cappedPower;
        cir.setReturnValue(sent-cappedPower);
    }

    @Inject(method = "step()V",at = @At("HEAD"))
    private void step(CallbackInfo ci){
        if(!MTEPatchesConfig.buildcraft.limitPipePower) return;

        long maxPower = ((AccessorPipeFlowPower) this.this$0).getMaxPower();
        mte_patches$powerLimitRemaining =Math.min(maxPower*20, mte_patches$powerLimitRemaining +maxPower);
    }
}

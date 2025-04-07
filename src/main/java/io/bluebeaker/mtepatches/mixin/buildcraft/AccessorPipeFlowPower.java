package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.transport.pipe.flow.PipeFlowPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PipeFlowPower.class,remap = false)
public interface AccessorPipeFlowPower {
    @Accessor
    public long getMaxPower();
}

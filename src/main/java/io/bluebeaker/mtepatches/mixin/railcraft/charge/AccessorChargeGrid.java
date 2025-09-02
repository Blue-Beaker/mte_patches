package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import mods.railcraft.common.util.charge.ChargeNetwork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(value = ChargeNetwork.ChargeGrid.class,remap = false)
public interface AccessorChargeGrid {
    @Accessor("invalid")
    void setInvalid(boolean value);
    @Accessor("invalid")
    boolean getInvalid();
    @Accessor("chargeNodes")
    Set<ChargeNetwork.ChargeNode> getChargeNodes();
    @Invoker("destroy")
    void invokeDestroy(boolean touchNodes);
}

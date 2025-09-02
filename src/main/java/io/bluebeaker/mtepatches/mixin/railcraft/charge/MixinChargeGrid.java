package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import mods.railcraft.common.util.charge.ChargeNetwork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;

@Mixin(value = ChargeNetwork.ChargeGrid.class,remap = false)
public abstract class MixinChargeGrid {
    @Shadow public abstract boolean add(ChargeNetwork.ChargeNode chargeNode);

    @Redirect(method = "addAll(Ljava/util/Collection;)Z",at = @At("HEAD"))
    public void addAll(Collection<? extends ChargeNetwork.ChargeNode> collection){
        for (ChargeNetwork.ChargeNode chargeNode : collection) {
            add(chargeNode);
        }
    }
}

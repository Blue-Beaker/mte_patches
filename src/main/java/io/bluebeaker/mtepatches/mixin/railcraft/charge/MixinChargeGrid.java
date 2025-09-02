package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import mods.railcraft.common.util.charge.ChargeNetwork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;

@Mixin(value = ChargeNetwork.ChargeGrid.class,remap = false)
public abstract class MixinChargeGrid {
    @Shadow public abstract boolean add(ChargeNetwork.ChargeNode chargeNode);

    @Inject(method = "addAll(Ljava/util/Collection;)Z",at = @At("HEAD"),cancellable = true)
    public void addAll(Collection<? extends ChargeNetwork.ChargeNode> collection, CallbackInfoReturnable<Boolean> cir){
        if(!railcraft.chargeNetworkFix) return;
        boolean changed = false;
        for (ChargeNetwork.ChargeNode chargeNode : collection) {
            changed = changed || add(chargeNode);
        }
        cir.setReturnValue(changed);
    }
}

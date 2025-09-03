package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import mods.railcraft.common.util.charge.ChargeNetwork;
import mods.railcraft.common.util.charge.ChargeNetwork.ChargeNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChargeNode.class,remap = false)
public class MixinChargeNode {
    @Shadow private ChargeNetwork.ChargeGrid chargeGrid;

    @Inject(method = "constructGrid",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/charge/ChargeNetwork$ChargeGrid;addAll(Ljava/util/Collection;)Z"))
    public void addSelfToNewGrid(CallbackInfo ci){
        chargeGrid.add(((ChargeNode)(Object)this));
    }
}

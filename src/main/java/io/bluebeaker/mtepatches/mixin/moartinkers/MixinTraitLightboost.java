package io.bluebeaker.mtepatches.mixin.moartinkers;

import com.bartz24.moartinkers.traits.TraitLightboost;
import io.bluebeaker.mtepatches.moartinkers.MTUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TraitLightboost.class,remap = false)
public class MixinTraitLightboost {
    @Inject(method = "miningSpeed",at = @At("HEAD"),cancellable = true)
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event, CallbackInfo ci){
        MTUtils.injectionMiningSpeedBoost(tool, event, ci);
    }
}

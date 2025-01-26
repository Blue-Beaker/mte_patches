package io.bluebeaker.mtepatches.moartinkers;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class MTUtils {
    public static void injectionMiningSpeedBoost(ItemStack tool, PlayerEvent.BreakSpeed event, CallbackInfo ci){
        if (MTEPatchesConfig.moarTinkers.sneakDisablesMiningBoost && event.getEntityPlayer().isSneaking())
            ci.cancel();
    }
}

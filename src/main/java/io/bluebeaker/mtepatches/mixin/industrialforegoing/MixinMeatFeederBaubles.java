package io.bluebeaker.mtepatches.mixin.industrialforegoing;

import com.buuz135.industrial.utils.compat.baubles.MeatFeederBauble;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MeatFeederBauble.class,remap = false)
public abstract class MixinMeatFeederBaubles {
    @Inject(method = "onWornTick",at = @At(value = "HEAD"), cancellable = true)
    private void noTickClient(ItemStack itemstack, EntityLivingBase player, CallbackInfo ci){
        if(MTEPatchesConfig.industrialforegoing.fixMeatFeederTickNull && player.getEntityWorld().isRemote) ci.cancel();
    }
}

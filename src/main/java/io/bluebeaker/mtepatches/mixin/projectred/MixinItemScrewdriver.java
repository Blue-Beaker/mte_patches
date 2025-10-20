package io.bluebeaker.mtepatches.mixin.projectred;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.Utils;
import mrtjp.projectred.core.Configurator;
import mrtjp.projectred.core.ItemScrewdriver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemScrewdriver.class,remap = false)
public abstract class MixinItemScrewdriver {
    @Inject(method = "damageScrewdriver",at = @At("HEAD"))
    public void fireEventOnBreak(EntityPlayer player, ItemStack stack, CallbackInfo ci){
        if(!MTEPatchesConfig.projectred.fixToolBreaking) return;
        if(Configurator.unbreakableScrewdriver()) return;

        EnumHand hand = Utils.getHandForItem(stack, player);
        if(hand==null) return;

        if(stack.getItemDamage()>=stack.getMaxDamage()){
            ForgeEventFactory.onPlayerDestroyItem(player,stack.copy(),hand);
        }
    }
}

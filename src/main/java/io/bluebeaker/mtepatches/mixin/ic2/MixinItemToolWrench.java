package io.bluebeaker.mtepatches.mixin.ic2;

import ic2.core.item.tool.ItemToolWrench;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemToolWrench.class,remap = false)
public abstract class MixinItemToolWrench {
    @Inject(method = "damage",at=@At("HEAD"))
    public void fireBreakingEvent(ItemStack stack, int damage, EntityPlayer player, CallbackInfo ci){
        if(!MTEPatchesConfig.multitoolPatch.IC2Wrench) return;

        EnumHand hand = Utils.getHandForItem(stack, player);
        if(hand==null) return;

        // Check whether the item will break on use
        if(stack.getItemDamage()>=stack.getMaxDamage()){
            ForgeEventFactory.onPlayerDestroyItem(player,stack.copy(),hand);
        }
    }
}

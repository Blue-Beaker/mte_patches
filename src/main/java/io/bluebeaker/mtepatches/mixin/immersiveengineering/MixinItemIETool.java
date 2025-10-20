package io.bluebeaker.mtepatches.mixin.immersiveengineering;

import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.common.items.ItemIETool;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static blusunrize.immersiveengineering.common.Config.IEConfig.Tools.cutterDurabiliy;
import static blusunrize.immersiveengineering.common.Config.IEConfig.Tools.hammerDurabiliy;
import static blusunrize.immersiveengineering.common.items.ItemIETool.HAMMER_META;

@Mixin(value = ItemIETool.class,remap = false)
public abstract class MixinItemIETool {
    // Fire onDestroy event on item destroy. Fixes consuming the whole morph-o-tool/omniwand on destroy
    @Inject(method = "damageIETool",at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMetadata()I"))
    public void fireEventOnDestroy(ItemStack stack, int amount, Random rand, EntityPlayer player, CallbackInfo ci){
        if(player==null) return;
        EnumHand hand;
        if(player.getHeldItemMainhand()==stack) {
            hand=EnumHand.MAIN_HAND;
        } else if (player.getHeldItemOffhand()==stack) {
            hand=EnumHand.OFF_HAND;
        }else {
            return;
        }
        int curDamage = ItemNBTHelper.getInt(stack, Lib.NBT_DAMAGE) + amount;

        if(curDamage >= (stack.getMetadata()==HAMMER_META?hammerDurabiliy: cutterDurabiliy)){
            ForgeEventFactory.onPlayerDestroyItem(player,stack.copy(),hand);
        }
    }

}

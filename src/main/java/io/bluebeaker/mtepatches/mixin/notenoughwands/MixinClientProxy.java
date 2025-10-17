package io.bluebeaker.mtepatches.mixin.notenoughwands;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import romelo333.notenoughwands.Items.GenericWand;
import romelo333.notenoughwands.setup.ClientProxy;

@Mixin(value = ClientProxy.class,remap = false)
public class MixinClientProxy {
    @Redirect(method = "renderWorldLastEvent(Lnet/minecraftforge/client/event/RenderWorldLastEvent;)V",at = @At(value = "INVOKE", target = "Lromelo333/notenoughwands/Items/GenericWand;renderOverlay(Lnet/minecraftforge/client/event/RenderWorldLastEvent;Lnet/minecraft/client/entity/EntityPlayerSP;Lnet/minecraft/item/ItemStack;)V"))
    public void preventGlAttribLeaks(GenericWand instance, RenderWorldLastEvent evt, EntityPlayerSP player, ItemStack wand){
        if(MTEPatchesConfig.notEnoughWands.fixGlAttribLeak){
            // Push GL attributes to prevent leakage
            GL11.glPushAttrib(0xFFFFFFFF);
            instance.renderOverlay(evt, player, wand);
            GL11.glPopAttrib();
        }else {
            instance.renderOverlay(evt, player, wand);
        }
    }
}

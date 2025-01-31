package io.bluebeaker.mtepatches.mixin.industrialforegoing;

import com.buuz135.industrial.proxy.block.upgrade.ConveyorInsertionUpgrade;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ConveyorInsertionUpgrade.class,remap = false)
public class MixinConveyorInsertionUpgrade {
    @Inject(method = "handleEntity(Lnet/minecraft/entity/Entity;)V",at = @At(value = "INVOKE", target = "Lcom/buuz135/industrial/proxy/block/upgrade/ConveyorInsertionUpgrade;getHandlerCapability(Lnet/minecraftforge/common/capabilities/Capability;)Ljava/lang/Object;"),cancellable = true)
    public void fixDuplication(Entity entity, CallbackInfo ci){
        if(MTEPatchesConfig.industrialforegoing.fixConveyorInsertionDuplication && entity.isDead){
            ci.cancel();
        }
    }
}

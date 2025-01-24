package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.transport.pipe.flow.PipeFlowItems;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PipeFlowItems.class,remap = false)
public class MixinPipeFlowItems {
    @Inject(method = "getCapability(Lnet/minecraftforge/common/capabilities/Capability;Lnet/minecraft/util/EnumFacing;)Ljava/lang/Object;",at = @At("HEAD"), cancellable = true)
    public <T> void injected(Capability<T> capability, EnumFacing facing, CallbackInfoReturnable<T> cir){
//        if(capability == CapUtil.CAP_ITEMS){
//            cir.setReturnValue(capability.getDefaultInstance());
//        }
    }
}

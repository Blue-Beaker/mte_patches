package io.bluebeaker.mtepatches.mixin.industrialforegoing;

import com.buuz135.industrial.proxy.event.MeatFeederTickHandler;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MeatFeederTickHandler.class,remap = false)
public abstract class MixinMeatFeederHandler {

    @Redirect(method = "meatTick",at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/capability/IFluidHandlerItem;drain(Lnet/minecraftforge/fluids/FluidStack;Z)Lnet/minecraftforge/fluids/FluidStack;"))
    private static FluidStack fixNull(IFluidHandlerItem instance, FluidStack fluidStack, boolean b){
        if(MTEPatchesConfig.industrialforegoing.fixMeatFeederTickNull && instance==null) return null;
        return instance.drain(fluidStack,b);
    }
}

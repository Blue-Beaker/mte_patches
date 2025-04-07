package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.api.transport.pluggable.PipePluggable;
import buildcraft.api.transport.pluggable.PluggableDefinition;
import buildcraft.transport.plug.PluggablePowerAdaptor;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.buildcraft.EnergyAdaptorMJtoFE;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PluggablePowerAdaptor.class,remap = false)
public abstract class MixinPluggablePowerAdaptor extends PipePluggable {

    public MixinPluggablePowerAdaptor(PluggableDefinition definition, IPipeHolder holder, EnumFacing side) {
        super(definition, holder, side);
    }

    @Inject(method = "getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Ljava/lang/Object;",at=@At("RETURN"),cancellable = true)
    public <T> void getCapability(Capability<T> cap, CallbackInfoReturnable<T> cir){
        if(MTEPatchesConfig.buildcraft.mjToForgeEnergy && cir.getReturnValue()==null && cap == CapabilityEnergy.ENERGY){
            IMjConnector capability = holder.getPipe().getBehaviour().getCapability(MjAPI.CAP_CONNECTOR, side);
            if(capability!=null){
                cir.setReturnValue((T) new EnergyAdaptorMJtoFE(capability));
            }
        }
    }
}

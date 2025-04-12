package io.bluebeaker.mtepatches.mixin.projectred;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.microblock.MicroMaterialRegistry;
import codechicken.multipart.TMultiPart;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.projectred.FakeBlocks;
import mrtjp.projectred.transmission.FramedWirePart;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FramedWirePart.class,remap = false)
public abstract class MixinFramedWirePart extends TMultiPart {
    @Shadow private boolean hasMaterial;

    @Shadow private int material;

    @Inject(method = "getStrength",at = @At("HEAD"),cancellable = true)
    public void getStrength(EntityPlayer player, CuboidRayTraceResult hit, CallbackInfoReturnable<Float> cir){
        if(!MTEPatchesConfig.projectred.fixMiningSpeed) return;
        float strength = FakeBlocks.getStrength(FakeBlocks.WIRE.getDefaultState(), player, this.world());
        if(hasMaterial) {
            cir.setReturnValue(
                    Math.min(strength,MicroMaterialRegistry.getMaterial(this.material).getStrength(player)));
            return;
        }
        cir.setReturnValue(strength);
    }
}

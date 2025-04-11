package io.bluebeaker.mtepatches.mixin.projectred;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.multipart.TMultiPart;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import mrtjp.projectred.ProjectRedRelocation$;
import mrtjp.projectred.relocation.FramePart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FramePart.class,remap = false)
public abstract class MixinFramePart extends TMultiPart {
    @Inject(method = "getStrength",at = @At("HEAD"),cancellable = true)
    public void getStrength(EntityPlayer player, CuboidRayTraceResult hit, CallbackInfoReturnable<Float> cir){
        if(!MTEPatchesConfig.projectred.fixMiningSpeed) return;
        cir.setReturnValue(ForgeHooks.blockStrength(ProjectRedRelocation$.MODULE$.blockFrame().getDefaultState(),player, this.world(), new BlockPos(0,-1,0)));
    }
}

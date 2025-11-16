package io.bluebeaker.mtepatches.mixin.ic2;

import ic2.core.ExplosionIC2;
import io.bluebeaker.mtepatches.LoadedModChecker;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.ftbu.FTBUUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ExplosionIC2.class,remap = false)
public abstract class MixinExplosionIC2 extends Explosion {

    public MixinExplosionIC2(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, affectedPositions);
    }

    @Inject(method = "destroyUnchecked(IIIZ)V",at = @At("HEAD"),cancellable = true)
    private void checkFTBPermissions(int x, int y, int z, boolean noDrop, CallbackInfo ci){
        if(!MTEPatchesConfig.ic2.explosionProtection || !LoadedModChecker.ftbutilities.isLoaded()) return;
        if(world.getMinecraftServer()==null) return;
        if(!FTBUUtils.canChunkExplode(x>>4,z>>4, world.provider.getDimension(),world.getMinecraftServer())){
            ci.cancel();
        };
    }
}

package io.bluebeaker.mtepatches.mixin_core;

import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Explosion.class)
public interface AccessorExplosion {
    @Accessor
    World getWorld();
}

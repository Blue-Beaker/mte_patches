package io.bluebeaker.mtepatches.mixin.railcraft;

import mods.railcraft.common.carts.Train;
import net.minecraft.world.World;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = Train.Manager.class,remap = false)
public interface AccessorTrainManager {
    @Accessor("instances")
    static Map<World, Train.Manager> getInstances() {
        throw new NotImplementedException("Not implemented, injection failed?");
    }
}

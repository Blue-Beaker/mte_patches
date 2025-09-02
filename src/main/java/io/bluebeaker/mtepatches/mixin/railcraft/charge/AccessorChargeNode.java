package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import mods.railcraft.common.util.charge.ChargeNetwork;
import mods.railcraft.common.util.charge.ChargeNetwork.ChargeGrid;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ChargeNetwork.ChargeNode.class,remap = false)
public interface AccessorChargeNode {
    @Accessor("invalid")
    void setInvalid(boolean value);
    @Accessor("chargeGrid")
    void setGrid(ChargeGrid grid);
    @Accessor("pos")
    BlockPos getPos();
}

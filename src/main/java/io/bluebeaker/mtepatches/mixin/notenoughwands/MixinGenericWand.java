package io.bluebeaker.mtepatches.mixin.notenoughwands;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import romelo333.notenoughwands.Items.GenericWand;

@Mixin(value = GenericWand.class,remap = false)
public abstract class MixinGenericWand extends Item {
    @Shadow protected int needsrf;

    @Redirect(method = "extractEnergy",at= @At(value = "FIELD", target = "Lromelo333/notenoughwands/Items/GenericWand;needsrf:I",opcode = Opcodes.GETFIELD))
    // Fix acceleration wand not costing more energy for faster modes and automations
    public int removeRFCap(GenericWand instance){
        if(MTEPatchesConfig.notEnoughWands.fixWandCostMultiplier)
            return Integer.MAX_VALUE;
        return this.needsrf;
    }
}

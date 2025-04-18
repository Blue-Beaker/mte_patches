package io.bluebeaker.mtepatches.mixin.forestry;

import forestry.farming.multiblock.FarmController;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Stack;

@Mixin(value = FarmController.class,remap = false)
public class MixinFarmController {

    @Shadow @Final private Stack<ItemStack> pendingProduce;
    public void addPendingProduce(ItemStack stack) {
        if(MTEPatchesConfig.forestry.multiFarmSoilReplaceFix)
            this.pendingProduce.push(stack);
    }
}

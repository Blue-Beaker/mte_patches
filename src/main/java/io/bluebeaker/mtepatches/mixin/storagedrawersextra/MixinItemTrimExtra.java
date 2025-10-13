package io.bluebeaker.mtepatches.mixin.storagedrawersextra;

import com.jaquadro.minecraft.storagedrawersextra.item.ItemTrimExtra;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.storageDrawers;

@Mixin(value = ItemTrimExtra.class)
public abstract class MixinItemTrimExtra extends ItemBlock {
    public MixinItemTrimExtra(Block block) {
        super(block);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    public void subTypes(Block block, CallbackInfo ci){
        if(storageDrawers.extraTrimSubtypes)
            this.setHasSubtypes(true);
    }
}

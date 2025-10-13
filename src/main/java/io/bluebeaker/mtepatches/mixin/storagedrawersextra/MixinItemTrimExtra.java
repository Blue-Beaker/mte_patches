package io.bluebeaker.mtepatches.mixin.storagedrawersextra;

import com.jaquadro.minecraft.storagedrawersextra.item.ItemTrimExtra;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
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

    @ModifyConstant(method = "getTranslationKey(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",constant = @Constant(stringValue = "tile.extra_trim"))
    public String translationKey(String value){
        if(storageDrawers.extraDrawersTranslation && !I18n.hasKey(value))
            return "tile.extraTrim";
        return value;
    }
    @ModifyConstant(method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",constant = @Constant(stringValue = "storageDrawers.material"))
    public String materialTranslationKey(String value){
        if(storageDrawers.extraDrawersTranslation && !I18n.hasKey(value))
            return "storagedrawers.material";
        return value;
    }
    @ModifyConstant(method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",constant = @Constant(stringValue = "storageDrawers.material."))
    public String materialTranslationKey2(String value){
        if(storageDrawers.extraDrawersTranslation && !I18n.hasKey(value))
            return "storagedrawers.material.";
        return value;
    }

}

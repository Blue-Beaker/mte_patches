package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.railcraft.InventoryUtils;
import mods.railcraft.common.blocks.machine.manipulator.TileItemUnloader;
import mods.railcraft.common.util.inventory.IInventoryComposite;
import mods.railcraft.common.util.inventory.wrappers.InventoryMapper;
import mods.railcraft.common.util.misc.Predicates;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;

@Mixin(value = TileItemUnloader.class,remap = false)
public abstract class MixinTileItemUnloader {
    @Unique
    int mte_patches$cooldown = 0;
    @Redirect(method = "clearInv()V",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/inventory/wrappers/InventoryMapper;moveOneItemTo(Lmods/railcraft/common/util/inventory/IInventoryComposite;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack redirectMoveOneItem2(InventoryMapper instance, IInventoryComposite dest){
        if(railcraft.itemMoveInterval==0) return instance.moveOneItemTo(dest);
        ItemStack itemStack = InventoryUtils.redirectMoveOneItem(instance, dest, Predicates.alwaysTrue());
        mte_patches$cooldown=railcraft.itemMoveInterval;
        return itemStack;
    }
    //Add transfer cooldown
    @Inject(method = "clearInv()V",at = @At("HEAD"),cancellable = true)
    public void transferCooldown(CallbackInfo ci){
        if(mte_patches$cooldown>0){
            mte_patches$cooldown--;
            ci.cancel();
        }
    }
}

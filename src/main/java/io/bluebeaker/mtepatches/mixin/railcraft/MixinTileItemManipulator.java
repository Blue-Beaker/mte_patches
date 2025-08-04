package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.railcraft.InventoryUtils;
import mods.railcraft.common.blocks.machine.manipulator.TileItemManipulator;
import mods.railcraft.common.blocks.machine.manipulator.TileManipulatorCart;
import mods.railcraft.common.util.inventory.IInventoryComposite;
import mods.railcraft.common.util.misc.Predicates;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;
import java.util.function.Predicate;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;

@Mixin(value = TileItemManipulator.class,remap = false)
public abstract class MixinTileItemManipulator extends TileManipulatorCart {
    @Unique
    int mte_patches$cooldown = 0;
    @Unique
    boolean mte_patches$lastProcessing = false;
    @Unique
    UUID mte_patches$lastCartID;

    @Redirect(method = "processCart(Lnet/minecraft/entity/item/EntityMinecart;)V",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/inventory/IInventoryComposite;moveOneItemTo(Lmods/railcraft/common/util/inventory/IInventoryComposite;)Lnet/minecraft/item/ItemStack;",ordinal = 0))
    public ItemStack redirectMoveOneItem0(IInventoryComposite instance, IInventoryComposite dest){
        if(railcraft.itemMoveInterval==0) return instance.moveOneItemTo(dest);
        return mte_patches$moveItems(instance, dest, Predicates.alwaysTrue());
    }
    @Redirect(method = "processCart(Lnet/minecraft/entity/item/EntityMinecart;)V",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/inventory/IInventoryComposite;moveOneItemTo(Lmods/railcraft/common/util/inventory/IInventoryComposite;Ljava/util/function/Predicate;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack redirectMoveOneItem1(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter){
        if(railcraft.itemMoveInterval==0) return instance.moveOneItemTo(dest,filter);
        return mte_patches$moveItems(instance, dest, filter);
    }

    @Redirect(method = "moveItem(Ljava/util/stream/Stream;)V",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/inventory/IInventoryComposite;moveOneItemTo(Lmods/railcraft/common/util/inventory/IInventoryComposite;Ljava/util/function/Predicate;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack redirectMoveOneItem2(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter){
        if(railcraft.itemMoveInterval==0) return instance.moveOneItemTo(dest,filter);
        return mte_patches$moveItems(instance, dest, filter);
    }
    // Optimized method to move items
    @Unique
    private ItemStack mte_patches$moveItems(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter) {
        ItemStack itemStack = InventoryUtils.redirectMoveOneItem(instance, dest, filter);
        mte_patches$cooldown=InventoryUtils.getCooldownFromMovedStack(itemStack);
        mte_patches$lastProcessing=!itemStack.isEmpty();
        return itemStack;
    }

    //Add transfer cooldown
    @Inject(method = "processCart(Lnet/minecraft/entity/item/EntityMinecart;)V",at = @At("HEAD"),cancellable = true)
    public void transferCooldown(EntityMinecart cart, CallbackInfo ci){
        if(railcraft.itemMoveInterval==0) return;
        //Reset cooldown if cart changed
        UUID cartID = cart.getUniqueID();
        if(cartID!= mte_patches$lastCartID){
            mte_patches$lastCartID=cartID;
            mte_patches$cooldown=0;
        }
        if(mte_patches$cooldown>0){
            mte_patches$cooldown--;
            setProcessing(mte_patches$lastProcessing);
            ci.cancel();
        }
    }

    //Reset cooldown on cart release
    @Intrinsic
    protected void onNoCart() {
        mte_patches$cooldown=0;
    }
}

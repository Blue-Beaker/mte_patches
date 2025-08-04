package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.railcraft.InventoryUtils;
import mods.railcraft.common.blocks.machine.manipulator.TileItemManipulator;
import mods.railcraft.common.blocks.machine.manipulator.TileManipulatorCart;
import mods.railcraft.common.util.inventory.IInventoryComposite;
import mods.railcraft.common.util.misc.Predicates;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(value = TileItemManipulator.class,remap = false)
public abstract class MixinTileItemManipulator extends TileManipulatorCart {
    @Redirect(method = "processCart(Lnet/minecraft/entity/item/EntityMinecart;)V",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/inventory/IInventoryComposite;moveOneItemTo(Lmods/railcraft/common/util/inventory/IInventoryComposite;)Lnet/minecraft/item/ItemStack;",ordinal = 0))
    public ItemStack redirectMoveOneItem0(IInventoryComposite instance, IInventoryComposite dest){
        return InventoryUtils.redirectMoveOneItem(instance,dest, Predicates.alwaysTrue());
    }
    @Redirect(method = "processCart(Lnet/minecraft/entity/item/EntityMinecart;)V",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/inventory/IInventoryComposite;moveOneItemTo(Lmods/railcraft/common/util/inventory/IInventoryComposite;Ljava/util/function/Predicate;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack redirectMoveOneItem1(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter){
        return InventoryUtils.redirectMoveOneItem(instance,dest,filter);
    }

    @Redirect(method = "moveItem(Ljava/util/stream/Stream;)V",at = @At(value = "INVOKE", target = "Lmods/railcraft/common/util/inventory/IInventoryComposite;moveOneItemTo(Lmods/railcraft/common/util/inventory/IInventoryComposite;Ljava/util/function/Predicate;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack redirectMoveOneItem2(IInventoryComposite instance, IInventoryComposite dest, Predicate<ItemStack> filter){
        return InventoryUtils.redirectMoveOneItem(instance,dest,filter);
    }
}

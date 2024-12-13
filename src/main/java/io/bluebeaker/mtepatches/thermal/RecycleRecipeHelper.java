package io.bluebeaker.mtepatches.thermal;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class RecycleRecipeHelper {
    public static final RecycleRecipeHelper SMELTER = new RecycleRecipeHelper();
    public static final RecycleRecipeHelper SAWMILL = new RecycleRecipeHelper();

    public final Set<Item> RECYCLE_ITEMS = new HashSet<>();
    public boolean matches(ItemStack stack){
        return RECYCLE_ITEMS.contains(stack.getItem());
    }
    public boolean add(ItemStack stack){
        return RECYCLE_ITEMS.add(stack.getItem());
    }
}

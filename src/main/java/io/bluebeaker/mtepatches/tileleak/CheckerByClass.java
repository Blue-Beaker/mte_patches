package io.bluebeaker.mtepatches.tileleak;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CheckerByClass implements ITileChecker {
    private static final List<Class<?>> blockClasses = new ArrayList<>();

    @Override
    public boolean checkTile(Block block) {
        Class<?> aClass = block.getClass();
        while (aClass != Block.class) {
            if (blockClasses.contains(aClass)) {
                return true;
            }
            aClass=aClass.getSuperclass();
        }
        return false;
    }
    public void add(String blockClass) {
        try {
            CheckerByClass.blockClasses.add(Class.forName(blockClass));
        } catch (ClassNotFoundException ignored) {

        }
    }
}

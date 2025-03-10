package io.bluebeaker.mtepatches.mixin.ic2;

import ic2.core.Platform;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Platform.class,remap = false)
public interface AccessorPlatform {
    @Invoker(value = "getMessageComponents")
    ITextComponent[] getMessageComponents(Object... args);
}

package io.bluebeaker.mtepatches.mixin.ic2;

import ic2.core.Platform;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Platform.class,remap = false)
public abstract class MixinPlatform {
    // I don't know why but this just fixed the StackOverFlowError on dedicated server
}

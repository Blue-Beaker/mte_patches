package io.bluebeaker.mtepatches.mixin.ic2;

import ic2.core.Platform;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Platform.class,remap = false)
public abstract class MixinPlatform {

    // I don't know why but this just fixed the StackOverFlowError on dedicated server
    protected ITextComponent[] getMessageComponents(Object... args) {
        ITextComponent[] encodedArgs = new ITextComponent[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String && ((String)args[i]).startsWith("ic2.")) {
                encodedArgs[i] = new TextComponentTranslation((String)args[i]);
            } else {
                encodedArgs[i] = new TextComponentString(args[i].toString());
            }
        }
        return encodedArgs;
    }
}

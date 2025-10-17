package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.lib.tile.TileBC_Neptune;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileBC_Neptune.class,remap = false)
public class MixinTileBC {
    @Inject(method = "writeToNBT(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound;",at = @At("RETURN"),remap = true)
    public void removeAdditionalPlayerDataFromNBT(NBTTagCompound nbt, CallbackInfoReturnable<NBTTagCompound> cir){
        if(!MTEPatchesConfig.buildcraft.cleanPlayerProfileNBT) return;
        // Remove excess player's additional properties stored to NBT of BuildCraft Tiles
        if(nbt.hasKey("owner",10)){
            NBTTagCompound owner = nbt.getCompoundTag("owner");
            owner.removeTag("Properties");
        }
    }
}

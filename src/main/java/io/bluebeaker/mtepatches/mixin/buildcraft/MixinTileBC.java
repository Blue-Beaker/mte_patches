package io.bluebeaker.mtepatches.mixin.buildcraft;

import buildcraft.lib.tile.TileBC_Neptune;
import com.mojang.authlib.GameProfile;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.Utils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileBC_Neptune.class,remap = false)
public abstract class MixinTileBC extends TileEntity {
    @Shadow private GameProfile owner;

    @Shadow public abstract void markChunkDirty();

    @Redirect(method = "writeToNBT(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound;",at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTUtil;writeGameProfile(Lnet/minecraft/nbt/NBTTagCompound;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/nbt/NBTTagCompound;"),remap = true)
    public NBTTagCompound getProfile(NBTTagCompound tagCompound, GameProfile profile){
        // Don't get player's additional properties when storing to NBT of BuildCraft Tiles
        if(MTEPatchesConfig.buildcraft.cleanPlayerProfileNBT) {
            return Utils.writePlayerNameAndID(new NBTTagCompound(),profile);
        }
        return NBTUtil.writeGameProfile(new NBTTagCompound(),profile);
    }

    @Inject(method = "readFromNBT(Lnet/minecraft/nbt/NBTTagCompound;)V",at = @At("RETURN"),remap = true)
    public void stripFromExistingNBT(NBTTagCompound nbt, CallbackInfo ci){
        // Clean player's properties from existing tile NBTs
        if(MTEPatchesConfig.buildcraft.cleanPlayerProfileNBT && nbt.getCompoundTag("owner").hasKey("Properties")){
            this.markChunkDirty();
        }
    }
}

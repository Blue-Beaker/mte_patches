package io.bluebeaker.mtepatches;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;

import javax.annotation.Nullable;
import java.net.URLConnection;

public class Utils {
    public static void setTimeout(URLConnection urlConnection) {
        urlConnection.setConnectTimeout(MTEPatchesConfig.connectionTimeout.timeout);
        urlConnection.setReadTimeout(MTEPatchesConfig.connectionTimeout.timeout);
        if(MTEPatchesMod.getLogger()!=null)
            MTEPatchesMod.getLogger().info("Set connection timeout to {}ms for {}",urlConnection.getConnectTimeout(),urlConnection.getURL());
        else
            System.out.printf("[mtepatches]:  Set connection timeout to %dms for %s\n",urlConnection.getConnectTimeout(),urlConnection.getURL());
    }

    public static NBTTagCompound writePlayerNameAndID(NBTTagCompound tag, GameProfile profile){
        if (!StringUtils.isNullOrEmpty(profile.getName()))
        {
            tag.setString("Name", profile.getName());
        }
        if (profile.getId() != null)
        {
            tag.setString("Id", profile.getId().toString());
        }
        return tag;
    }

    /** Get which hand the stack is on.
     * @param stack The stack to check
     * @param player The player to check
     * @return The hand which the stack is on, or null if the stack isn't on any hand, or player is null.
     */
    @Nullable
    public static EnumHand getHandForItem(ItemStack stack, @Nullable EntityPlayer player){
        if(player==null) return null;
        if(player.getHeldItemMainhand()==stack) {
            return EnumHand.MAIN_HAND;
        } else if (player.getHeldItemOffhand()==stack) {
            return EnumHand.OFF_HAND;
        }else {
            return null;
        }
    }
}

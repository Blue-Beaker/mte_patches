package io.bluebeaker.mtepatches;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;

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
}

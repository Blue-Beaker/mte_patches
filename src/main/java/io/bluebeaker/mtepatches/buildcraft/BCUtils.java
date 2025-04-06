package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.MjAPI;
import io.bluebeaker.mtepatches.MTEPatchesConfig;

public class BCUtils {

    public static int convertMJtoFE(long mj){
        return (int) ((double) mj /MjAPI.MJ*MTEPatchesConfig.buildcraft.mjToForgeEnergyRatio);
    }

    public static long convertFEtoMJ(int fe){
        return (long) (fe*MjAPI.MJ/MTEPatchesConfig.buildcraft.mjToForgeEnergyRatio);
    }
}

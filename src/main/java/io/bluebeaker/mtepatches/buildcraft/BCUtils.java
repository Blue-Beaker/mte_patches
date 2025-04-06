package io.bluebeaker.mtepatches.buildcraft;

import io.bluebeaker.mtepatches.MTEPatchesConfig;

public class BCUtils {
    public static int convertMJtoFE(long mj){
        return (int) (mj*MTEPatchesConfig.buildcraft.mjToForgeEnergyRatio);
    }
    public static long convertFEtoMJ(int fe){
        return (long) (fe/MTEPatchesConfig.buildcraft.mjToForgeEnergyRatio);
    }
}

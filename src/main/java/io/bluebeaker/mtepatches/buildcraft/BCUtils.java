package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.MjAPI;
import io.bluebeaker.mtepatches.MTEPatchesConfig;

public class BCUtils {

    private static double mjToFERatio = getMjToFERatio();

    public static void updateValues(){
        mjToFERatio=MTEPatchesConfig.buildcraft.mjToForgeEnergyRatio/MjAPI.MJ;
    }
    public static double getMjToFERatio(){
        return mjToFERatio;
    }

    public static int convertMJtoFE(long mj){
        return (int) (mj*mjToFERatio);
    }

    public static long convertFEtoMJ(int fe){
        return (long) (fe/mjToFERatio);
    }
}

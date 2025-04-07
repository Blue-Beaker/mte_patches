package io.bluebeaker.mtepatches.buildcraft;

import buildcraft.api.mj.IMjReceiver;
import buildcraft.api.mj.MjAPI;
import io.bluebeaker.mtepatches.MTEPatchesConfig;

public class BCUtils {

    private static double mjToFERatio;
    private static long minimumMjToInsert;

    public static void updateValues(){
        mjToFERatio=MTEPatchesConfig.buildcraft.mjToForgeEnergyRatio/MjAPI.MJ;
    }
    public static double getMjToFERatio(){
        return mjToFERatio;
    }

    public static int convertMJtoFE(long mj){
        double fe = (mj * mjToFERatio);
        return fe<Integer.MAX_VALUE ? (int)fe : Integer.MAX_VALUE;
    }

    public static long convertFEtoMJ(int fe){
        if(mjToFERatio==0) return 0;
        return (long) (fe/mjToFERatio);
    }
    public static long receiveMJGetTransfered(IMjReceiver receiver, long mjReceive, boolean simulate){
        return mjReceive - receiver.receivePower(mjReceive,simulate);
    }

}

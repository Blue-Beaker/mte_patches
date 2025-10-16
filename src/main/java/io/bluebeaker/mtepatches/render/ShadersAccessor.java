package io.bluebeaker.mtepatches.render;

import io.bluebeaker.mtepatches.MTEPatchesMod;

import java.lang.reflect.Field;

public class ShadersAccessor {
    private static Field isShadowPass;
    static {
        try {
            Class<?> ofShaders = Class.forName("net.optifine.shaders.Shaders");
            isShadowPass= ofShaders.getDeclaredField("isShadowPass");
            isShadowPass.setAccessible(true);
            MTEPatchesMod.getLogger().info("Optifine shaders found. Enabling render optimization for shaders.");
        } catch (Throwable e) {
        }
    }

    /** Common method to check for shader's shadow pass.
     * @return Whether Optifine is found, and it's rendering shadow for shaders.
     */
    public static boolean getIsRenderingShadowPass(){
        try {
            return isShadowPass != null && (boolean) isShadowPass.get(null);
        } catch (Throwable e) {
            return false;
        }
    }
}

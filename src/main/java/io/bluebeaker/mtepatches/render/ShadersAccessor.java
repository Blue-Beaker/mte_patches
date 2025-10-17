package io.bluebeaker.mtepatches.render;

import io.bluebeaker.mtepatches.MTEPatchesMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
@SideOnly(Side.CLIENT)
public class ShadersAccessor {
    private static Field isShadowPass;
    private static Field isShaderPackInitialized;
    static {
        try {
            Class<?> ofShaders = Class.forName("net.optifine.shaders.Shaders");
            isShadowPass= ofShaders.getDeclaredField("isShadowPass");
            isShadowPass.setAccessible(true);
            isShaderPackInitialized = ofShaders.getDeclaredField("isShaderPackInitialized");
            isShaderPackInitialized.setAccessible(true);
            MTEPatchesMod.getLogger().info("Optifine shaders found. Enabling render optimization for shaders.");
        } catch (Throwable e) {
        }
    }

    /** Common method to check for shader's shadow pass.
     * @return Whether Optifine is found, and it's rendering shadow for shaders.
     */
    public static boolean getIsRenderingShadowPass(){
        try {
            return isShadowPass != null && isShadowPass.getBoolean(null);
        } catch (Throwable e) {
            return false;
        }
    }

    /** Common method to check for shaders.
     * @return Whether Optifine is found, and a shaderpack is active.
     */
    public static boolean getIsShadersOn(){
        try {
            return isShaderPackInitialized != null && isShaderPackInitialized.getBoolean(null);
        } catch (Throwable e) {
            return false;
        }
    }
}

package io.bluebeaker.mtepatches.render;

import io.bluebeaker.mtepatches.MTEPatchesMod;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;
import java.util.Set;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

public class RenderSkipRegistry {
    public static final RenderSkipRegistry INSTANCE = new RenderSkipRegistry();
    public final Set<Class<?>> skipFar = new HashSet<>();
    public final Set<Class<?>> skipFarTiles = new HashSet<>();
    public final Set<Class<?>> skipShadows = new HashSet<>();
    public final Set<Class<?>> skipShadowsTiles = new HashSet<>();

    public void reloadConfigs(){
        if(FMLCommonHandler.instance().getSide()!= Side.CLIENT) return;
        skipFar.clear();
        skipShadows.clear();
        parseClassToConfigs(render.farCulling.extra_tesrs, skipFar, skipFarTiles, "farCulling_custom");
        parseClassToConfigs(render.shadowCulling.extra_tesrs, skipShadows, skipShadowsTiles, "shadowCulling_custom");
    }

    private static void parseClassToConfigs(String[] configLines, Set<Class<?>> tesrClasses, Set<Class<?>> teClasses, String sectionName) {
        for (String s : configLines) {
            try {
                String trim = s.split("#")[0].trim();
                if(trim.isEmpty()) continue;
                Class<?> aClass = Class.forName(trim);
                if(TileEntitySpecialRenderer.class.isAssignableFrom(aClass)) {
                    tesrClasses.add(aClass);
                } else if (TileEntity.class.isAssignableFrom(aClass)) {
                    teClasses.add(aClass);
                }else {
                    MTEPatchesMod.getLogger().warn("Class {} in {} is neither a TileEntity or a TileEntitySpecialRenderer, skipping it", s, sectionName);
                }
            } catch (Throwable e) {
                MTEPatchesMod.getLogger().warn("Error loading {}: config line \"{}\" : ", sectionName,s,e);
            }
        }
    }
}

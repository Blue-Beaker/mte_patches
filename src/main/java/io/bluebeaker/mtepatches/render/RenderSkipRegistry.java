package io.bluebeaker.mtepatches.render;

import io.bluebeaker.mtepatches.MTEPatchesMod;

import java.util.HashSet;
import java.util.Set;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

public class RenderSkipRegistry {
    public static final RenderSkipRegistry INSTANCE = new RenderSkipRegistry();
    public final Set<Class<?>> skipFar = new HashSet<>();
    public final Set<Class<?>> skipShadows = new HashSet<>();
    public final Set<Class<?>> skipFarTiles = new HashSet<>();
    public final Set<Class<?>> skipShadowsTiles = new HashSet<>();

    public void reloadConfigs(){
        skipFar.clear();
        skipShadows.clear();
        readConfigClasses(render.skipFarAway.extra_tesrs, skipFar,"skipFarAway_tesr");
        readConfigClasses(render.skipShadows.extra_tesrs, skipShadows,"skipShadows_tesr");
        readConfigClasses(render.skipFarAway.extra_tiles, skipFarTiles,"skipFarAway_tiles");
        readConfigClasses(render.skipShadows.extra_tiles, skipShadowsTiles,"skipShadows_tiles");
    }
    private void readConfigClasses(String[] entries, Set<Class<?>> classes, String section){
        for (String s : entries) {
            try {
                Class<?> aClass = Class.forName(s);
                classes.add(aClass);
            } catch (Throwable e) {
                MTEPatchesMod.getLogger().warn("Error loading {}: config line \"{}\" : ",section,s,e);
            }
        }
    }
}

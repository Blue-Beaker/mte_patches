package io.bluebeaker.mtepatches.render;

import io.bluebeaker.mtepatches.MTEPatchesMod;

import java.util.HashSet;
import java.util.Set;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.render;

public class RenderSkipRegistry {
    public static final RenderSkipRegistry INSTANCE = new RenderSkipRegistry();
    public final Set<Class<?>> skipFar = new HashSet<>();
    public final Set<Class<?>> skipShaders = new HashSet<>();

    public void reloadConfigs(){
        skipFar.clear();
        skipShaders.clear();
        readConfigClasses(render.renderers_skip_far,skipFar,"renderers_skip_far");
        readConfigClasses(render.renderers_skip_shaders,skipShaders,"renderers_skip_shaders");
    }
    private void readConfigClasses(String[] entries, Set<Class<?>> classes, String section){
        for (String s : entries) {
            try {
                Class<?> aClass = Class.forName(s);
                classes.add(aClass);
            } catch (Throwable e) {
                MTEPatchesMod.getLogger().error("Error loading {}: at renderer class \"{}\" : ",section,s,e);
            }
        }
    }
}

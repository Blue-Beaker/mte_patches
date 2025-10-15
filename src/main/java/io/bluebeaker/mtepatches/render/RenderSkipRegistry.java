package io.bluebeaker.mtepatches.render;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.MTEPatchesMod;

import java.util.HashSet;
import java.util.Set;

public class RenderSkipRegistry {
    public static final RenderSkipRegistry INSTANCE = new RenderSkipRegistry();
    public final Set<Class<?>> skipFar = new HashSet<>();
    public final Set<Class<?>> skipShaders = new HashSet<>();

    public void reloadConfigs(){
        for (String s : MTEPatchesConfig.render.renderers_skip_far) {
            try {
                Class<?> aClass = Class.forName(s);
                skipFar.add(aClass);
            } catch (ClassNotFoundException e) {
                MTEPatchesMod.getLogger().error("Error loading renderers_skip_far: at renderer class \"{}\" : ",s,e);
            }
        }

    }
}

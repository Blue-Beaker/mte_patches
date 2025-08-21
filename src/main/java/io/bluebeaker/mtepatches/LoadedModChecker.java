package io.bluebeaker.mtepatches;

import net.minecraftforge.fml.common.Loader;

public enum LoadedModChecker {
    railcraft("railcraft"),
    ic2("ic2"),
    forestry("forestry"),
    thermalexpansion("thermalexpansion"),
    rftools("rftools"),
    buildcraftcore("buildcraftcore"),
    buildcrafttransport("buildcrafttransport"),
    moartinkers("moartinkers"),
    industrialforegoing("industrialforegoing"),
    projectredcore("projectred-core"),
    projectredexpansion("projectred-expansion"),
    projectredintegration("projectred-integration"),
    projectredillumination("projectred-illumination");

    public final String modid;
    private boolean isLoaded = false;

    LoadedModChecker(String modid){
        this.modid=modid;
    }

    public boolean isLoaded(){
        if(this.isLoaded)
            return true;
        this.isLoaded = Loader.isModLoaded(this.modid);
        return this.isLoaded;
    }
    public String getVersion(){
        return Loader.instance().getIndexedModList().get(this.modid).getVersion();
    }
}

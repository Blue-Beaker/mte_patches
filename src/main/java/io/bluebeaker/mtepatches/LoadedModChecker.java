package io.bluebeaker.mtepatches;

import net.minecraftforge.fml.common.Loader;

public enum LoadedModChecker {
    biomesoplenty("biomesoplenty"),
    buildcraftcore("buildcraftcore"),
    buildcrafttransport("buildcrafttransport"),
    forestry("forestry"),
    ic2("ic2"),
    ic2cropplugin("ic2cropplugin"),
    industrialforegoing("industrialforegoing"),
    moartinkers("moartinkers"),
    projectredcore("projectred-core"),
    projectredexpansion("projectred-expansion"),
    projectredillumination("projectred-illumination"),
    projectredintegration("projectred-integration"),
    railcraft("railcraft"),
    rftools("rftools"),
    stevescarts("stevescarts"),
    storagedrawers("storagedrawers"),
    storagedrawersextra("storagedrawersextra"), thermalexpansion("thermalexpansion");

    public final String modid;
    private boolean isLoaded = false;

    LoadedModChecker(String modid) {
        this.modid = modid;
    }

    public boolean isLoaded() {
        if (this.isLoaded)
            return true;
        this.isLoaded = Loader.isModLoaded(this.modid);
        return this.isLoaded;
    }

    public String getVersion() {
        return Loader.instance().getIndexedModList().get(this.modid).getVersion();
    }
}

package io.bluebeaker.mtepatches;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MTEPatchesMixins implements ILateMixinLoader {
    public static Map<String,LoadedModChecker> modMixinsToCheck = new HashMap<>();
    static {
        addMixinsForMod(LoadedModChecker.biomesoplenty);
        addMixinsForMod("buildcraft",LoadedModChecker.buildcraftcore);
        addMixinsForMod(LoadedModChecker.forestry);
        addMixinsForMod(LoadedModChecker.ic2);
        addMixinsForMod(LoadedModChecker.ic2cropplugin);
        addMixinsForMod(LoadedModChecker.industrialforegoing);
        addMixinsForMod(LoadedModChecker.moartinkers);
        addMixinsForMod("projectred",LoadedModChecker.projectredcore);
        addMixinsForMod(LoadedModChecker.railcraft);
        addMixinsForMod(LoadedModChecker.rftools);
        addMixinsForMod(LoadedModChecker.stevescarts);
        addMixinsForMod(LoadedModChecker.storagedrawers);
        addMixinsForMod(LoadedModChecker.storagedrawersextra);
        addMixinsForMod(LoadedModChecker.thermaldynamics);
        addMixinsForMod(LoadedModChecker.thermalexpansion);
    }

    @Override
    public List<String> getMixinConfigs() {
        List<String> mixins = new ArrayList<>();
        mixins.add("mixins.mtepatches.json");
        mixins.addAll(modMixinsToCheck.keySet());
        return mixins;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        if(modMixinsToCheck.containsKey(mixinConfig)){
            return modMixinsToCheck.get(mixinConfig).isLoaded();
        }
        return true;
    }

    private static void addMixinsForMod(String name,LoadedModChecker mod){
        modMixinsToCheck.put("mixins.mtepatches."+name+".json",mod);
    }
    private static void addMixinsForMod(LoadedModChecker mod){
        addMixinsForMod(mod.name(),mod);
    }
}

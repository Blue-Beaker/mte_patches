package io.bluebeaker.mtepatches.buildcraft;

import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;

public abstract class CachingAdaptorProvider<K,V> {
    protected final HashMap<K,V> adaptors = new HashMap<>();
    protected final EnumMap<EnumFacing,K> sides = new EnumMap<>(EnumFacing.class);
    protected @Nullable K keyNullFacing;

    protected V getOrCreateAdaptor(K cap, EnumFacing facing) {
        if(!MTEPatchesConfig.buildcraft.cacheAdaptors)
            return createNewAdaptor(cap);
        V adaptor = adaptors.get(cap);
        // Save adaptor for the tile
        if(adaptor!=null){
            return adaptor;
        }
        adaptor = createNewAdaptor(cap);
        adaptors.put(cap,adaptor);
        // If the capability is refreshed, remove the previous one from cache
        if(facing!=null){
            if(sides.containsKey(facing)){
                adaptors.remove(sides.get(facing));
            }
            sides.put(facing,cap);
        }else {
            if(keyNullFacing !=null){
                adaptors.remove(keyNullFacing);
            }
            keyNullFacing =cap;
        }

        return adaptor;
    }

    protected abstract V createNewAdaptor(K cap);
}

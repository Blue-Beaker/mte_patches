package io.bluebeaker.mtepatches.buildcraft;

import java.util.HashMap;

public abstract class CachingAdaptorProvider<K,V> {
    public final HashMap<K,V> adaptors = new HashMap<>();

    protected V getOrCreateAdaptor(K cap) {
        // Save adaptor for the tile
        if(adaptors.containsKey(cap)){
            return adaptors.get(cap);
        }
        V adaptor = createNewAdaptor(cap);
        adaptors.put(cap,adaptor);
        return adaptor;
    }

    protected abstract V createNewAdaptor(K cap);
}

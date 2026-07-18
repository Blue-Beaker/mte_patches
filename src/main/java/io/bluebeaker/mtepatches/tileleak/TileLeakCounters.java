package io.bluebeaker.mtepatches.tileleak;

import io.bluebeaker.mtepatches.utils.Counter;

public class TileLeakCounters {
    /**
     * Counter for blocks marked as 'hasTileEntity' in the patch.
     */
    public static final Counter<Class<?>> MARKED_COUNTER = new Counter<>();
}

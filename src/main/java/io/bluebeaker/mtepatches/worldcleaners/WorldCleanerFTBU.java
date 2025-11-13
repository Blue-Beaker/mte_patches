package io.bluebeaker.mtepatches.worldcleaners;

import com.feed_the_beast.ftbutilities.ranks.Ranks;

public class WorldCleanerFTBU implements IWorldCleaner{
    @Override
    public void removeAllWorldReferences() {
        Ranks.INSTANCE=null;
    }
}

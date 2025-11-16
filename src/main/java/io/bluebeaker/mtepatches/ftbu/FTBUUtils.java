package io.bluebeaker.mtepatches.ftbu;

import com.feed_the_beast.ftblib.lib.math.ChunkDimPos;
import com.feed_the_beast.ftbutilities.FTBUtilitiesConfig;
import com.feed_the_beast.ftbutilities.data.ClaimedChunk;
import com.feed_the_beast.ftbutilities.data.ClaimedChunks;
import com.feed_the_beast.ftbutilities.data.FTBUtilitiesUniverseData;
import net.minecraft.server.MinecraftServer;

public class FTBUUtils {
    public static boolean canChunkExplode(int x, int z, int dim, MinecraftServer server){
        ChunkDimPos pos = new ChunkDimPos(x, z, dim);
        
        if (dim == 0 && FTBUtilitiesConfig.world.safe_spawn && FTBUtilitiesUniverseData.isInSpawn(server, pos)) {
            return false;
        } else if (FTBUtilitiesConfig.world.enable_explosions.isDefault()) {
            ClaimedChunk chunk = ClaimedChunks.isActive() ? ClaimedChunks.instance.getChunk(pos) : null;
            return chunk == null || chunk.hasExplosions();
        } else {
            return FTBUtilitiesConfig.world.enable_explosions.isTrue();
        }
    }
}

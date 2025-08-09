package io.bluebeaker.mtepatches;

import mods.railcraft.common.blocks.multi.TileMultiBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RCMultiblockPatch {
    
    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent event) {
        if(!MTEPatchesConfig.railcraft.multiblockSyncFix) return;
        Chunk chunk = event.getChunkInstance();
        RCMultiblockPatch patch = new RCMultiblockPatch(chunk);
        patch.updateRecursive(chunk);
         if(!patch.markedChunks.isEmpty())
            MTEPatchesMod.logDebug("Marked chunks: {}",patch.markedChunks.toString());
    }

    public Set<Chunk> markedChunks = new HashSet<Chunk>();

    private RCMultiblockPatch(Chunk chunk) {
    }

    private void updateNeighborsForMultiblock(Chunk chunk) {

        Map<BlockPos, TileEntity> tileentities = chunk.getTileEntityMap();
        World world = chunk.getWorld();

        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        boolean hasUpdateTile = false;

        boolean updateWest=false;
        boolean updateEast=false;
        boolean updateNorth=false;
        boolean updateSouth=false;
        for (BlockPos pos : tileentities.keySet()) {
            TileEntity te = tileentities.get(pos);
            if (te instanceof TileMultiBlock) {
                hasUpdateTile = true;
                int localX = pos.getX() & 15;
                int localZ = pos.getZ() & 15;
                if (localX == 0)
                    updateWest=true;
                else if (localX == 15)
                    updateEast=true;
                if (localZ == 0)
                    updateNorth=true;
                else if (localZ == 15)
                    updateSouth=true;
            }
        }
        if(hasUpdateTile){
            if(updateWest)
            this.updateRecursive(world.getChunk(chunkX - 1, chunkZ));
            if (updateEast)
            this.updateRecursive(world.getChunk(chunkX + 1, chunkZ));
            if (updateNorth)
            this.updateRecursive(world.getChunk(chunkX, chunkZ - 1));
            if (updateSouth)
            this.updateRecursive(world.getChunk(chunkX, chunkZ + 1));
        }
    }

    private void updateRecursive(Chunk chunk){
        if(this.markedChunks.contains(chunk)) return;
        updateTilesInChunk(chunk);
        this.markedChunks.add(chunk);
        this.updateNeighborsForMultiblock(chunk);
    }

    private static void updateTilesInChunk(Chunk chunk) {
        if (!chunk.isLoaded())
            return;
        Map<BlockPos, TileEntity> tileentities = chunk.getTileEntityMap();
        for (BlockPos pos : tileentities.keySet()) {
            TileEntity te = tileentities.get(pos);
            if (te instanceof TileMultiBlock) {
                TileMultiBlock multiBlock = ((TileMultiBlock) te);
                multiBlock.onLoad();
                multiBlock.sendUpdateToClient();
            }
        }
        
    }
}

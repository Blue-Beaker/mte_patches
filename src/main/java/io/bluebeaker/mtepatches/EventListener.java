package io.bluebeaker.mtepatches;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventListener {
    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event){
        Map<BlockPos,TileEntity> tileentities = event.getChunk().getTileEntityMap();
        for (BlockPos pos:tileentities.keySet()){
            
        }
    }
}

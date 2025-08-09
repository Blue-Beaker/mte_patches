package io.bluebeaker.mtepatches.mixin.railcraft;

import io.bluebeaker.mtepatches.MTEPatchesMod;
import mods.railcraft.common.util.misc.AdjacentTileCache;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;

@Mixin(value = AdjacentTileCache.class,remap = false)
public abstract class MixinAdjacentTileCache {
    @Shadow @Final private TileEntity source;

    @Shadow public abstract void purge();

    @Unique
    int mtepatches_last_world_time = 0;
    @Inject(method = "refresh()V",at = @At("HEAD"))
    public void forcePurgeAfterInterval(CallbackInfo ci){
        if(railcraft.tileCachePurgeInterval==0) return;
        //Do not count at client-side
        if(source.getWorld().isRemote) return;
        MinecraftServer server = source.getWorld().getMinecraftServer();
        if(server==null) return;

        int newTime = server.getTickCounter();
        if(newTime>mtepatches_last_world_time && newTime-mtepatches_last_world_time<=railcraft.tileCachePurgeInterval) return;
        mtepatches_last_world_time=newTime;

        purge();
        MTEPatchesMod.logDebug("Purging tile at {}",source.getPos());
    }
}

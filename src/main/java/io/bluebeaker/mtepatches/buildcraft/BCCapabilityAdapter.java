package io.bluebeaker.mtepatches.buildcraft;


import buildcraft.transport.tile.TilePipeHolder;
import io.bluebeaker.mtepatches.MTEPatchesMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BCCapabilityAdapter {
    public static BCCapabilityAdapter INSTANCE = new BCCapabilityAdapter();
    private BCCapabilityAdapter(){

    }
    private ResourceLocation itemCap;
    public void init(){
        this.itemCap = new ResourceLocation(MTEPatchesMod.MODID,"BC_itemCapability");
    }
    @SubscribeEvent
    public void subscribe(AttachCapabilitiesEvent<TileEntity> event){
        if(event.getObject() instanceof TilePipeHolder){
            event.addCapability(this.itemCap,
                    new ItemCapabilityProvider((TilePipeHolder) event.getObject()));
        }
    }
}

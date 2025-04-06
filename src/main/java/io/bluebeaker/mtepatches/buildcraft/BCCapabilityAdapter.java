package io.bluebeaker.mtepatches.buildcraft;


import buildcraft.lib.tile.TileBC_Neptune;
import buildcraft.transport.tile.TilePipeHolder;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
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
    private ResourceLocation powerCap;
    public void init(){
        this.itemCap = new ResourceLocation(MTEPatchesMod.MODID,"BC_itemCapability");
        this.powerCap = new ResourceLocation(MTEPatchesMod.MODID,"BC_powerCapability");
    }
    @SubscribeEvent
    public void subscribe(AttachCapabilitiesEvent<TileEntity> event){
        TileEntity tile = event.getObject();
        if(MTEPatchesConfig.buildcraft.itemPipeAcceptEjection && tile instanceof TilePipeHolder){
            event.addCapability(this.itemCap,
                    new ItemCapabilityProvider((TilePipeHolder) tile));
        }
        if(MTEPatchesConfig.buildcraft.mjToForgeEnergyRatio >0 && tile instanceof TileBC_Neptune){
            event.addCapability(this.powerCap,
                    new EnergyCapabilityProvider((TileBC_Neptune)tile));
        }
    }
}

package io.bluebeaker.mtepatches.buildcraft;


import buildcraft.lib.tile.TileBC_Neptune;
import buildcraft.transport.tile.TilePipeHolder;
import io.bluebeaker.mtepatches.LoadedModChecker;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.MTEPatchesMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BCCapabilityAdapter {
    public static BCCapabilityAdapter INSTANCE = new BCCapabilityAdapter();
    private BCCapabilityAdapter(){
        this.itemCap = new ResourceLocation(MTEPatchesMod.MODID,"BC_itemCapability");
        this.powerMJtoFE = new ResourceLocation(MTEPatchesMod.MODID,"BC_MJtoFE");
        this.powerFEtoMJ = new ResourceLocation(MTEPatchesMod.MODID,"BC_FEtoMJ");
    }
    private ResourceLocation itemCap;
    private ResourceLocation powerMJtoFE;
    private ResourceLocation powerFEtoMJ;
    public void init(){
//        this.itemCap = new ResourceLocation(MTEPatchesMod.MODID,"BC_itemCapability");
//        this.powerMJtoFE = new ResourceLocation(MTEPatchesMod.MODID,"BC_MJtoFE");
//        this.powerFEtoMJ = new ResourceLocation(MTEPatchesMod.MODID,"BC_FEtoMJ");
    }
    @SubscribeEvent(priority = EventPriority.LOW)
    public void subscribe(AttachCapabilitiesEvent<TileEntity> event){
        TileEntity tile = event.getObject();
        if(MTEPatchesConfig.buildcraft.itemPipeAcceptEjection && LoadedModChecker.buildcrafttransport.isLoaded() && tile instanceof TilePipeHolder){
            event.addCapability(this.itemCap,
                    new ItemCapabilityProvider((TilePipeHolder) tile));
        }
        if(MTEPatchesConfig.buildcraft.mjToForgeEnergy){
            if(tile instanceof TileBC_Neptune){
                event.addCapability(this.powerMJtoFE,
                        new EnergyAdaptorProviderMJtoFE((TileBC_Neptune)tile));
            }else{
                event.addCapability(this.powerFEtoMJ,
                        new EnergyAdaptorProviderFEtoMJ(tile));
            }
        }
    }
}

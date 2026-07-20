package io.bluebeaker.mtepatches.buildcraft;


import buildcraft.lib.item.ItemBC_Neptune;
import buildcraft.lib.tile.TileBC_Neptune;
import buildcraft.transport.tile.TilePipeHolder;
import io.bluebeaker.mtepatches.LoadedModChecker;
import io.bluebeaker.mtepatches.MTEPatchesConfig;
import io.bluebeaker.mtepatches.MTEPatchesMod;
import io.bluebeaker.mtepatches.utils.ConsumableFluidProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BCCapabilityAdapter {
    public static final ConsumableFluidProvider WATER_GEL_FLUID_PROVIDER = new ConsumableFluidProvider(new FluidStack(FluidRegistry.WATER, 1000));
    
    public static BCCapabilityAdapter INSTANCE = new BCCapabilityAdapter();

    private BCCapabilityAdapter(){
        this.itemCap = new ResourceLocation(MTEPatchesMod.MODID,"BC_itemCapability");
        this.powerMJtoFE = new ResourceLocation(MTEPatchesMod.MODID,"BC_MJtoFE");
        this.powerFEtoMJ = new ResourceLocation(MTEPatchesMod.MODID,"BC_FEtoMJ");
        this.fluidContainerItem = new ResourceLocation(MTEPatchesMod.MODID,"BC_fluidContainerItem");
    }

    private final ResourceLocation itemCap;
    private final ResourceLocation powerMJtoFE;
    private final ResourceLocation powerFEtoMJ;
    private final ResourceLocation fluidContainerItem;
    public static final ResourceLocation BC_GEL = new ResourceLocation("buildcraftfactory:gel");

    @SubscribeEvent(priority = EventPriority.LOW)
    public void subscribeTiles(AttachCapabilitiesEvent<TileEntity> event){
        TileEntity tile = event.getObject();
        if(MTEPatchesConfig.buildcraft.itemPipeAcceptEjection && LoadedModChecker.buildcrafttransport.isLoaded() && tile instanceof TilePipeHolder){
            event.addCapability(this.itemCap,
                    new ItemTransactorAdaptor((TilePipeHolder) tile));
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

    @SubscribeEvent(priority = EventPriority.LOW)
    public void subscribeItem(AttachCapabilitiesEvent<ItemStack> event){
        if(!MTEPatchesConfig.buildcraft.disposableWaterGel) return;
        ItemStack itemstack = event.getObject();
        if (itemstack==null) return;
        Item item = itemstack.getItem();
        if(item instanceof ItemBC_Neptune && item.getRegistryName() != null && item.getRegistryName().equals(BC_GEL)){
            event.addCapability(this.fluidContainerItem, WATER_GEL_FLUID_PROVIDER);
        }
    }
}

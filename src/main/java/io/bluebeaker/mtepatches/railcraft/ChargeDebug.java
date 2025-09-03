package io.bluebeaker.mtepatches.railcraft;

import io.bluebeaker.mtepatches.mixin.railcraft.charge.AccessorChargeGrid;
import io.bluebeaker.mtepatches.mixin.railcraft.charge.AccessorChargeNode;
import mods.railcraft.api.charge.Charge;
import mods.railcraft.api.charge.IChargeBlock;
import mods.railcraft.common.plugins.forge.WorldPlugin;
import mods.railcraft.common.util.charge.ChargeNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;

@Mod.EventBusSubscriber
public class ChargeDebug {
    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event){
        if(!railcraft.chargeNetworkFixDebug) return;
        World world = event.getWorld();
        if(world.isRemote) return;
        EntityPlayer player = event.getEntityPlayer();

        BlockPos pos = event.getPos();
        IBlockState state = WorldPlugin.getBlockState(world, pos);
        if (state.getBlock() instanceof IChargeBlock) {
            ChargeNetwork.ChargeNode node = (ChargeNetwork.ChargeNode) ((IChargeBlock) state.getBlock()).getMeterAccess(Charge.distribution, state, world, pos);
            if (node.isValid() && !node.isGridNull()) {
//                StringBuilder nodesMessage = new StringBuilder();
                for (ChargeNetwork.ChargeNode chargeNode : ((AccessorChargeGrid) node.getGrid()).getChargeNodes()) {
                    BlockPos pos1 = ((AccessorChargeNode) chargeNode).getPos();
                    summonDebugParticle(world, EnumParticleTypes.REDSTONE, pos1);
//                    nodesMessage.append(pos1.getX()+","+pos1.getY()+","+pos1.getZ()+";");
                }
                ChargeNetwork.ChargeGrid grid = node.getGrid();
                player.sendStatusMessage(new TextComponentString("Size="+grid.size()+", Grid="+grid),true);
            }
        }
    }


    public static void summonDebugParticle(World world1, EnumParticleTypes particle, BlockPos pos1, Block block){
        summonDebugParticle(world1, particle,pos1, ItemBlock.getItemFromBlock(block),0);
    }
    public static void summonDebugParticle(World world1, EnumParticleTypes particle, BlockPos pos1, Block block, int meta){
        summonDebugParticle(world1, particle,pos1, ItemBlock.getItemFromBlock(block),meta);
    }
    public static void summonDebugParticle(World world1, EnumParticleTypes particle, BlockPos pos1){
        if(!railcraft.chargeNetworkFixDebug) return;
        if(world1 instanceof WorldServer){
            ((WorldServer)world1).spawnParticle(particle, pos1.getX()+0.5, pos1.getY()+1, pos1.getZ()+0.5, 5, 0.0, 0.0,0.0,0);
        }
    }
    public static void summonDebugParticle(World world1, EnumParticleTypes particle, BlockPos pos1, Item item, int meta) {
        if(!railcraft.chargeNetworkFixDebug) return;
        int id = Item.REGISTRY.getIDForObject(item);
        if(world1 instanceof WorldServer){
            ((WorldServer)world1).spawnParticle(particle, pos1.getX()+0.5, pos1.getY()+1, pos1.getZ()+0.5, 5, 0.0, 0.0,0.0,0,id,meta);
        }
    }
}

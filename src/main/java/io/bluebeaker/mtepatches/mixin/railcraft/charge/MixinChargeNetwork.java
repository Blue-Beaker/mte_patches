package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import com.llamalad7.mixinextras.sugar.Local;
import io.bluebeaker.mtepatches.railcraft.ChargeDebug;
import mods.railcraft.api.charge.IChargeBlock;
import mods.railcraft.common.util.charge.ChargeNetwork;
import mods.railcraft.common.util.charge.ChargeNetwork.ChargeGrid;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;

@Mixin(value = ChargeNetwork.class,remap = false)
public abstract class MixinChargeNetwork {
    @Shadow @Final private Map<BlockPos, ChargeNetwork.ChargeNode> nodes;
    @Shadow protected abstract void forConnections(BlockPos pos, BiConsumer<BlockPos, IBlockState> action);
    @Shadow @Final private WeakReference<World> world;

    @Shadow @Final private ChargeGrid NULL_GRID;

    @Shadow public abstract ChargeGrid grid(BlockPos pos);

    @Unique
    private final Set<ChargeGrid> mte_patches$changedGrids = new HashSet<>();
    @Unique
    private final Set<BlockPos> mte_patches$extraBlocksToUpdate = new HashSet<>();

    @Inject(method = "tick",at = @At("TAIL"))
    private void afterAddedNodes(CallbackInfo ci, @Local(ordinal = 0) Set<BlockPos> added){
        if(!railcraft.chargeNetworkFix) return;
        World world1 = world.get();
        if(world1==null) return;

        for (ChargeGrid grid1 : mte_patches$changedGrids) {
            if(((AccessorChargeGrid)grid1).getInvalid()) continue;

            Map<BlockPos,IBlockState> blockStateMap = new HashMap<>();
            // Get blocks neighbouring to the grid1
            for (ChargeNetwork.ChargeNode chargeNode : grid1) {
                forConnections(((AccessorChargeNode)chargeNode).getPos(), blockStateMap::put);
            }
            // Register every block in the map
            for (BlockPos pos2 : blockStateMap.keySet()) {

                IBlockState state = blockStateMap.get(pos2);
                Block block = state.getBlock();
                if(!(block instanceof IChargeBlock)) continue;

                ChargeNetwork.ChargeNode node2 = nodes.get(pos2);
                if(node2!=null){
                    ChargeGrid grid2 = node2.getGrid();
                    if(grid1==grid2) continue;
//                    if(grid2.isEmpty()){
//                        ((AccessorChargeGrid) grid2).invokeDestroy(false);
//                    }
                    for (ChargeNetwork.ChargeNode chargeNode : grid2) {
                        BlockPos pos3 = ((AccessorChargeNode) chargeNode).getPos();
                        ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.BLOCK_CRACK, pos3, Blocks.GOLD_BLOCK);
                        ((AccessorChargeNode) chargeNode).setInvalid(true);
                        mte_patches$extraBlocksToUpdate.add(pos3);
                    }
                    BlockPos pos = ((AccessorChargeNode) node2).getPos();
                    ChargeDebug.summonDebugParticle(world1, EnumParticleTypes.VILLAGER_HAPPY, pos);
//                    grid1.addAll(grid2);
//                    ((AccessorChargeGrid) grid2).invokeDestroy(false);
//                    ((AccessorChargeNode)node2).setInvalid(true);
                    ((AccessorChargeNode)node2).setGrid(NULL_GRID);
                    mte_patches$extraBlocksToUpdate.add(pos2);
                }else {
                    ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.BLOCK_CRACK, pos2, Blocks.EMERALD_BLOCK);
                }
                ((IChargeBlock) block).registerNode(state,world1,pos2);
            }
        }
        mte_patches$changedGrids.clear();

        // Update extra blocks
        for (BlockPos pos3 : mte_patches$extraBlocksToUpdate){
            IBlockState state = world1.getBlockState(pos3);
            Block block = state.getBlock();
            if(!(block instanceof IChargeBlock)) continue;
            ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.WATER_SPLASH, pos3);
            ((IChargeBlock) block).registerNode(state,world1,pos3);
        }
        mte_patches$extraBlocksToUpdate.clear();
    }

    // Mark grid of added node
    @Inject(method = "addNodeImpl",at = @At("TAIL"))
    private void afterAddedNode(BlockPos pos, ChargeNetwork.ChargeNode node,CallbackInfo ci){
        World world1 = world.get();
        if(!railcraft.chargeNetworkFix) return;
        mte_patches$changedGrids.add(node.getGrid());
        if(!node.getGrid().contains(node)){
            node.getGrid().add(node);

            ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.LAVA, pos);
        }
    }
    // Mark changed blocks around removed node
    @Inject(method = "removeNodeImpl",at = @At("HEAD"))
    private void beforeRemoveNode(BlockPos pos, CallbackInfo ci){
        if(!railcraft.chargeNetworkFix) return;
        ChargeNetwork.ChargeNode chargeNode = nodes.get(pos);
        if(chargeNode==null) return;

        for (EnumFacing value : EnumFacing.values()) {
            BlockPos pos1 = pos.offset(value);
            mte_patches$extraBlocksToUpdate.add(pos1);
//            ChargeNetwork.ChargeNode node1 = nodes.get(pos1);

//            if(node1!=null && node1.isValid()){
//                mte_patches$changedGrids.add(node1.getGrid());
//            }
        }
    }

    @Inject(method = "needsNode",at = @At("RETURN"), cancellable = true)
    public void setNodeWithNullGrid(BlockPos pos, IChargeBlock.ChargeSpec chargeSpec, CallbackInfoReturnable<Boolean> cir, @Local ChargeNetwork.ChargeNode node){
        if(!railcraft.chargeNetworkFix || cir.getReturnValue()) return;
        if(node.getGrid().isNull() || node.getGrid().isEmpty()) cir.setReturnValue(true);
    }
}

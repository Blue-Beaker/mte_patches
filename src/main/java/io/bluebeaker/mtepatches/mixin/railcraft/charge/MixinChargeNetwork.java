package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableSet;
import com.llamalad7.mixinextras.sugar.Local;
import io.bluebeaker.mtepatches.railcraft.ChargeDebug;
import mods.railcraft.api.charge.IChargeBlock;
import mods.railcraft.common.util.charge.ChargeNetwork;
import mods.railcraft.common.util.charge.ChargeNetwork.ChargeGrid;
import mods.railcraft.common.util.charge.ChargeNetwork.ChargeNode;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.BiConsumer;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;
import static mods.railcraft.common.util.charge.ChargeNetwork.CONNECTION_MAPS;

@Mixin(value = ChargeNetwork.class,remap = false)
public abstract class MixinChargeNetwork {
    @Shadow @Final private Map<BlockPos, ChargeNode> nodes;
    @Shadow protected abstract void forConnections(BlockPos pos, BiConsumer<BlockPos, IBlockState> action);
    @Shadow @Final private WeakReference<World> world;

    @Shadow @Final private ChargeGrid NULL_GRID;

    @Shadow public abstract ChargeGrid grid(BlockPos pos);

    @Shadow public abstract boolean addNode(BlockPos pos, IBlockState state);

    @Shadow @Final private Set<ChargeGrid> grids;

    @Shadow @Nullable protected abstract IChargeBlock.ChargeSpec getChargeSpec(IBlockState state, BlockPos pos);

    @Shadow public abstract void removeNode(BlockPos pos);

    @Unique
    private final Set<ChargeGrid> mte_patches$changedGrids = new HashSet<>();
    @Unique
    private final Set<BlockPos> mte_patches$destroyedUpdatePos = new HashSet<>();
    @Unique
    private final Set<BlockPos> mte_patches$addNodeUpdated = new HashSet<>();
    @Unique
    private final Set<BlockPos> mte_patches$lastUpdatedBlocks = new HashSet<>();

    @Inject(method = "tick",at = @At("TAIL"))
    private void afterAddedNodes(CallbackInfo ci, @Local(ordinal = 0) Set<BlockPos> added){
        if(!railcraft.chargeNetworkFix) return;
        World world1 = world.get();
        if(world1==null) return;
        for (ChargeGrid grid1 : mte_patches$changedGrids) {
            if(((AccessorChargeGrid)grid1).getInvalid()) continue;

            Map<BlockPos,IBlockState> blockStateMap = new HashMap<>();
            // Get blocks neighbouring to the grid1
            for (ChargeNode chargeNode : grid1) {
                forConnections(((AccessorChargeNode)chargeNode).getPos(), blockStateMap::put);
            }
            // Register every block in the map
            for (BlockPos pos2 : blockStateMap.keySet()) {

                IBlockState state = blockStateMap.get(pos2);
                Block block = state.getBlock();
                if(!(block instanceof IChargeBlock)) continue;

                ChargeNode node2 = nodes.get(pos2);
                if(node2!=null){
                    ChargeGrid grid2 = node2.getGrid();
                    if(grid1==grid2) {
                        ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.BLOCK_CRACK, pos2, Blocks.BROWN_SHULKER_BOX);
                        continue;
                    };

                    if(grid2.isEmpty()){
                      grid1.add(node2);
                      grids.remove(grid2);
                    }
                    for (ChargeNode chargeNode : grid2) {
                        BlockPos pos3 = ((AccessorChargeNode) chargeNode).getPos();
                        ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.BLOCK_CRACK, pos3, Blocks.GOLD_BLOCK);
//                        ((AccessorChargeNode) chargeNode).setInvalid(true);
//                        mte_patches$extraBlocksToUpdate.add(pos3);
                    }
                    grid1.addAll(grid2);
                    ((AccessorChargeGrid) grid2).invokeDestroy(false);

                    BlockPos pos = ((AccessorChargeNode) node2).getPos();
                    ChargeDebug.summonDebugParticle(world1, EnumParticleTypes.VILLAGER_HAPPY, pos);
//                    grid1.addAll(grid2);
//                    ((AccessorChargeGrid) grid2).invokeDestroy(false);
//                    ((AccessorChargeNode)node2).setInvalid(true);
//                    ((AccessorChargeNode)node2).setGrid(NULL_GRID);
//                    mte_patches$extraBlocksToUpdate.add(pos2);
                }else {
                    ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.BLOCK_CRACK, pos2, Blocks.EMERALD_BLOCK);
                }
                addNode(pos2,state);
            }
        }
        mte_patches$changedGrids.clear();

        mte_patches$updateAddedConnections(world1);

        mte_patches$updateRemovedConnections(world1);

        // Remove empty grids
        grids.removeIf(ForwardingCollection::isEmpty);
    }

    @Unique
    private void mte_patches$updateAddedConnections(World world1) {
        // Update extra blocks
        // But don't update the one updated in last tick
        mte_patches$addNodeUpdated.removeAll(mte_patches$lastUpdatedBlocks);
        for (BlockPos pos3 : mte_patches$addNodeUpdated){
            IBlockState state = world1.getBlockState(pos3);
            Block block = state.getBlock();
            if(!(block instanceof IChargeBlock)) continue;
            ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.WATER_SPLASH, pos3);
            addNode(pos3,state);
        }
        mte_patches$lastUpdatedBlocks.clear();
        mte_patches$addNodeUpdated.clear();
    }

    @Unique
    private void mte_patches$updateRemovedConnections(World world1) {
        // Update connections of removed nodes
        for (BlockPos updatePos : ImmutableSet.copyOf(mte_patches$destroyedUpdatePos)) {
//            removeNode(updatePos);
            ChargeNode node = nodes.remove(updatePos);
            if(node==null) continue;
            ((AccessorChargeNode)node).setInvalid(true);
            addNode(updatePos, world1.getBlockState(updatePos));
        }
        mte_patches$destroyedUpdatePos.clear();
    }

    // Mark grid of added node
    @Inject(method = "addNodeImpl",at = @At("HEAD"))
    private void afterAddNode(BlockPos pos, ChargeNode node, CallbackInfo ci){
        World world1 = world.get();
        if(!railcraft.chargeNetworkFix) return;
        mte_patches$lastUpdatedBlocks.add(pos);
        mte_patches$changedGrids.add(node.getGrid());
//        if(!node.getGrid().contains(node)){
//            node.getGrid().add(node);
//            ChargeDebug.summonDebugParticle(world1,EnumParticleTypes.LAVA, pos);
//        }
        forConnections(pos,(pos2,state)->{
            mte_patches$addNodeUpdated.add(pos2);
            ChargeDebug.summonDebugParticle(world.get(),EnumParticleTypes.BLOCK_CRACK,pos,Blocks.PINK_SHULKER_BOX);
        });
    }
    // Mark changed blocks around removed node
    @Inject(method = "removeNodeImpl",at = @At("HEAD"))
    private void beforeRemoveNode(BlockPos pos, CallbackInfo ci){
        if(!railcraft.chargeNetworkFix) return;
        if(mte_patches$lastUpdatedBlocks.contains(pos)) return;
        mte_patches$lastUpdatedBlocks.add(pos);
        ChargeNode chargeNode = nodes.get(pos);
        if(chargeNode==null) return;

        World world1 = world.get();
        if(world1==null) return;

        IChargeBlock.ChargeSpec chargeSpec = chargeNode.getChargeSpec();
        ForwardingMap<Vec3i, EnumSet<IChargeBlock.ConnectType>> connectionMap = CONNECTION_MAPS.get(chargeSpec.getConnectType());
        connectionMap.forEach((offset,types)->{
            BlockPos pos1 = pos.add(offset);
            mte_patches$destroyedUpdatePos.add(pos1);
        });
//        for (EnumFacing value : EnumFacing.values()) {
//            BlockPos pos1 = pos.offset(value);
//            mte_patches$destroyedGridsPos.add(pos1);
//            ChargeNetwork.ChargeNode node1 = nodes.get(pos1);
//
//            if(node1!=null && node1.isValid()){
//                ((AccessorChargeNode)node1).setGrid(NULL_GRID);
//            }
//        }
    }

    @Inject(method = "needsNode",at = @At("RETURN"), cancellable = true)
    public void needsNodeExtra(BlockPos pos, IChargeBlock.ChargeSpec chargeSpec, CallbackInfoReturnable<Boolean> cir, @Local ChargeNode node){
        if(!railcraft.chargeNetworkFix) return;
        if(cir.getReturnValue()){
            return;
        };
        if(node.getGrid().isNull() || node.getGrid().isEmpty() || !node.getGrid().contains(node)){
            cir.setReturnValue(true);
        }
    }
}

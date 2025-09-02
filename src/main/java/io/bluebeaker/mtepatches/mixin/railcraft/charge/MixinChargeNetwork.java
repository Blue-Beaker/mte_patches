package io.bluebeaker.mtepatches.mixin.railcraft.charge;

import com.llamalad7.mixinextras.sugar.Local;
import mods.railcraft.api.charge.IChargeBlock;
import mods.railcraft.common.util.charge.ChargeNetwork;
import mods.railcraft.common.util.charge.ChargeNetwork.ChargeGrid;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

@Mixin(value = ChargeNetwork.class,remap = false)
public abstract class MixinChargeNetwork {
    @Shadow @Final private Map<BlockPos, ChargeNetwork.ChargeNode> nodes;
    @Shadow protected abstract void forConnections(BlockPos pos, BiConsumer<BlockPos, IBlockState> action);
    @Shadow @Final private WeakReference<World> world;

    @Unique
    private final Set<ChargeGrid> mte_patches$changedGrids = new HashSet<>();
    @Unique
    private final Set<BlockPos> mte_patches$extraBlocksToUpdate = new HashSet<>();

    @Inject(method = "tick",at = @At("TAIL"))
    private void afterAddedNodes(CallbackInfo ci, @Local(ordinal = 0) Set<BlockPos> added){
        World world1 = world.get();
        if(world1==null) return;

        for (ChargeGrid grid1 : mte_patches$changedGrids) {
            Map<BlockPos,IBlockState> blockStateMap = new HashMap<>();
            AccessorChargeGrid grid = (AccessorChargeGrid) grid1;
            // Get blocks neighbouring to the grid1
            for (ChargeNetwork.ChargeNode chargeNode : grid.getChargeNodes()) {
                forConnections(((AccessorChargeNode)chargeNode).getPos(), blockStateMap::put);
            }
            // Register every block in the map
            for (BlockPos pos2 : blockStateMap.keySet()) {

                IBlockState state = blockStateMap.get(pos2);
                Block block = state.getBlock();
                if(!(block instanceof IChargeBlock)) continue;

                ChargeNetwork.ChargeNode node2 = nodes.get(pos2);
                if(node2!=null && grid1==node2.getGrid()) continue;

                ((IChargeBlock) block).registerNode(state,world1,pos2);
            }
        }
        //
        for (BlockPos pos3 : mte_patches$extraBlocksToUpdate){
            IBlockState state = world1.getBlockState(pos3);
            Block block = state.getBlock();
            if(!(block instanceof IChargeBlock)) continue;
            ((IChargeBlock) block).registerNode(state,world1,pos3);
        }
        mte_patches$extraBlocksToUpdate.clear();
    }

    // Mark grid of added node
    @Inject(method = "addNodeImpl",at = @At("TAIL"))
    private void afterAddedNode(BlockPos pos, ChargeNetwork.ChargeNode node,CallbackInfo ci){
        mte_patches$changedGrids.add(node.getGrid());
    }
    // Mark changed blocks around removed node
    @Inject(method = "removeNodeImpl",at = @At("HEAD"))
    private void beforeRemoveNode(BlockPos pos, CallbackInfo ci){
        ChargeNetwork.ChargeNode chargeNode = nodes.get(pos);
        if(chargeNode==null) return;

        for (EnumFacing value : EnumFacing.values()) {
            mte_patches$extraBlocksToUpdate.add(pos.offset(value));
        }
    }
}

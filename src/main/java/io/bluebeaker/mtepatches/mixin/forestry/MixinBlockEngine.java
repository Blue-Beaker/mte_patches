package io.bluebeaker.mtepatches.mixin.forestry;

import forestry.core.blocks.BlockBase;
import forestry.energy.blocks.BlockEngine;
import forestry.energy.blocks.BlockTypeEngine;
import forestry.plugins.PluginIC2;
import io.bluebeaker.mtepatches.LoadedModChecker;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.forestry;

@Mixin(value = BlockEngine.class,remap = false)
public abstract class MixinBlockEngine extends BlockBase<BlockTypeEngine> {

    public MixinBlockEngine(BlockTypeEngine blockType) {
        super(blockType);
    }

    @Inject(method = "addCollisionBoxToList",at = @At("HEAD"),cancellable = true,remap = true)
    public void bioGenCollision(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_, CallbackInfo ci){
        if(!forestry.collisionBoxFix || !LoadedModChecker.ic2.isLoaded()) return;
        BlockEngine engine = (BlockEngine) (Object) this;
        if(engine==PluginIC2.getBlocks().generator){

            AxisAlignedBB axisalignedbb = new AxisAlignedBB(pos);
            if (entityBox.intersects(axisalignedbb))
            {
                collidingBoxes.add(axisalignedbb);
            }
            ci.cancel();
        }
    }
    @Inject(method = "collisionRayTrace",at = @At("HEAD"),cancellable = true,remap = true)
    public void bioGenCollisionRay(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end, CallbackInfoReturnable<RayTraceResult> cir){
        if(!forestry.collisionBoxFix || !LoadedModChecker.ic2.isLoaded()) return;
        BlockEngine engine = (BlockEngine) (Object) this;
        if(engine==PluginIC2.getBlocks().generator){
            cir.setReturnValue(super.collisionRayTrace(blockState, worldIn, pos, start, end));
        }
    }
    @Inject(method = "isOrientedAtEnergyReciever",at = @At("HEAD"),cancellable = true)
    private static void dontRotateBioGen(World world, BlockPos pos, EnumFacing orientation, CallbackInfoReturnable<Boolean> cir){
        if(!forestry.collisionBoxFix || !LoadedModChecker.ic2.isLoaded()) return;
        Block block = world.getBlockState(pos).getBlock();
        if(block==PluginIC2.getBlocks().generator){
            cir.setReturnValue(false);
        }
    }
}

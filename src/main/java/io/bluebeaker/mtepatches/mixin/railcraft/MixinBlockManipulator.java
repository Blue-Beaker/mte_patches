package io.bluebeaker.mtepatches.mixin.railcraft;

import mods.railcraft.common.blocks.machine.BlockMachine;
import mods.railcraft.common.blocks.machine.manipulator.BlockMachineManipulator;
import mods.railcraft.common.blocks.machine.manipulator.ManipulatorVariant;
import mods.railcraft.common.blocks.machine.manipulator.TileIC2Manipulator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

import static io.bluebeaker.mtepatches.MTEPatchesConfig.railcraft;

@Mixin(BlockMachineManipulator.class)
public abstract class MixinBlockManipulator extends BlockMachine<ManipulatorVariant> {
    public MixinBlockManipulator(Material mat) {
        super(mat);
    }

    @Intrinsic
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if(railcraft.manipulatorDropFix){

            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileIC2Manipulator)
            {
                // Drop every item in the upgrade slots
                for (int i = 2; i < 6; i++) {
                    ItemStack stackInSlot = ((TileIC2Manipulator) tileentity).getInventory().getStackInSlot(i);
                    InventoryHelper.spawnItemStack(worldIn,pos.getX(),pos.getY(),pos.getZ(),stackInSlot);
                }
            }

        }

        super.breakBlock(worldIn, pos, state);
    }
}

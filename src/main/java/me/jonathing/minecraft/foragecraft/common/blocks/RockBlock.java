package me.jonathing.minecraft.foragecraft.common.blocks;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RockBlock extends FallingBlock
{
//	private static AxisAlignedBB bounds_normal = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.25D, 0.7D);
//	private static AxisAlignedBB bounds_flat= new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.125D, 0.8D);

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    }

    public RockBlock(Block.Properties properties)
    {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader world, BlockPos blockPos)
    {
        return world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.down()).isSolid();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult)
    {
        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        Block.spawnAsEntity(world, blockPos, new ItemStack(this.getRockItem(), 1));
        return ActionResultType.SUCCESS;
    }

    public Item getRockItem()
    {
        return ForageBlocks.rock.asItem();
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    @Override
    public Block.OffsetType getOffsetType()
    {
        return Block.OffsetType.XZ;
    }
}

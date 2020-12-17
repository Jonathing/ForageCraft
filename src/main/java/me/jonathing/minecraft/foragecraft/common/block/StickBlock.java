package me.jonathing.minecraft.foragecraft.common.block;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This class holds the {@link ForageBlocks#stick} block. It is required so that it is able to have its own hitbox,
 * along with several other features that are exclusive to the stick block.
 * <p>
 * Why am I having the stick act like a rock? Because fuck you, that's why.
 *
 * @author Jonathing
 * @see ForageBlocks#stick
 * @see RockBlock
 * @since 2.0.0
 */
public class StickBlock extends RockBlock
{
//    private static AxisAlignedBB bounds = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public StickBlock(Block.Properties properties)
    {
        super(properties);
    }

    /**
     * This method defines the hitbox for the {@link ForageBlocks#stick}.
     * <p>
     * I'm not really sure what these numbers actually mean, but Bailey and I toyed with these values enough to get
     * exactly what we needed for this item.
     *
     * @see net.minecraft.block.FallingBlock#getShape(BlockState, IBlockReader, BlockPos, ISelectionContext)
     */
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_)
    {
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    }

    /**
     * @see net.minecraft.block.FallingBlock#rotate(BlockState, Rotation)
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_)
    {
        return p_185499_1_.with(FACING, p_185499_2_.rotate(p_185499_1_.get(FACING)));
    }

    /**
     * @see net.minecraft.block.FallingBlock#mirror(BlockState, Mirror)
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_)
    {
        return p_185471_1_.rotate(p_185471_2_.toRotation(p_185471_1_.get(FACING)));
    }

    /**
     * @see net.minecraft.block.FallingBlock#fillStateContainer(StateContainer.Builder)
     */
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_)
    {
        p_206840_1_.add(FACING);
    }

    /**
     * @see net.minecraft.block.FallingBlock#getStateForPlacement(BlockItemUseContext)
     */
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_)
    {
        return this.getDefaultState().with(FACING, p_196258_1_.getPlacementHorizontalFacing().getOpposite());
    }

    /**
     * This method ensures that if a neighbor to the stick block changes, the stick block will drop itself.
     *
     * @see net.minecraft.block.FallingBlock#neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)
     */
    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!this.isValidPosition(state, world, pos))
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            Block.spawnAsEntity(world, pos, new ItemStack(this.getRockItem(), 1));
        }
    }

    /**
     * This is our way of telling the game that this specific {@link RockBlock} is a {@link ForageBlocks#stick}.
     *
     * @see RockBlock#getRockItem()
     */
    @Override
    public Item getRockItem()
    {
        return Items.STICK;
    }

    @Override
    @Nonnull
    public Item asItem()
    {
        return Items.STICK;
    }
}

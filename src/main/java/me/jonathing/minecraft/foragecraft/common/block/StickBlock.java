package me.jonathing.minecraft.foragecraft.common.block;

import me.jonathing.minecraft.foragecraft.common.block.template.DecorativeBlock;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

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
public class StickBlock extends RockBlock implements IWaterLoggable
{
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final Random STICK_RANDOM = new Random();

    /**
     * Makes a new {@link DecorativeBlock} with features exclusive to the {@link ForageBlocks#stick}.
     *
     * @see DecorativeBlock#DecorativeBlock(Properties, VoxelShape, Supplier)
     */
    public StickBlock()
    {
        super(Block.Properties.from(Blocks.OAK_PLANKS).doesNotBlockMovement().notSolid().zeroHardnessAndResistance(), DecorativeBlock.STICK_SHAPE, () -> Items.STICK);
    }

    /**
     * @see net.minecraft.block.FallingBlock#rotate(BlockState, Rotation)
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    /**
     * @see net.minecraft.block.FallingBlock#mirror(BlockState, Mirror)
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    /**
     * @see net.minecraft.block.FallingBlock#fillStateContainer(StateContainer.Builder)
     */
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(RockBlock.WATERLOGGED, FACING);
    }

    /**
     * Some nonsense from the {@link LanternBlock} that allowed me to add waterlogging support for rocks.
     *
     * @param context The {@link BlockItemUseContext} given to the method.
     * @return Either a waterlogged or non-waterlogged rock based on the result of this method.
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());

        for (Direction direction : context.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                BlockState blockstate = this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
                if (blockstate.isValidPosition(context.getWorld(), context.getPos()))
                {
                    return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
                }
            }
        }

        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
     * Calls {@link #getStateWithRandomDirection(Random)} but with our own {@link Random} instead.
     *
     * @return The {@link BlockState} given by {@link #getStateWithRandomDirection(Random)}.
     * @see #getStateWithRandomDirection(Random)
     * @see #STICK_RANDOM
     */
    @Nonnull
    public BlockState getStateWithRandomDirection()
    {
        return this.getStateWithRandomDirection(STICK_RANDOM);
    }

    /**
     * Gets the stick block's default {@link BlockState} along with a random {@link Direction}. It is preferrable to use
     * this rather than {@link Block#getDefaultState()}.
     *
     * @param random The {@link Random} for determining which {@link Direction} to use. It is recommended to use a
     *               {@link World#rand}.
     * @return {@link Block#getDefaultState()} with a random {@link Direction}.
     * @see Block#getDefaultState()
     * @see #getStateWithRandomDirection()
     */
    @Nonnull
    public BlockState getStateWithRandomDirection(@Nonnull Random random)
    {
        Direction direction;

        switch (random.nextInt(4))
        {
            case 3:
                direction = Direction.WEST;
                break;
            case 2:
                direction = Direction.SOUTH;
                break;
            case 1:
                direction = Direction.EAST;
                break;
            default:
                direction = Direction.NORTH;
                break;
        }

        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    @Nonnull
    public Item asItem()
    {
        return this.getDecorativeItem();
    }
}

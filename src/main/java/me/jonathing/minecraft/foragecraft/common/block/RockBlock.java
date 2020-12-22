package me.jonathing.minecraft.foragecraft.common.block;

import me.jonathing.minecraft.foragecraft.common.block.template.DecorativeBlock;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

/**
 * This class holds the {@link ForageBlocks#rock}. This class is the king of all rocks, as it is also integral to the
 * how {@link ForageBlocks#flat_rock} and {@link ForageBlocks#stick} function.
 *
 * @author Jonathing
 * @see ForageBlocks#rock
 * @see DecorativeBlock
 * @see IWaterLoggable
 * @since 2.1.0
 */
public class RockBlock extends DecorativeBlock implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    /**
     * Makes a new {@link DecorativeBlock} with features exclusive to the {@link ForageBlocks#rock}.
     *
     * @see DecorativeBlock#DecorativeBlock(Properties, VoxelShape, Supplier)
     */
    public RockBlock()
    {
        this(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).doesNotBlockMovement().notSolid().zeroHardnessAndResistance(), DecorativeBlock.ROCK_SHAPE, () -> ForageBlocks.rock.asItem());
    }

    /**
     * Makes a new {@link DecorativeBlock} with features exclusive to the {@link ForageBlocks#rock} but with a different
     * {@link VoxelShape} and a different {@link Item}. Primarily used to make the {@link ForageBlocks#flat_rock}.
     *
     * @see DecorativeBlock#DecorativeBlock(Properties, VoxelShape, Supplier)
     */
    public RockBlock(VoxelShape shape, Supplier<Item> decorativeItem)
    {
        this(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).doesNotBlockMovement().notSolid().zeroHardnessAndResistance(), shape, decorativeItem);
    }

    public RockBlock(Properties properties, VoxelShape shape, Supplier<Item> decorativeItem)
    {
        super(properties, shape, decorativeItem);
        this.setDefaultState(this.getStateContainer().getBaseState().with(WATERLOGGED, false));
    }

    /**
     * This method checks if the decorative block can be placed by checking if the given {@link BlockState} is air and
     * if the {@link BlockState} of the {@link BlockPos} right under it is solid. If the given {@link BlockState} is
     * water, the rock will be waterlogged.
     *
     * @param blockState The {@link BlockState} to replace with the decorative block.
     * @param world      The {@link IWorldReader} that the {@link BlockState} resides in.
     * @param blockPos   The {@link BlockPos} of the {@link BlockState}.
     * @return The result of the position validity check.
     */
    @Override
    public boolean isValidPosition(@Nonnull BlockState blockState, IWorldReader world, @Nonnull BlockPos blockPos)
    {
        return (world.getBlockState(blockPos).getBlock() instanceof AirBlock || world.getBlockState(blockPos).getFluidState().equals(Fluids.WATER.getStillFluidState(false)))
                && world.getBlockState(blockPos.down()).isSolid();
    }

    /**
     * Some nonsense from the {@link LanternBlock} that allowed me to add waterlogging support for rocks.
     *
     * @param context The {@link BlockItemUseContext} given to the method.
     * @return Either a waterlogged or non-waterlogged rock based on the result of this method.
     */
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());

        for (Direction direction : context.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                BlockState blockstate = this.getDefaultState();
                if (blockstate.isValidPosition(context.getWorld(), context.getPos()))
                {
                    return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
    }

    /**
     * Has the same logic as
     * {@link DecorativeBlock#onBlockActivated(BlockState, World, BlockPos, PlayerEntity, Hand, BlockRayTraceResult)}
     * but also accounts for waterlogging.
     *
     * @param blockState          The {@link BlockState} of the decorative block that was activated.
     * @param world               The {@link World} in which the block was activated.
     * @param blockPos            The {@link BlockPos} of the decorative block that was activated.
     * @param player              The {@link PlayerEntity} that activated the block.
     * @param hand                The {@link Hand} of the {@link PlayerEntity} that activated the block.
     * @param blockRayTraceResult The {@link BlockRayTraceResult} given for the method.
     * @return {@link ActionResultType#SUCCESS}
     * @see DecorativeBlock#onBlockActivated(BlockState, World, BlockPos, PlayerEntity, Hand, BlockRayTraceResult)
     */
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult)
    {
        world.setBlockState(blockPos, this.getFluidState(blockState).equals(Fluids.EMPTY.getDefaultState()) ? Blocks.AIR.getDefaultState() : this.getFluidState(blockState).getBlockState());
        Block.spawnAsEntity(world, blockPos, new ItemStack(this.getDecorativeItem(), 1));
        return ActionResultType.SUCCESS;
    }

    /**
     * Some nonsense from the {@link LanternBlock} that allowed me to add waterlogging support for rocks.
     *
     * @param stateIn     The {@link BlockState} of the rock to check if it is waterlogged.
     * @param facing      A {@link Direction} given to the method.
     * @param facingState A {@link BlockState} given to the method.
     * @param worldIn     A {@link IWorld} given to the method.
     * @param currentPos  A {@link BlockPos} given to the method.
     * @param facingPos   A {@link BlockPos} given to the method.
     * @return The {@link BlockState} returned by
     * {@link FallingBlock#updatePostPlacement(BlockState, Direction, BlockState, IWorld, BlockPos, BlockPos)}
     */
    @Override
    @Nonnull
    public BlockState updatePostPlacement(BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    /**
     * Gets the {@link FluidState} of the {@link net.minecraft.fluid.Fluid} that is currently waterlogging the given
     * {@link BlockState}.
     *
     * @param state The {@link BlockState} to get the {@link FluidState} of.
     * @return The {@link FluidState} if the rock is waterlogged or {@link Fluids#EMPTY} otherwise.
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}

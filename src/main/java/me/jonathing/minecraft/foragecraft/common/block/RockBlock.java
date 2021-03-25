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
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

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
     * Makes a new {@link DecorativeBlock} with features exclusive to the {@link ForageBlocks#rock} with a gicen
     * {@link VoxelShape} and decorative {@link Item} supplied by a {@link Lazy}.
     *
     * @see RockBlock#RockBlock(Properties, VoxelShape, Lazy)
     */
    public RockBlock(VoxelShape shape, Lazy<Item> decorativeItem)
    {
        this(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).noCollission().noOcclusion().instabreak(), shape, decorativeItem);
    }

    public RockBlock(Properties properties, VoxelShape shape, Lazy<Item> decorativeItem)
    {
        super(properties, shape, decorativeItem);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    /**
     * This method checks if the decorative block can be placed by checking if the given {@link BlockState} is air and
     * if the {@link BlockState} of the {@link BlockPos} right under it is solid. If the given {@link BlockState} is
     * water, the rock will be waterlogged.
     *
     * @param blockState The blockstate to replace with the decorative block.
     * @param level      The level that the {@link BlockState} resides in.
     * @param blockPos   The position in the level of the {@link BlockState}.
     * @return The result of the position validity check.
     */
    @Override
    public boolean canSurvive(@Nonnull BlockState blockState, IWorldReader level, @Nonnull BlockPos blockPos)
    {
        BlockState blockStateBelow = level.getBlockState(blockPos.below());
        return (level.getBlockState(blockPos).getBlock() instanceof AirBlock
                || level.getBlockState(blockPos).getFluidState().equals(Fluids.WATER.getSource(false)))
                && blockStateBelow.canOcclude();
    }

    /**
     * Some nonsense from the {@link LanternBlock} that allows adding waterlogging support for rocks.
     *
     * @param context The item use context given to the method.
     * @return Either a waterlogged or non-waterlogged rock based on the result of this method.
     * @see LanternBlock#getStateForPlacement(BlockItemUseContext)
     * @see Block#getStateForPlacement(BlockItemUseContext)
     */
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        for (Direction direction : context.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                BlockState blockstate = this.defaultBlockState();
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos()))
                {
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    /**
     * Some nonsense to add waterlogging support for rocks.
     *
     * @param builder The state container builder to add the waterlogged property to.
     * @see Block#createBlockStateDefinition(StateContainer.Builder)
     */
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
    }

    /**
     * Contains the same logic from
     * {@link DecorativeBlock#use(BlockState, World, BlockPos, PlayerEntity, Hand, BlockRayTraceResult)}
     * but also has nonsense that accounts for waterlogging.
     *
     * @param blockState          The blockstate of the decorative block that was activated.
     * @param level               The level in which the block was activated.
     * @param blockPos            The position in the level of the decorative block that was activated.
     * @param player              The player that activated the block.
     * @param hand                The hand of the {@link PlayerEntity} that activated the block.
     * @param blockRayTraceResult The ray trace result given for the method.
     * @return {@link ActionResultType#SUCCESS}
     * @see DecorativeBlock#use(BlockState, World, BlockPos, PlayerEntity, Hand, BlockRayTraceResult)
     */
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResultType use(BlockState blockState, World level, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult)
    {
        level.setBlockAndUpdate(blockPos, this.getFluidState(blockState).equals(Fluids.EMPTY.defaultFluidState()) ? Blocks.AIR.defaultBlockState() : this.getFluidState(blockState).createLegacyBlock());
        Block.popResource(level, blockPos, new ItemStack(this.getDecorativeItem(), 1));
        return ActionResultType.SUCCESS;
    }

    /**
     * Some nonsense from the {@link LanternBlock} that allowed me to add waterlogging support for rocks.
     *
     * @param stateIn     The blockstate of the rock to check if it is waterlogged.
     * @param facing      A horizontal-facing direction given to the method that is used in the superclass's method.
     * @param facingState A blockstate given to the method that is used in the superclass's method.
     * @param level       The level where the blockstates reside in.
     * @param currentPos  A position in the level given to the method that also accounts for waterlogging.
     * @param facingPos   A position in the level given to the method that is used in the superclass's method.
     * @return The {@link BlockState} returned by
     * {@link FallingBlock#updateShape(BlockState, Direction, BlockState, IWorld, BlockPos, BlockPos)}
     * @see FallingBlock#updateShape(BlockState, Direction, BlockState, IWorld, BlockPos, BlockPos)
     */
    @Override
    @Nonnull
    public BlockState updateShape(BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
    {
        if (stateIn.getValue(WATERLOGGED))
        {
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    /**
     * Gets the {@link FluidState} of the {@link net.minecraft.fluid.Fluid} that is currently waterlogging the given
     * {@link BlockState}.
     *
     * @param state The blockstate to get the {@link FluidState} of.
     * @return The fluidstate if the rock is waterlogged or {@link Fluids#EMPTY} otherwise.
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}

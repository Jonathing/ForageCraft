package me.jonathing.minecraft.foragecraft.common.block.template;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * This class holds the template for any decorative blocks in ForageCraft.
 *
 * @author Jonathing
 * @see FallingBlock
 * @see IWaterLoggable
 * @since 2.0.0
 */
public abstract class DecorativeBlock extends FallingBlock implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape shape;
    private final Lazy<Item> decorativeItem;

    public DecorativeBlock(Properties properties, VoxelShape shape, Lazy<Item> decorativeItem)
    {
        super(properties);
        this.shape = shape;
        this.decorativeItem = decorativeItem;
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    /**
     * This method defines the hitbox for a decorative block along with its offset.
     *
     * @see FallingBlock#getShape(BlockState, IBlockReader, BlockPos, ISelectionContext)
     */
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState blockState, IBlockReader level, BlockPos pos, ISelectionContext context)
    {
        Vector3d offset = blockState.getOffset(level, pos);
        return this.shape.move(offset.x(), 0, offset.z());
    }

    /**
     * This method defines for all decorative blocks that if a piston is to attempt to push them, they should have a
     * {@link PushReaction} of {@link PushReaction#DESTROY}.
     *
     * @param blockState The blockstate of the block being pushed.
     * @return The reaction value when the block is pushed ({@link PushReaction#DESTROY}).
     */
    @Override
    @SuppressWarnings("deprecation")
    public PushReaction getPistonPushReaction(BlockState blockState)
    {
        return PushReaction.DESTROY;
    }

    /**
     * This method checks if the decorative block can be placed by checking if the given {@link BlockState} is air and
     * if the {@link BlockState} of the {@link BlockPos} right under it is solid. If the given {@link BlockState} is
     * water, the rock will be waterlogged.
     *
     * @param blockState The blockstate to replace with the decorative block. <em>Unused and can be null.</em>
     * @param level      The level that the {@link BlockState} resides in.
     * @param blockPos   The position in the level of the {@link BlockState}.
     * @return The result of the position validity check.
     */
    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(@Nullable BlockState blockState, IWorldReader level, BlockPos blockPos)
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
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            }
        }

        return null;
    }

    /**
     * This essentially just prevents particles from being spawned under a decorative block floating in stasis. They're
     * not really meant to do that.
     *
     * @param blockState The blockstate of the block that would have animated particles.
     * @param level      The level the blockstate is in.
     * @param blockPos   The position of the block in the level.
     * @param random     The random to use, most likely the level's {@link World#random}.
     * @see FallingBlock#animateTick(BlockState, World, BlockPos, Random)
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World level, BlockPos blockPos, Random random)
    {
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
     * On right-click, the decorative block will remove itself from the level and drop its {@link #getDecorativeItem()}.
     * Includes voodoo magic for waterlogging.
     *
     * @param blockState          The blockstate of the decorative block that was activated.
     * @param level               The level in which the block was activated.
     * @param blockPos            The position in the level of the decorative block that was activated.
     * @param player              The player that activated the block. <em>Unusued and can be null.</em>
     * @param hand                The hand of the {@link PlayerEntity} that activated the block. <em>Unused but
     *                            <strong>should not</strong> be null.</em>
     * @param blockRayTraceResult The ray trace result given for the method. <em>Unused and can be null.</em>
     * @return {@link ActionResultType#SUCCESS}
     * @see DecorativeBlock#use(BlockState, World, BlockPos, PlayerEntity, Hand, BlockRayTraceResult)
     */
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(BlockState blockState, World level, BlockPos blockPos, @Nullable PlayerEntity player, Hand hand, @Nullable BlockRayTraceResult blockRayTraceResult)
    {
        level.setBlockAndUpdate(blockPos, this.getFluidState(blockState).equals(Fluids.EMPTY.defaultFluidState()) ? Blocks.AIR.defaultBlockState() : this.getFluidState(blockState).createLegacyBlock());
        Block.popResource(level, blockPos, new ItemStack(this.getDecorativeItem(), 1));
        return ActionResultType.SUCCESS;
    }

    /**
     * Some nonsense from the {@link LanternBlock} that allowed me to add waterlogging support for rocks. All arguments
     * are sent to the {@link FallingBlock#updateShape(BlockState, Direction, BlockState, IWorld, BlockPos, BlockPos)}
     * super method so they <strong>should not</strong> be null.
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
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld level, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.getValue(WATERLOGGED))
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

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
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    /**
     * Each {@link DecorativeBlock} has its own decorative item that it drops wheneven it is right-clicked, broken, or
     * otherwise removed from the world. In this way, other decorative block classes can {@link Override} this method
     * and dictate that that specific class will drop that specific item.
     *
     * @return The {@link Item} that this specific {@link DecorativeBlock} instance portrays itself as.
     */
    public Item getDecorativeItem()
    {
        return this.decorativeItem.get();
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }
}

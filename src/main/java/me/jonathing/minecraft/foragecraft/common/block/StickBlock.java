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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

/**
 * This class holds the {@link ForageBlocks#stick} block. It is required so that it is able to have its own hitbox,
 * along with several other features that are exclusive to the stick block.
 *
 * @author Jonathing
 * @see ForageBlocks#stick
 * @see RockBlock
 * @since 2.0.0
 */
public class StickBlock extends DecorativeBlock
{
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    private static final Supplier<VoxelShape> SHAPE = () ->
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
    private static final Supplier<Properties> PROPERTIES =
            () -> AbstractBlock.Properties
                    .copy(Blocks.OAK_PLANKS)
                    .noCollission()
                    .noOcclusion()
                    .instabreak();

    /**
     * Makes a new {@link DecorativeBlock} with features exclusive to the {@link ForageBlocks#stick}.
     *
     * @see DecorativeBlock#DecorativeBlock(Properties, VoxelShape, Lazy)
     */
    public StickBlock()
    {
        super(PROPERTIES.get(), SHAPE.get(), () -> Items.STICK);
    }

    /**
     * @see net.minecraft.block.FallingBlock#rotate(BlockState, Rotation)
     */
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * @see net.minecraft.block.FallingBlock#mirror(BlockState, Mirror)
     */
    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    /**
     * @see net.minecraft.block.FallingBlock#createBlockStateDefinition(StateContainer.Builder)
     */
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(RockBlock.WATERLOGGED, FACING);
    }

    /**
     * Contains the same logic from {@link RockBlock#getStateForPlacement(BlockItemUseContext)} but also accounts for
     * the {@link StickBlock#FACING} property. Can be {@code null} if a {@link BlockState} for placement cannot be
     * found.
     *
     * @param context The item use context given to the method.
     * @return Either a waterlogged or non-waterlogged stick based on the result of this method.
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
                BlockState blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos()))
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            }
        }

        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    /**
     * Calls {@link #getStateWithRandomDirection(Random)} using a given {@link IWorld}'s {@link IWorld#getRandom()}.
     *
     * @param level The level to use its {@link IWorld#getRandom()} for.
     * @return The blockstate given by {@link #getStateWithRandomDirection(Random)} using the level's random.
     * @see IWorld#getRandom()
     */
    public BlockState getStateWithRandomDirection(IWorld level)
    {
        return this.getStateWithRandomDirection(level.getRandom());
    }

    /**
     * Gets the stick block's default {@link BlockState} along with a random {@link Direction}. It is preferable to use
     * this rather than {@link Block#defaultBlockState()}.
     *
     * @param random The {@link Random} for determining which {@link Direction} to use. It is recommended to use a
     *               {@link World#random}.
     * @return {@link Block#defaultBlockState()} with a random {@link Direction}.
     * @see Block#defaultBlockState()
     * @see #getStateWithRandomDirection(IWorld)
     */
    public BlockState getStateWithRandomDirection(@Nonnull Random random)
    {
        return this.defaultBlockState().setValue(FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
    }

    @Override
    public Item asItem()
    {
        return this.getDecorativeItem();
    }
}

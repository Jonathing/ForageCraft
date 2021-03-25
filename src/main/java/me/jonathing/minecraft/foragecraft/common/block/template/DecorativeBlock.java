package me.jonathing.minecraft.foragecraft.common.block.template;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

/**
 * This class holds the template for any decorative blocks in ForageCraft. This includes blocks such as
 * {@link ForageBlocks#rock}, {@link ForageBlocks#flat_rock}, and {@link ForageBlocks#stick}.
 *
 * @author Jonathing
 * @see FallingBlock
 * @since 2.0.0
 */
public class DecorativeBlock extends FallingBlock
{
    public static final VoxelShape STICK_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D);
    public static final VoxelShape ROCK_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);
    public static final VoxelShape FLAT_ROCK_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);

    private final VoxelShape shape;
    private final Lazy<Item> decorativeItem;

    public DecorativeBlock(Properties properties, VoxelShape shape, Lazy<Item> decorativeItem)
    {
        super(properties);
        this.shape = shape;
        this.decorativeItem = decorativeItem;
    }

    /**
     * This method defines the hitbox for a decorative block along with its offset.
     *
     * @see FallingBlock#getShape(BlockState, IBlockReader, BlockPos, ISelectionContext)
     */
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
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
    @Nonnull
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public PushReaction getPistonPushReaction(BlockState blockState)
    {
        return PushReaction.DESTROY;
    }

    /**
     * This method checks if the decorative block can be placed by checking if the given {@link BlockState} is air and
     * if the {@link BlockState} of the {@link BlockPos} right under it is solid.
     *
     * @param blockState The blockstate to replace with the decorative block.
     * @param level      The level reader that the {@link BlockState} resides in.
     * @param blockPos   The {@link BlockPos} of the {@link BlockState}.
     * @return The result of the position validity check.
     * @see FallingBlock#canSurvive(BlockState, IWorldReader, BlockPos)
     */
    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(@Nonnull BlockState blockState, IWorldReader level, @Nonnull BlockPos blockPos)
    {
        return level.getBlockState(blockPos).getBlock() instanceof AirBlock
                && level.getBlockState(blockPos.below()).canOcclude();
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
    @ParametersAreNonnullByDefault
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World level, BlockPos blockPos, Random random)
    {
    }

    /**
     * This method contains the logic that takes place when a block is activated by a player right-clicking on it. By
     * default, when right clicking on a decorative block, it will delete itself from the level and drop it's related
     * item given from the {@link #getDecorativeItem()} method.
     *
     * @param blockState          The blockstate of the decorative block that was activated.
     * @param level               The level in which the block was activated.
     * @param blockPos            The position in the level of the decorative block that was activated.
     * @param player              The player that activated the block.
     * @param hand                The hand of the {@link PlayerEntity} that activated the block.
     * @param blockRayTraceResult The ray trace result given for the method.
     * @return {@link ActionResultType#SUCCESS}
     */
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public ActionResultType use(BlockState blockState, World level, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult)
    {
        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        Block.popResource(level, blockPos, new ItemStack(this.getDecorativeItem(), 1));
        return ActionResultType.SUCCESS;
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
    @Nonnull
    public OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }
}

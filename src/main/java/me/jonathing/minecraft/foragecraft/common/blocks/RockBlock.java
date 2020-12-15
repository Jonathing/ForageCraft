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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

/**
 * This class holds the {@link ForageBlocks#rock}. This class is the king of all rocks, as it is also integral to the
 * how {@link ForageBlocks#flat_rock} and {@link ForageBlocks#stick} function.
 *
 * @author Jonathing
 * @see ForageBlocks#rock
 * @see FallingBlock
 * @since 2.0.0
 */
public class RockBlock extends FallingBlock
{
    // from the old ForageCraft
//	private static AxisAlignedBB bounds_normal = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.25D, 0.7D);
//	private static AxisAlignedBB bounds_flat= new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.125D, 0.8D);

    public RockBlock(Block.Properties properties)
    {
        super(properties);
    }

    /**
     * This method defines the hitbox for the {@link ForageBlocks#rock}.
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
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState blockState, IWorldReader world, BlockPos blockPos)
    {
        return world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.down()).isSolid();
    }

    /**
     * This essentially just prevents particles from being spawned under a rock or stick floating in stasis. They're
     * not really meant to do that.
     *
     * @see FallingBlock#animateTick(BlockState, World, BlockPos, Random)
     */
    @Override
    @ParametersAreNonnullByDefault
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_)
    {
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public ActionResultType onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult)
    {
        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        Block.spawnAsEntity(world, blockPos, new ItemStack(this.getRockItem(), 1));
        return ActionResultType.SUCCESS;
    }

    /**
     * Instead of making the {@link ForageBlocks#flat_rock} and {@link ForageBlocks#stick} extend {@link FallingBlock}
     * on their own, they instead extend this class because they're properties are so deathly similar that it allowed
     * me to be lazy. This method, in turn, is our way of telling the game what is exactly what. For example, this
     * specific instance of {@link RockBlock} will always be a {@link ForageBlocks#rock}. If {@link FlatRockBlock} and
     * {@link StickBlock} want to say otherwise, they can just extend this method and say so themselves.
     *
     * @return The {@link Item} that this specific {@link RockBlock} instance portrays itself as.
     * @see FlatRockBlock#getRockItem()
     * @see StickBlock#getRockItem()
     */
    public Item getRockItem()
    {
        return ForageBlocks.rock.asItem();
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    @Override
    @Nonnull
    public Block.OffsetType getOffsetType()
    {
        return Block.OffsetType.XZ;
    }
}

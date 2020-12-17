package me.jonathing.minecraft.foragecraft.common.block;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This class holds the {@link ForageBlocks#flat_rock} block. It is required so that it is able to have its own hitbox
 * and usage of the {@link RockBlock#getRockItem()} method.
 *
 * @author Jonathing
 * @see ForageBlocks#flat_rock
 * @see RockBlock
 * @since 2.0.0
 */
public class FlatRockBlock extends RockBlock
{
    public FlatRockBlock(Block.Properties properties)
    {
        super(properties);
    }

    /**
     * This method defines the hitbox for the {@link ForageBlocks#flat_rock}.
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
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    }

    /**
     * This is our way of telling the game that this specific {@link RockBlock} is a {@link ForageBlocks#flat_rock}.
     *
     * @see RockBlock#getRockItem()
     */
    @Override
    public Item getRockItem()
    {
        return ForageBlocks.flat_rock.asItem();
    }
}

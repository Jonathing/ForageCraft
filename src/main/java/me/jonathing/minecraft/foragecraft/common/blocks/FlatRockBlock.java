package me.jonathing.minecraft.foragecraft.common.blocks;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class FlatRockBlock extends RockBlock
{
    public FlatRockBlock(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_)
    {
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    }

    @Override
    public Item getRockItem()
    {
        return ForageBlocks.flat_rock.asItem();
    }
}

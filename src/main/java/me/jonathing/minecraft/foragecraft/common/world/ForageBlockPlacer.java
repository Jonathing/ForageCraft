package me.jonathing.minecraft.foragecraft.common.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

public class ForageBlockPlacer extends SimpleBlockPlacer
{
    private final ITag<Block> blockTag;

    public ForageBlockPlacer(ITag<Block> blockTag)
    {
        this.blockTag = blockTag;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void place(IWorld level, BlockPos blockPos, BlockState blockState, Random random)
    {
        if (level.getBlockState(blockPos.below()).is(blockTag))
            super.place(level, blockPos, blockState, random);
    }
}

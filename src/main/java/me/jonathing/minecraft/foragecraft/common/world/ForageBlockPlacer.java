package me.jonathing.minecraft.foragecraft.common.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

/**
 * The forage block placer is essentially a {@link SimpleBlockPlacer} but uses a block {@link ITag} as a special
 * whitelist for placing blocks.
 *
 * @author Jonathing
 * @see SimpleBlockPlacer
 * @since 2.2.0
 */
@ParametersAreNonnullByDefault
public class ForageBlockPlacer extends SimpleBlockPlacer
{
    private final ITag<Block> blockTag;

    public ForageBlockPlacer(ITag<Block> blockTag)
    {
        this.blockTag = blockTag;
    }

    @Override
    public void place(IWorld level, BlockPos blockPos, BlockState blockState, Random random)
    {
        if (blockState.hasProperty(HorizontalBlock.FACING))
            blockState = blockState.setValue(HorizontalBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(level.getRandom()));

        if (level.getBlockState(blockPos.below()).is(blockTag))
            super.place(level, blockPos, blockState, random);
    }
}

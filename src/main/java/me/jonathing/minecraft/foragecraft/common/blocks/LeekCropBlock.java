package me.jonathing.minecraft.foragecraft.common.blocks;

import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nonnull;

/**
 * This class holds the {@link me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#leek_crop} block. It is
 * required to ensure that the seeds item for it is {@link ForageItems#leek_seeds}.
 *
 * @author Jonathing
 * @see me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#leek_crop
 * @see ForageItems#leek_seeds
 * @see CropsBlock
 * @since 2.0.0
 */
public class LeekCropBlock extends CropsBlock
{
    public LeekCropBlock(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    @Nonnull
    protected IItemProvider getSeedsItem()
    {
        return ForageItems.leek_seeds;
    }
}

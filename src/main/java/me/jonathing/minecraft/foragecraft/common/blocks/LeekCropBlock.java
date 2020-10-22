package me.jonathing.minecraft.foragecraft.common.blocks;

import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;

public class LeekCropBlock extends CropsBlock
{
    public LeekCropBlock(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    protected IItemProvider getSeedsItem()
    {
        return ForageItems.leek_seeds;
    }
}

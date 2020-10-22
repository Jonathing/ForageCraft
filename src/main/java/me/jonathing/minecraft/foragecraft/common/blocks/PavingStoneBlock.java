package me.jonathing.minecraft.foragecraft.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PavingStoneBlock extends Block
{
    public PavingStoneBlock(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    public void onEntityWalk(World world, BlockPos blockPos, Entity entity)
    {
        super.onEntityWalk(world, blockPos, entity);

        entity.setVelocity(entity.getMotion().x * 1.5, 0, entity.getMotion().z * 1.5);
    }
}

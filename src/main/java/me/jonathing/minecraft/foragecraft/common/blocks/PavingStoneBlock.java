package me.jonathing.minecraft.foragecraft.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This class holds the {@link me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#paving_stones} block. It
 * is required so that the player is able to walk faster on them as intended.
 *
 * @author Jonathing
 * @see me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#paving_stones
 * @see #onEntityWalk(World, BlockPos, Entity)
 * @see Block
 * @since 2.0.0
 */
public class PavingStoneBlock extends Block
{
    public PavingStoneBlock(Block.Properties properties)
    {
        super(properties);
    }

    /**
     * This method ensures that whenever an entity is walking on a
     * {@link me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#paving_stones} block, they move at 1.5
     * times their normal speed.
     *
     * @see Block#onEntityWalk(World, BlockPos, Entity)
     */
    @Override
    @ParametersAreNonnullByDefault
    public void onEntityWalk(World world, BlockPos blockPos, Entity entity)
    {
        super.onEntityWalk(world, blockPos, entity);

        entity.setVelocity(entity.getMotion().x * 1.5, 0, entity.getMotion().z * 1.5);
    }
}

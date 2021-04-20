package me.jonathing.minecraft.foragecraft.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A speed block is just like a normal {@link Block} but it speeds up any entities that step on it by a certain
 * multiplier {@link #speedMultiplier} given in the {@link #SpeedBlock(float, Properties)} constructor.
 *
 * @author Jonathing
 * @see #stepOn(World, BlockPos, Entity)
 * @see Block
 * @since 2.0.0
 */
public class SpeedBlock extends Block
{
    private final float speedMultiplier;

    public SpeedBlock(float speedMultiplier, AbstractBlock.Properties properties)
    {
        super(properties);
        this.speedMultiplier = speedMultiplier;
    }

    /**
     * This method ensures that whenever an entity is walking on a speed block, they move at {@link #speedMultiplier}
     * times their normal speed.
     *
     * @param level    The level in which the speed block exists.
     * @param blockPos The position in the level where the speed block exists.
     * @param entity   The entity that is stepping on the block.
     * @see Block#stepOn(World, BlockPos, Entity)
     */
    @Override
    @ParametersAreNonnullByDefault
    public void stepOn(World level, BlockPos blockPos, Entity entity)
    {
        super.stepOn(level, blockPos, entity);
        entity.setDeltaMovement(entity.getDeltaMovement().x * speedMultiplier, 0, entity.getDeltaMovement().z * speedMultiplier);
    }
}

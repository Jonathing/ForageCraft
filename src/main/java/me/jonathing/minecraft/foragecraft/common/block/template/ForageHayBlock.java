package me.jonathing.minecraft.foragecraft.common.block.template;

import net.minecraft.block.HayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * This class holds the {@link me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#fascine} and
 * {@link me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#straw_bale} blocks. It is required to give it
 * a custom fall damage multiplier in the method {@link #fallOn(World, BlockPos, Entity, float)}.
 *
 * @author Jonathing
 * @see me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks#fascine
 * @see #fallOn(World, BlockPos, Entity, float)
 * @see HayBlock
 * @since 2.1.0
 */
public class ForageHayBlock extends HayBlock
{
    private final float fallDamageMultiplier;

    public ForageHayBlock(float fallDamageMultiplier, Properties properties)
    {
        super(properties);
        this.fallDamageMultiplier = fallDamageMultiplier;
    }

    /**
     * This method handles fall damage when an entity falls upon a ForageCraft hay block.
     *
     * @param world  The level in which the entity has fallen upon the hay block.
     * @param pos    The position in the level in which the entity has fallen upon the hay block.
     * @param entity The entity that has fallen upon the hay block.
     * @param damage The amount of damage the entity would normally take from fall damage.
     * @see Entity#causeFallDamage(float, float)
     */
    @Override
    public void fallOn(@Nonnull World world, @Nonnull BlockPos pos, Entity entity, float damage)
    {
        entity.causeFallDamage(damage, this.fallDamageMultiplier);
    }
}

package me.jonathing.minecraft.foragecraft.common.item;

import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * This is the class for the {@link ForageItems#gathering_knife} item. It is required specifically for specific drops that
 * are given when using this item, which are also done in the
 * {@link #onBlockDestroyed(ItemStack, World, BlockState, BlockPos, LivingEntity)} method.
 *
 * @author Jonathing
 * @see ForageItems#gathering_knife
 * @see Item
 * @see IVanishable
 * @since 2.1.0
 */
public class GatheringKnifeItem extends Item implements IVanishable
{
    private static final float STRAW_CHANCE = 0.55F;

    public GatheringKnifeItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack itemStack, World world, @Nonnull BlockState blockState, @Nonnull BlockPos pos, @Nonnull LivingEntity livingEntity)
    {
        if (!world.isRemote && blockState.getBlock().equals(Blocks.GRASS))
        {
            if (world.getRandom().nextFloat() < STRAW_CHANCE)
                Block.spawnAsEntity(world, pos, ForageItems.straw.getDefaultInstance());

            itemStack.damageItem(1, livingEntity, (onToolBroken) ->
                    onToolBroken.sendBreakAnimation(EquipmentSlotType.MAINHAND));

            return true;
        }

        return false;
    }
}

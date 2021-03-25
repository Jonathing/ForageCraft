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

    /**
     * This method holds the logic that is used whenever the gathering knife is used to break a specific block.
     *
     * @param itemStack    The {@link ItemStack} used by the {@link LivingEntity} when the block is destroyed.
     * @param world        The {@link World} in which the block was broken.
     * @param blockState   The {@link BlockState} of the broken block.
     * @param pos          The {@link BlockPos} in which the block was broken.
     * @param livingEntity The {@link LivingEntity} that used the {@link ItemStack} that broke the block.
     * @return If true, update the statistics page saying that the item was used.
     * @see Item#onBlockDestroyed(ItemStack, World, BlockState, BlockPos, LivingEntity)
     */
    @Override
    public boolean mineBlock(@Nonnull ItemStack itemStack, World world, @Nonnull BlockState blockState, @Nonnull BlockPos pos, @Nonnull LivingEntity livingEntity)
    {
        if (!world.isClientSide && blockState.getBlock().equals(Blocks.GRASS))
        {
            if (world.getRandom().nextFloat() < STRAW_CHANCE)
                Block.popResource(world, pos, ForageItems.straw.getDefaultInstance());

            itemStack.hurtAndBreak(1, livingEntity, (onToolBroken) ->
                    onToolBroken.broadcastBreakEvent(EquipmentSlotType.MAINHAND));

            return true;
        }

        return false;
    }

    /**
     * This method is used to return an {@link ItemStack} that acts as the result of the item used in crafting. In the
     * case of the gathering knife, it uses durability and if it is at 0, it deletes the item.
     *
     * @param itemStack The current ItemStack
     * @return The resulting ItemStack
     * @see Item#getContainerItem(ItemStack)
     */
    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        itemStack.setDamageValue(itemStack.getDamageValue() + 1);
        ItemStack result = itemStack.copy();
        return result.getDamageValue() >= result.getMaxDamage() ? ItemStack.EMPTY : result;
    }

    @Override
    public boolean hasContainerItem(ItemStack itemStack)
    {
        return true;
    }
}

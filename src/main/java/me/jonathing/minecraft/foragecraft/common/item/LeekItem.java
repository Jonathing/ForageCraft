package me.jonathing.minecraft.foragecraft.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

/**
 * This is the class for the {@link me.jonathing.minecraft.foragecraft.common.registry.ForageItems#leek} item. It is
 * required specifically for the easter egg that has been included in prior versions of ForageCraft.
 * <p>
 * Anime.
 *
 * @see me.jonathing.minecraft.foragecraft.common.registry.ForageItems#leek
 * @see Item
 */
public class LeekItem extends Item
{
    public LeekItem(Item.Properties properties)
    {
        super(properties);
    }

    /**
     * This method contains the logic that is used for the easter egg of the leek item.
     *
     * @see Item#hitEntity(ItemStack, LivingEntity, LivingEntity)
     */
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        target.attackEntityFrom(DamageSource.GENERIC, 4);

        if (attacker instanceof PlayerEntity)
        {
            if (!((PlayerEntity) attacker).isCreative())
            {
//                ((PlayerEntity)attacker).inventory.clearMatchingItems(stack.getItem(), -1, 1, null);
                stack.shrink(1);
                attacker.getEntityWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 1, 1);
                return true;
            }
            else
            {
                return super.hitEntity(stack, target, attacker);
            }
        }
        else
        {
            return super.hitEntity(stack, target, attacker);
        }
    }
}

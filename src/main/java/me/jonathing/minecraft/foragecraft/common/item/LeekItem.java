package me.jonathing.minecraft.foragecraft.common.item;

import me.jonathing.minecraft.foragecraft.common.registry.ForageTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;

/**
 * This is the class for the {@link me.jonathing.minecraft.foragecraft.common.registry.ForageItems#leek} item. It is
 * required specifically for the easter egg that has been included in prior versions of ForageCraft.
 * <p>
 * Anime.
 *
 * @author Jonathing
 * @see me.jonathing.minecraft.foragecraft.common.registry.ForageItems#leek
 * @see Item
 * @since 2.0.0
 */
public class LeekItem extends Item
{
    public LeekItem(Item.Properties properties)
    {
        super(properties);
    }

    /**
     * This method contains the logic that is used for the easter egg of the leek item.
     * <p>
     * Since version {@code 2.1.2}, it also fires the
     * {@link me.jonathing.minecraft.foragecraft.common.trigger.LeekTrigger} specific to the easter egg advancement.
     *
     * @see Item#hitEntity(ItemStack, LivingEntity, LivingEntity)
     */
    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, LivingEntity target, @Nonnull LivingEntity attacker)
    {
        target.attackEntityFrom(DamageSource.GENERIC, 4);

        if (attacker instanceof PlayerEntity)
        {
            if (!((PlayerEntity) attacker).isCreative() && target.isLiving())
            {
                stack.shrink(1);
                attacker.getEntityWorld().playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 1, 1);
                ForageTriggers.LEEK_TRIGGER.trigger((ServerPlayerEntity) attacker);
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

package me.jonathing.minecraft.foragecraft.common.item;

import me.jonathing.minecraft.foragecraft.common.registry.ForageTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
     * @see Item#hurtEnemy(ItemStack, LivingEntity, LivingEntity)
     */
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity player = (ServerPlayerEntity) attacker;

            if (!player.isCreative() && !player.isSpectator())
            {
                DamageSource damage = new DamageSource("leek")
                {
                    @Override
                    public ITextComponent getLocalizedDeathMessage(LivingEntity target)
                    {
                        return new TranslationTextComponent(
                                String.format("death.attack.%s.player", this.msgId),
                                target.getDisplayName(),
                                attacker.getDisplayName());
                    }
                };
                target.hurt(damage, 4);
                itemStack.shrink(1);
                attacker.getCommandSenderWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 1, 1);
                ForageTriggers.LEEK_TRIGGER.trigger(player);
                return true;
            }
            else
            {
                return super.hurtEnemy(itemStack, target, attacker);
            }
        }
        else
        {
            return super.hurtEnemy(itemStack, target, attacker);
        }
    }
}

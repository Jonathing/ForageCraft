package me.jonathing.minecraft.foragecraft.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class LeekItem extends Item
{
    public LeekItem(Item.Properties properties)
    {
        super(properties);
    }

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
                attacker.getEntityWorld().playSound((PlayerEntity) null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 1, 1);
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

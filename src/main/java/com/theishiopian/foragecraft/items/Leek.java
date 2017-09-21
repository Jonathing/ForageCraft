package com.theishiopian.foragecraft.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Leek extends ItemFood
{

	public Leek(int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName("leek");
		this.setRegistryName("leek");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		target.attackEntityFrom(DamageSource.GENERIC, 4);
		
		if (((EntityPlayer)attacker).capabilities.isCreativeMode==false)
        {
			((EntityPlayer)attacker).inventory.clearMatchingItems(stack.getItem(), -1, 1, null);
			attacker.getEntityWorld().playSound((EntityPlayer)null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 1, 1);
        }
		
        return true;
    }
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}
}

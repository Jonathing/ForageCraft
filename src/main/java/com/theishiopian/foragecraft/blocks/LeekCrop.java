package com.theishiopian.foragecraft.blocks;

import com.theishiopian.foragecraft.init.ModItems;

import net.minecraft.item.Item;

public class LeekCrop extends ModCropsBasic
{
	public LeekCrop()
	{
		super();
		String name = "leek_block";
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
	
	@Override
	public int getMaxAge()
    {
        return 7;
    }

	@Override
	public Item getSeed()
	{
		return ModItems.leek_seeds;
	}

	@Override
	public Item getCrop()
	{
		return ModItems.leek;
	}
}

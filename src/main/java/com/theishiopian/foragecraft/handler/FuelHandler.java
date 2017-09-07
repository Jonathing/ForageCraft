package com.theishiopian.foragecraft.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

@SuppressWarnings("deprecation")
public class FuelHandler implements IFuelHandler 
{

	@Override
	public int getBurnTime(ItemStack fuel) 
	{
		if(fuel.getItem()==Item.getByNameOrId("foragecraft:stick_bundle"))return 20*5*9; //enough to smelt ~4 items
		if(fuel.getItem()==Item.getByNameOrId("foragecraft:fascine"))return 20*5*9*9; // enough for 40 items
		return 0;
	}
}

// REMEMBER: always mutliply the amount of seconds by 20, since minecraft had too much coffee
// and measures time in 1/20th of a second increments

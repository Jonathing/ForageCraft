package com.theishiopian.foragecraft;

import com.theishiopian.foragecraft.config.ConfigVariables;
import com.theishiopian.foragecraft.init.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class SeedLoader {
	
	public static void seed()
	{
		// Loaded Seeds based on config file
		if(ConfigVariables.pumpkinSeeds)MinecraftForge.addGrassSeed(new ItemStack(Items.PUMPKIN_SEEDS),6);
		if(ConfigVariables.melonSeeds)MinecraftForge.addGrassSeed(new ItemStack(Items.MELON_SEEDS),6);
		if(ConfigVariables.beetrootSeeds)MinecraftForge.addGrassSeed(new ItemStack(Items.BEETROOT_SEEDS), 4);
		
		//Mod seeds (they dont need config entries)
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.leek_seeds),8);
	}
}

package com.theishiopian.foragecraft.init;

import com.theishiopian.foragecraft.items.StickBundle;
import com.theishiopian.foragecraft.items.Straw;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems 
{
	
	public static Item stick_bundle;
	public static Item straw;
	
	public static void init()
	{
		stick_bundle = new StickBundle();
		straw = new Straw();
	}
	
	public static void register()
	{
		registerItem(stick_bundle);
		registerItem(straw);
	}
	
	private static void registerItem(Item item)
	{
		GameRegistry.register(item);
	}
	
	public static void registerRenders()
	{
		registerRender(stick_bundle);
		registerRender(straw);
	}
	
	public static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "Inventory"));
	}
}

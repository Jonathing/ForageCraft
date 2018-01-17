package com.theishiopian.foragecraft.proxy;

import com.theishiopian.foragecraft.init.ModBlocks;
import com.theishiopian.foragecraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy 
{
	public void preInit(FMLPreInitializationEvent e) 
	{
        
    }

    public void init(FMLInitializationEvent e) 
    {
        
    }

    public void postInit(FMLPostInitializationEvent e) 
    {
       
    }
    
    @SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e)
	{
		regBlock(e, ModBlocks.fascine);
		regBlock(e, ModBlocks.straw_bale);
		regBlock(e, ModBlocks.scarecrow);
		regBlock(e, ModBlocks.road_stone);
		regBlock(e, ModBlocks.stick_block);
		regBlock(e, ModBlocks.rock_normal);
		regBlock(e, ModBlocks.rock_flat);
		regBlock(e, ModBlocks.leek_block);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e)
	{
		regItem(e, ModItems.stick_bundle);
		regItem(e, ModItems.straw);
		regItem(e, new ItemBlock(ModBlocks.fascine).setRegistryName(ModBlocks.fascine.getRegistryName()));
		regItem(e, new ItemBlock(ModBlocks.straw_bale).setRegistryName(ModBlocks.straw_bale.getRegistryName()));
		regItem(e, new ItemBlock(ModBlocks.road_stone).setRegistryName(ModBlocks.road_stone.getRegistryName()));
		regItem(e, new ItemBlock(ModBlocks.scarecrow).setRegistryName(ModBlocks.scarecrow.getRegistryName()));
		regItem(e, new ItemBlock(ModBlocks.stick_block).setRegistryName(ModBlocks.stick_block.getRegistryName()));//for creative mode players
		regItem(e, ModItems.rock_normal);
		regItem(e, ModItems.rock_flat);
		regItem(e, ModItems.spaghetti);
		regItem(e, ModItems.leek_seeds);
		regItem(e, ModItems.leek);
	}

	public static void regBlock(RegistryEvent.Register<Block> e, Block block)
	{
		e.getRegistry().register(block);
	}

	public static void regItem(RegistryEvent.Register<Item> e, Item item)
	{
		e.getRegistry().register(item);
	}
	
	
}
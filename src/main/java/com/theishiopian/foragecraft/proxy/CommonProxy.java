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
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(ModBlocks.fascine);
		event.getRegistry().register(ModBlocks.straw_bale);
		event.getRegistry().register(ModBlocks.scarecrow);
		event.getRegistry().register(ModBlocks.road_stone);
		event.getRegistry().register(ModBlocks.stick_block);
		event.getRegistry().register(ModBlocks.rock_normal);
		event.getRegistry().register(ModBlocks.rock_flat);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(ModItems.stick_bundle);
		event.getRegistry().register(ModItems.straw);
		event.getRegistry().register(new ItemBlock(ModBlocks.fascine).setRegistryName(ModBlocks.fascine.getRegistryName()));
		event.getRegistry().register(new ItemBlock(ModBlocks.straw_bale).setRegistryName(ModBlocks.straw_bale.getRegistryName()));
		event.getRegistry().register(new ItemBlock(ModBlocks.road_stone).setRegistryName(ModBlocks.road_stone.getRegistryName()));
		event.getRegistry().register(new ItemBlock(ModBlocks.scarecrow).setRegistryName(ModBlocks.scarecrow.getRegistryName()));
		event.getRegistry().register(ModItems.rock_normal);
		event.getRegistry().register(ModItems.rock_flat);
		event.getRegistry().register(ModItems.spaghetti);
	}
	
	
}
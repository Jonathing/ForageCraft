package com.theishiopian.foragecraft.proxy;

import com.theishiopian.foragecraft.init.ModItems;
import net.minecraft.item.Item;
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
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(ModItems.stick_bundle);
		event.getRegistry().register(ModItems.straw);
	}
}
//fuck my life
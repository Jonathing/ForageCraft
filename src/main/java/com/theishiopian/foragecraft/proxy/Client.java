package com.theishiopian.foragecraft.proxy;

import com.theishiopian.foragecraft.entity.EntityCrow;
import com.theishiopian.foragecraft.entity.EntityRockFlat;
import com.theishiopian.foragecraft.entity.EntityRockNormal;
import com.theishiopian.foragecraft.init.ModBlocks;
import com.theishiopian.foragecraft.init.ModItems;
import com.theishiopian.foragecraft.render.RenderCrow;
import com.theishiopian.foragecraft.render.RenderRockFlat;
import com.theishiopian.foragecraft.render.RenderRockNormal;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Client extends CommonProxy
{
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		ModBlocks.initModels();
		ModItems.initModels();
		RenderingRegistry.registerEntityRenderingHandler(EntityRockNormal.class, RenderRockNormal.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityRockFlat.class, RenderRockFlat.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCrow.class, RenderCrow.FACTORY);
	}
}

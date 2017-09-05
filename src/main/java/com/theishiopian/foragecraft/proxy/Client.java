package com.theishiopian.foragecraft.proxy;

import com.theishiopian.foragecraft.entity.EntityRockFlat;
import com.theishiopian.foragecraft.entity.EntityRockNormal;
import com.theishiopian.foragecraft.init.ModItems;
import com.theishiopian.foragecraft.render.RenderRockFlat;
import com.theishiopian.foragecraft.render.RenderRockNormal;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class Client extends CommonProxy
{
	@Override
	public void init()
	{
		//curently FUBAR
		ModItems.initModels();
		RenderingRegistry.registerEntityRenderingHandler(EntityRockNormal.class, RenderRockNormal.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityRockFlat.class, RenderRockFlat.FACTORY);
	}
}

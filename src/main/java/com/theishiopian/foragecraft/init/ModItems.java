package com.theishiopian.foragecraft.init;

import com.theishiopian.foragecraft.items.StickBundle;
import com.theishiopian.foragecraft.items.Straw;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{

	public static StickBundle stick_bundle;
	public static Straw straw;

	public static void init()
	{
		stick_bundle = new StickBundle();
		straw = new Straw();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		//TODO: create superclass for all foragecraft items, to make adding new ones go faster
		stick_bundle.initModel();
		straw.initModel();
	}
}

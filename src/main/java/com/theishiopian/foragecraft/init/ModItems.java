package com.theishiopian.foragecraft.init;

import com.theishiopian.foragecraft.init.ModBlocks.RockType;
import com.theishiopian.foragecraft.items.RockItem;
import com.theishiopian.foragecraft.items.Spaghetti;
import com.theishiopian.foragecraft.items.StickBundle;
import com.theishiopian.foragecraft.items.Straw;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{
	public static StickBundle stick_bundle;
	public static Straw straw;
	public static RockItem rock_normal;
	public static RockItem rock_flat;
	public static Spaghetti spaghetti;

	public static void init()
	{
		stick_bundle = new StickBundle();
		straw = new Straw();
		rock_normal = new RockItem(ModBlocks.rock_normal, RockType.NORMAL);
		rock_flat = new RockItem(ModBlocks.rock_flat, RockType.FLAT);
		spaghetti = new Spaghetti(11, 20, false);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		stick_bundle.initModel();
		straw.initModel();
		rock_normal.initModel();
		rock_flat.initModel();
		spaghetti.initModel();
	}
}

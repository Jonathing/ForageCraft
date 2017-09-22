package com.theishiopian.foragecraft.init;

import com.theishiopian.foragecraft.init.ModBlocks.RockType;
import com.theishiopian.foragecraft.items.Leek;
import com.theishiopian.foragecraft.items.Modseeds;
import com.theishiopian.foragecraft.items.RockItem;
import com.theishiopian.foragecraft.items.Spaghetti;
import com.theishiopian.foragecraft.items.StickBundle;
import com.theishiopian.foragecraft.items.Straw;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{
	public static StickBundle stick_bundle;
	public static Straw straw;
	public static RockItem rock_normal;
	public static RockItem rock_flat;
	public static Spaghetti spaghetti;
	public static Modseeds leek_seeds;
	public static Leek leek;

	public static void init()
	{
		stick_bundle = new StickBundle();
		straw = new Straw();
		rock_normal = new RockItem(ModBlocks.rock_normal, RockType.NORMAL);
		rock_flat = new RockItem(ModBlocks.rock_flat, RockType.FLAT);
		spaghetti = new Spaghetti(11, 20, false);//keep those wolves away from my spaghetti!
		leek_seeds = (Modseeds) new Modseeds(ModBlocks.leek_block,Blocks.FARMLAND).setUnlocalizedName("leek_seeds").setRegistryName("leek_seeds");
		leek = new Leek(2,4, false);
	}
	
	
	
	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		stick_bundle.initModel();
		straw.initModel();
		rock_normal.initModel();
		rock_flat.initModel();
		spaghetti.initModel();
		leek_seeds.initModel();
		leek.initModel();
	}
}

package com.theishiopian.foragecraft.init;

import com.theishiopian.foragecraft.blocks.BlockStick;
import com.theishiopian.foragecraft.blocks.Fascine;
import com.theishiopian.foragecraft.blocks.RoadStone;
import com.theishiopian.foragecraft.blocks.RockBlock;
import com.theishiopian.foragecraft.blocks.Scarecrow;
import com.theishiopian.foragecraft.blocks.StrawBale;

public class ModBlocks 
{
	
	public static Fascine fascine;
	public static StrawBale straw_bale;
	public static Scarecrow scarecrow;
	public static RockBlock rock_normal;
	public static RockBlock rock_flat;
	public static RoadStone road_stone;
	public static BlockStick stick_block;
	
	public static enum RockType{NORMAL, FLAT};
	
	public static void init()
	{
		fascine = new Fascine();
		straw_bale = new StrawBale();
		scarecrow = new Scarecrow();
		rock_normal = new RockBlock(RockType.NORMAL);
		rock_flat = new RockBlock(RockType.FLAT);
		road_stone = new RoadStone();
		stick_block = new BlockStick();
	}

	public static void initModels()
	{
		fascine.initModel();
		straw_bale.initModel();
		scarecrow.initModel();
		rock_normal.initModel();
		rock_flat.initModel();
		road_stone.initModel();
		stick_block.initModel();
	}
}

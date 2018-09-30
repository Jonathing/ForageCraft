package com.theishiopian.foragecraft.handler;

import java.util.Random;

import com.theishiopian.foragecraft.ForageCraftMod;

import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockForageHandler
{
	// RNG's for randomness
	public static Random CHANCE = new java.util.Random();
	public static Random AMOUNT = new java.util.Random();

	@SubscribeEvent
	public void blockBreak(HarvestDropsEvent event)
	{
		//todo:
		/*
		 * create json parser
		 * parse custom json files at runtime via gson
		 * fuck loot tables
		 */
	}
}				


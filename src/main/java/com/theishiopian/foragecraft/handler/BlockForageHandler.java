package com.theishiopian.foragecraft.handler;

import java.util.Random;

import com.theishiopian.foragecraft.ForageCraftMod;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
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
		ResourceLocation table = ForageCraftMod.ForageTable.get(event.getState().getBlock());

		if(table != null)
		{
			System.out.println(table.toString());
			
			World world = event.getWorld();
			LootTable loottable = world.getLootTableManager().getLootTableFromLocation(table);
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer)world)).withPlayer(event.getHarvester());

            event.getDrops().clear();
            
            for (ItemStack itemstack : loottable.generateLootForPools(new Random(), lootcontext$builder.build()))
            {
                event.getDrops().add(itemstack);
                
                System.out.println("adding item "+itemstack.getItem().toString());
            }
		}
	}
}				


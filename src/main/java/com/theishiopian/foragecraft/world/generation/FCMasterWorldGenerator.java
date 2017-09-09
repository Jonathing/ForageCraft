package com.theishiopian.foragecraft.world.generation;

import java.util.Random;

import com.theishiopian.foragecraft.config.ConfigVariables;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

/*
/  World generation based on sky_01's MC forums tutorial
/  http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/2666351-1-8-x-and-1-9-structure-generation-tutorial
*/

public class FCMasterWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, net.minecraft.world.gen.IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider)
	{
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		
		switch (world.provider.getDimension())
		{
			case -1:
				generateNether(world, random, blockX, blockZ);
			break;
			case 0:
				generateOverworld(world, random, blockX, blockZ);
			break;
			case 1:
				generateEnd(world, random, blockX, blockZ);
			break;
		}
	}

	// TODO Make shit for the nether
	private void generateNether(World world, Random rand, int blockX, int blockZ)
	{

	}

	private void generateOverworld(World world, Random rand, int blockX, int blockZ)
	{
		WorldGenerator rocky = new RockGenerator();
		WorldGenerator sticky = new StickGenerator();

		int MIN = 32;
		int MAX = 1024;
		int rockRange = MIN + rand.nextInt(MAX - MIN);
		int stickRange = MIN + rand.nextInt(MAX - MIN);
		BlockPos biomeCheckPos = new BlockPos(blockX, 64, blockZ);
		
		// If the player decides to disable rocks.
		if(ConfigVariables.enableRocks)
		{
			// rocks can be found everywhere, even mushroom isles
			for (int i = 0; i < rockRange; i++)
			{
				int randX = blockX + rand.nextInt(16);
				int randY = rand.nextInt(255);
				int randZ = blockZ + rand.nextInt(16);
				BlockPos pos = new BlockPos(randX, randY, randZ);
				rocky.generate(world, rand, pos);
			}
		}

		// What's the if statement for??? What does it deactivate??? - Jonathan
		// Ohhhhh it makes sure that it's only in those biomes
		if
		(
			BiomeDictionary.hasType(world.getBiome(biomeCheckPos), BiomeDictionary.Type.FOREST)||
			BiomeDictionary.hasType(world.getBiome(biomeCheckPos), BiomeDictionary.Type.JUNGLE)||
			BiomeDictionary.hasType(world.getBiome(biomeCheckPos), BiomeDictionary.Type.SWAMP)
		)

		// If the player decides to disable sticks
		if(ConfigVariables.enableSticks)
		{
			for (int i = 0; i < stickRange; i++)
			{
				int randX = blockX + rand.nextInt(16);
				int randY = rand.nextInt(255);//I long for cubic chunks :(
				int randZ = blockZ + rand.nextInt(16);

				sticky.generate(world, rand, new BlockPos(randX, randY, randZ));
			}
		}
	}

	// TODO Make shit for the End maybe
	private void generateEnd(World world, Random rand, int blockX, int blockZ)
	{

	}
}

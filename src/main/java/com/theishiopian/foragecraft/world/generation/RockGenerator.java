package com.theishiopian.foragecraft.world.generation;

import java.util.Random;

import com.theishiopian.foragecraft.ConfigVariables;
import com.theishiopian.foragecraft.ForageLogger;
import com.theishiopian.foragecraft.Reference;
import com.theishiopian.foragecraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/*
/  World generation based on sky_01's MC forums tutorial
/  http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/2666351-1-8-x-and-1-9-structure-generation-tutorial
/
/  Logger based from Tinkers' Construct
/  https://github.com/SlimeKnights/TinkersConstruct/blob/08f7180399ca8ad4717493dd0aa5a63b7aa14584/src/main/java/slimeknights/tconstruct/TConstruct.java
*/

public class RockGenerator extends WorldGenerator
{
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos)
	{
		Random rng = new Random();

		int whatRock;
		
		Block rock = null;
		
		switch(rng.nextInt(2))
		{
			case 0:
				rock = ModBlocks.rock_normal;
				whatRock = 0;
				break;
			case 1:
				rock = ModBlocks.rock_flat;
				whatRock = 1;
				break;
			default:
				rock = ModBlocks.rock_normal;
				whatRock = 0;
				// break;
				// Add break in default case even though it's impossible to get to? - Jonathan
		}

		BlockPos rp = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

		BlockPos rpUnder = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());

		Block toReplace = worldIn.getBlockState(rp).getBlock();

		Block onTopOf = worldIn.getBlockState(rpUnder).getBlock();

		Biome biome = worldIn.getBiome(pos);
		
		if((toReplace == Blocks.AIR || toReplace == Blocks.TALLGRASS)
				&& (onTopOf != Blocks.RED_MUSHROOM_BLOCK && onTopOf != Blocks.BROWN_MUSHROOM_BLOCK)
				&& worldIn.getBlockState(rp.down()).isSideSolid(worldIn, pos, EnumFacing.UP))
		{
			//TODO: add sandstone rocks or something similar. Also seashells
			// TODO: Maybe also remove the desert biome along with the beach biome for generation? - Jonathan
			if(!BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
				if(biome != Biomes.BEACH)worldIn.setBlockState(rp, rock.getDefaultState(), 2);
			else if(!worldIn.canSeeSky(pos))
				worldIn.setBlockState(rp, rock.getDefaultState(), 2);

			// I'll manage the config vars Theishiopian. You don't need to worry about them. - Jonathan
			switch(whatRock)
			{
				case 0:
					ForageLogger.printDevelop("Generating rock at X: " + rp.getX() + " Y: " + rp.getY() + " Z: " + rp.getZ() + " on top of " + onTopOf + ".");
					break;
				case 1:
					ForageLogger.printDevelop("Generating flat rock at X: " + rp.getX() + " Y: " + rp.getY() + " Z: " + rp.getZ() + " on top of " + onTopOf + ".");
			}
		}

		return false;
	}

}

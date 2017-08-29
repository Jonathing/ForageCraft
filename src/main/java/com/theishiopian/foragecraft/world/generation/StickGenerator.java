package com.theishiopian.foragecraft.world.generation;

import java.util.Random;

import com.theishiopian.foragecraft.ConfigVariables;
import com.theishiopian.foragecraft.Reference;
import com.theishiopian.foragecraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
/  World generation based on sky_01's MC forums tutorial
/  http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/2666351-1-8-x-and-1-9-structure-generation-tutorial
/
/  Logger based from Tinkers' Construct
/  https://github.com/SlimeKnights/TinkersConstruct/blob/08f7180399ca8ad4717493dd0aa5a63b7aa14584/src/main/java/slimeknights/tconstruct/TConstruct.java
*/

public class StickGenerator extends WorldGenerator
{
	public static final Logger log = LogManager.getLogger(Reference.SHORT_NAME);

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos)
	{
		Block stick = ModBlocks.stick_block;

		BlockPos sp = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

		BlockPos spUnder = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());

		Block toReplace = worldIn.getBlockState(sp).getBlock();

		Block onTopOf = worldIn.getBlockState(spUnder).getBlock();

		if((toReplace == Blocks.AIR ||toReplace == Blocks.TALLGRASS)
				&& (worldIn.getBlockState(sp.down()).isSideSolid(worldIn, pos, EnumFacing.UP) && worldIn.getBlockState(sp.down()).getBlock()==Blocks.GRASS)
				&& pos.getY()>64)
		{
			worldIn.setBlockState(pos, stick.getDefaultState().withProperty(BlockPumpkin.FACING, EnumFacing.Plane.HORIZONTAL.random(rand)), 2);

			if(ConfigVariables.developerMode)
			{
				log.info("Generating stick at X: " + sp.getX() + " Y: " + sp.getY() + " Z: " + sp.getZ() + " on top of " + onTopOf + ".");
			}
		}

		return false;
	}

}

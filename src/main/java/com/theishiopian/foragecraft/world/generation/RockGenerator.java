package com.theishiopian.foragecraft.world.generation;

import com.theishiopian.foragecraft.ForageLogger;
import com.theishiopian.foragecraft.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

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
    @ParametersAreNonnullByDefault
    public boolean generate(World worldIn, Random rand, BlockPos pos)
    {
        Random rng = new Random();

        int whatRock;

        Block rock;

        if (rng.nextInt(2) == 1)
        {
            rock = ModBlocks.rock_flat;
            whatRock = 1;
        }
        else
        {
            rock = ModBlocks.rock_normal;
            whatRock = 0;
        }

        BlockPos rp = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

        BlockPos rpUnder = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());

        Block toReplace = worldIn.getBlockState(rp).getBlock();

        Block onTopOf = worldIn.getBlockState(rpUnder).getBlock();

        Biome biome = worldIn.getBiome(pos);

        if ((toReplace == Blocks.AIR || toReplace == Blocks.TALLGRASS)
                && this.isValidSpot(onTopOf)
                && worldIn.getBlockState(rp.down()).isSideSolid(worldIn, pos, EnumFacing.UP))
        {
            //TODO: add sandstone rocks or something similar. Also seashells
            if (this.isValidBiome(biome)) worldIn.setBlockState(rp, rock.getDefaultState(), 2);

            switch (whatRock)
            {
                case 0:
                    ForageLogger.printWorldGen("Generating rock at X: " + rp.getX() + " Y: " + rp.getY() + " Z: " + rp.getZ() + " on top of " + onTopOf + ".");
                    break;
                case 1:
                    ForageLogger.printWorldGen("Generating flat rock at X: " + rp.getX() + " Y: " + rp.getY() + " Z: " + rp.getZ() + " on top of " + onTopOf + ".");
                    break;
            }
        }

        return false;
    }

    //TODO possible merge these if at all possible

    public boolean isValidSpot(Block in)
    {
        //tighter control over what rocks generate on.
        Block[] array =
                {
                        Blocks.GRASS,
                        Blocks.GRAVEL,
                        Blocks.STONE
                };

        for (Block b : array)
        {
            if (in == b) return true;
        }
        return false;
    }

    public boolean isValidBiome(Biome in)
    {
        Biome[] array =
                {
                        Biomes.BEACH,
                        Biomes.DESERT,
                        Biomes.DESERT_HILLS
                };

        for (Biome b : array)
        {
            if (in == b) return false;
        }
        return true;
    }

}

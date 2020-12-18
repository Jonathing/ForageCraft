package me.jonathing.minecraft.foragecraft.common.compat.patchouli;

import me.jonathing.minecraft.foragecraft.common.compat.ModCompatHandler;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.*;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * This mod compatibility class helps generate {@link IMultiblock}s for use in the Patchouli guide book. It is better,
 * in our case, to do this through code rather than directly in the json data file so we are able to specify precise
 * {@link Block}s and {@link BlockState}s.
 *
 * @author Jonathing
 * @see ModCompatHandler#init()
 * @since 2.0.1
 */
public class PatchouliHelper
{
    /**
     * @see #registerMultiblocks()
     */
    private static final String[][] THE_SURFACE = new String[][]
            {
                    new String[] {" R Y ", "S PR ", " YFB ", "  AS "},
                    new String[] {" GGG ", "GGGGG", "GG0GG", " GGG "},
                    new String[] {"  D  ", " DDD ", " DDD ", "  D  "}
            };

    /**
     * @see #registerMultiblocks()
     */
    private static final String[][] THE_UNDERGROUND = new String[][]
            {
                    new String[] {"     ", "1  G ", "G  S ", " C2  "},
                    new String[] {" SES ", "SCSdS", "SS0dS", " SSE "},
                    new String[] {"  S  ", " SSS ", " DSS ", "  S  "}
            };

    private static final String[][] SINGLE_BLOCK = new String[][]
            {
                    new String[] {"   ", " 0 ", "   "},
                    new String[] {"111", "111", "111"}
            };

    public static void registerMultiblocks()
    {
        IMultiblock theSurface = PatchouliAPI.get()
                .makeMultiblock(THE_SURFACE,
                        '0', Blocks.GRASS_BLOCK, // center block
                        'D', Blocks.DIRT,
                        'G', Blocks.GRASS_BLOCK,
                        'R', ForageBlocks.rock,
                        'Y', Blocks.DANDELION,
                        'S', ForageBlocks.stick,
                        'P', Blocks.POPPY,
                        'F', ForageBlocks.flat_rock,
                        'B', Blocks.BLUE_ORCHID,
                        'A', Blocks.ALLIUM)
                .setSymmetrical(true);

        IMultiblock theUnderground = PatchouliAPI.get()
                .makeMultiblock(THE_UNDERGROUND,
                        '0', Blocks.GOLD_ORE, // center block
                        'S', Blocks.STONE,
                        'd', Blocks.DIRT,
                        'D', Blocks.DIAMOND_ORE,
                        'E', Blocks.EMERALD_ORE,
                        'C', Blocks.COAL_ORE,
                        '1', Blocks.SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, 10),
                        'G', Blocks.GRAVEL,
                        '2', Blocks.SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, 13))
                .setSymmetrical(true);

        IMultiblock singleStick = PatchouliAPI.get()
                .makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.stick,
                        '1', Blocks.GRASS_BLOCK);

        IMultiblock singleRock = PatchouliAPI.get()
                .makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.rock,
                        '1', Blocks.GRASS_BLOCK);

        IMultiblock singleFlatRock = PatchouliAPI.get()
                .makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.flat_rock,
                        '1', Blocks.GRASS_BLOCK);

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "the_surface"), theSurface);
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "the_underground"), theUnderground);
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_stick"), singleStick);
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_rock"), singleRock);
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_flat_rock"), singleFlatRock);
    }
}

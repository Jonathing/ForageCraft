package me.jonathing.minecraft.foragecraft.common.compat.patchouli;

import me.jonathing.minecraft.foragecraft.common.block.StickBlock;
import me.jonathing.minecraft.foragecraft.common.compat.ForageModCompat;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.*;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * This mod compatibility class helps generate {@link IMultiblock}s for use in the Patchouli guide book. It is better,
 * in our case, to do this through code rather than directly in the json data file so we are able to specify precise
 * {@link Block}s and {@link BlockState}s.
 *
 * @author Jonathing
 * @see ForageModCompat#init()
 * @since 2.1.0
 */
public class PatchouliHelper
{
    /**
     * This is the 2D {@link String} array, used to make a {@link IMultiblock} in {@link #registerMultiblocks()}, for
     * the "The Surface" page in the Patchouli book.
     *
     * @see #registerMultiblocks()
     */
    public static final String[][] THE_SURFACE = new String[][]
            {
                    new String[]{" R Y ", "S PR ", " YFB ", "  As "},
                    new String[]{" GGG ", "GGGGG", "GG0GG", " GGG "},
                    new String[]{"  D  ", " DDD ", " DDD ", "  D  "}
            };

    /**
     * This is the 2D {@link String} array, used to make a {@link IMultiblock} in {@link #registerMultiblocks()}, for
     * the "The Underground" page in the Patchouli book.
     *
     * @see #registerMultiblocks()
     */
    public static final String[][] THE_UNDERGROUND = new String[][]
            {
                    new String[]{"     ", "1  G ", "G  S ", " C2  "},
                    new String[]{" SES ", "SCSdS", "SS0dS", " SSE "},
                    new String[]{"  S  ", " SSS ", " DSS ", "  S  "}
            };

    /**
     * This is the 2D {@link String} array, used to make a {@link IMultiblock} in {@link #registerMultiblocks()}, for
     * the "The Nether" page in the Patchouli book.
     *
     * @see #registerMultiblocks()
     */
    public static final String[][] THE_NETHER = new String[][]
            {
                    new String[]{"  1  ", "     ", "     ", "  1  "},
                    new String[]{"  N  ", "  O  ", "  O  ", "  N  "},
                    new String[]{" IOi ", "  P  ", "  P  ", " IOi "},
                    new String[]{" LOl ", "  P  ", "  P  ", " LOl "},
                    new String[]{" FO  ", "R PfB", " 3P 2", " bOr "},
                    new String[]{" WNC ", "WWOCC", "WW0CC", " WNC "},
                    new String[]{"  N  ", " QNN ", " NNG ", "  N  "}
            };

    /**
     * This is the 2D {@link String} array, used to make a {@link IMultiblock} in {@link #registerMultiblocks()}, for
     * any single block page in the Patchouli book.
     *
     * @see #registerMultiblocks()
     */
    public static final String[][] SINGLE_BLOCK = new String[][]
            {
                    new String[]{"   ", " 0 ", "   "},
                    new String[]{"111", "111", "111"}
            };

    /**
     * Registers all of the multiblocks into the game.
     *
     * @see PatchouliHelper
     */
    public static void registerMultiblocks()
    {
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "the_surface"),
                PatchouliAPI.get().makeMultiblock(THE_SURFACE,
                        '0', Blocks.GRASS_BLOCK, // center block
                        'D', Blocks.DIRT,
                        'G', Blocks.GRASS_BLOCK,
                        'R', ForageBlocks.rock,
                        'Y', Blocks.DANDELION,
                        'S', ForageBlocks.stick.defaultBlockState(),
                        's', ForageBlocks.stick.defaultBlockState().setValue(StickBlock.FACING, Direction.SOUTH),
                        'P', Blocks.POPPY,
                        'F', ForageBlocks.flat_rock,
                        'B', Blocks.BLUE_ORCHID,
                        'A', Blocks.ALLIUM)
                        .setSymmetrical(true));

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "the_underground"),
                PatchouliAPI.get().makeMultiblock(THE_UNDERGROUND,
                        '0', Blocks.GOLD_ORE, // center block
                        'S', Blocks.STONE,
                        'd', Blocks.DIRT,
                        'D', Blocks.DIAMOND_ORE,
                        'E', Blocks.EMERALD_ORE,
                        'C', Blocks.COAL_ORE,
                        '1', Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 10),
                        'G', Blocks.GRAVEL,
                        '2', Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, 13))
                        .setSymmetrical(true));

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "the_nether"),
                PatchouliAPI.get().makeMultiblock(THE_NETHER,
                        '0', Blocks.OBSIDIAN, // center block
                        'N', Blocks.NETHERRACK,
                        'Q', Blocks.NETHER_QUARTZ_ORE,
                        'G', Blocks.NETHER_GOLD_ORE,
                        'W', Blocks.WARPED_NYLIUM,
                        'C', Blocks.CRIMSON_NYLIUM,
                        'O', Blocks.OBSIDIAN,
                        'R', Blocks.WARPED_ROOTS,
                        'F', Blocks.WARPED_FUNGUS,
                        'P', Blocks.NETHER_PORTAL.defaultBlockState().setValue(NetherPortalBlock.AXIS, Direction.Axis.X),
                        'f', Blocks.CRIMSON_FUNGUS,
                        'B', Blocks.BLACKSTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM),
                        'b', Blocks.POLISHED_BLACKSTONE_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM),
                        'r', Blocks.CRIMSON_ROOTS,
                        'L', Blocks.SOUL_LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true),
                        'l', Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true),
                        'I', Blocks.IRON_BARS.defaultBlockState().setValue(PaneBlock.SOUTH, true),
                        'i', Blocks.IRON_BARS.defaultBlockState().setValue(PaneBlock.NORTH, true),
                        '1', Blocks.FIRE.defaultBlockState().setValue(FireBlock.AGE, 0),
                        '2', ForageBlocks.blackstone_rock,
                        '3', ForageBlocks.blackstone_flat_rock)
                        .setSymmetrical(true));

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_stick"),
                PatchouliAPI.get().makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.stick.defaultBlockState().setValue(StickBlock.FACING, Direction.SOUTH), // center block
                        '1', Blocks.GRASS_BLOCK));

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_rock"),
                PatchouliAPI.get().makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.rock, // center block
                        '1', Blocks.GRASS_BLOCK));

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_flat_rock"),
                PatchouliAPI.get().makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.flat_rock, // center block
                        '1', Blocks.GRASS_BLOCK));

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_blackstone_rock"),
                PatchouliAPI.get().makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.blackstone_rock, // center block
                        '1', Blocks.NETHERRACK));

        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_blackstone_flat_rock"),
                PatchouliAPI.get().makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.blackstone_flat_rock, // center block
                        '1', Blocks.NETHERRACK));
    }
}

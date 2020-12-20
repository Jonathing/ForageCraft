package me.jonathing.minecraft.foragecraft.common.compat.patchouli;

import me.jonathing.minecraft.foragecraft.common.block.StickBlock;
import me.jonathing.minecraft.foragecraft.common.compat.ModCompatHandler;
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
 * @see ModCompatHandler#init()
 * @since 2.0.1
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
                    new String[]{" FO  ", "R PfB", "  P  ", " bOr "},
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
        IMultiblock theSurface = PatchouliAPI.get()
                .makeMultiblock(THE_SURFACE,
                        '0', Blocks.GRASS_BLOCK, // center block
                        'D', Blocks.DIRT,
                        'G', Blocks.GRASS_BLOCK,
                        'R', ForageBlocks.rock,
                        'Y', Blocks.DANDELION,
                        'S', ForageBlocks.stick.getDefaultState(),
                        's', ForageBlocks.stick.getDefaultState().with(StickBlock.FACING, Direction.SOUTH),
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

        IMultiblock theNether = PatchouliAPI.get()
                .makeMultiblock(THE_NETHER,
                        '0', Blocks.OBSIDIAN,
                        'N', Blocks.NETHERRACK,
                        'Q', Blocks.NETHER_QUARTZ_ORE,
                        'G', Blocks.NETHER_GOLD_ORE,
                        'W', Blocks.WARPED_NYLIUM,
                        'C', Blocks.CRIMSON_NYLIUM,
                        'O', Blocks.OBSIDIAN,
                        'R', Blocks.WARPED_ROOTS,
                        'F', Blocks.WARPED_FUNGUS,
                        'P', Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, Direction.Axis.X),
                        'f', Blocks.CRIMSON_FUNGUS,
                        'B', Blocks.BLACKSTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM),
                        'b', Blocks.POLISHED_BLACKSTONE_BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM),
                        'r', Blocks.CRIMSON_ROOTS,
                        'L', Blocks.SOUL_LANTERN.getDefaultState().with(LanternBlock.HANGING, true),
                        'l', Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true),
                        'I', Blocks.IRON_BARS.getDefaultState().with(PaneBlock.SOUTH, true),
                        'i', Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true),
                        '1', Blocks.FIRE.getDefaultState().with(FireBlock.AGE, 0))
                .setSymmetrical(true);

        IMultiblock singleStick = PatchouliAPI.get()
                .makeMultiblock(SINGLE_BLOCK,
                        '0', ForageBlocks.stick.getDefaultState().with(StickBlock.FACING, Direction.SOUTH),
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
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "the_nether"), theNether);
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_stick"), singleStick);
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_rock"), singleRock);
        PatchouliAPI.get().registerMultiblock(new ResourceLocation(ForageInfo.MOD_ID, "single_flat_rock"), singleFlatRock);
    }
}

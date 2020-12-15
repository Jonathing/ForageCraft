package me.jonathing.minecraft.foragecraft.common.handler;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;

/**
 * This class handles the foraging loot for vanilla blocks in the game. If I ever need to make loot for any of my own
 * blocks, I will do so with data generation.
 *
 * @author Jonathing
 * @see #onBlockBroken(BlockEvent.BreakEvent)
 * @since 2.0.0
 */
@Mod.EventBusSubscriber
public class ForagingEventHandler
{
    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when grass is broken,
     * and the item itself. The list uses {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     */
    private static final NonNullLazy<List<Triple<Float, Item, Integer>>> GRASS_BLOCK_DROPS = NonNullLazy.of(() -> Arrays.asList(
            Triple.of(0.09f, Items.STICK, 1),
            Triple.of(0.01f, Items.CARROT, 1),
            Triple.of(0.01f, Items.POTATO, 1),
            Triple.of(0.01f, Items.POISONOUS_POTATO, 1),
            Triple.of(0.01f, Items.BEETROOT, 1),
            Triple.of(0.01f, Items.CARROT, 1),
            Triple.of(0.005f, Items.BONE, 9),
            Triple.of(0.005f, Items.SKELETON_SKULL, 1)));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when dirt is mined,
     * and the item itself. The list uses {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     */
    private static final NonNullLazy<List<Triple<Float, Item, Integer>>> DIRT_DROPS = NonNullLazy.of(() -> Arrays.asList(
            Triple.of(0.07f, Items.STICK, 1),
            Triple.of(0.04f, Items.FLINT, 1),
            Triple.of(0.01f, Items.POTATO, 1),
            Triple.of(0.005f, Items.BONE, 9),
            Triple.of(0.005f, Items.SKELETON_SKULL, 1)));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when stone is mined,
     * and the item itself. The list uses {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     */
    private static final NonNullLazy<List<Triple<Float, Item, Integer>>> STONE_DROPS = NonNullLazy.of(() -> Arrays.asList(
            Triple.of(0.005f, Items.GOLD_NUGGET, 1),
            Triple.of(0.05f, Items.FLINT, 1)));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when coal ore is
     * mined, and the item itself. The list uses {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     */
    private static final NonNullLazy<List<Triple<Float, Item, Integer>>> COAL_ORE_DROPS = NonNullLazy.of(() -> Arrays.asList(
            Triple.of(0.001f, Items.DIAMOND, 1),
            Triple.of(0.001f, Items.EMERALD, 1)));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when nether quartz ore
     * is mined, and the item itself. The list uses {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     */
    private static final NonNullLazy<List<Triple<Float, Item, Integer>>> NETHER_QUARTZ_ORE_DROPS = NonNullLazy.of(() -> Arrays.asList(
            Triple.of(0.5f, Items.GOLD_NUGGET, 9)));

    /**
     * For vanilla blocks, I am not able to create JSON files through data generation. I could have
     * a JSON parser do this for me, but for now, I'd rather put a list of files in here and let
     * Forge do all of the work. Essentially, this method runs at the block broken event, checks
     * to see if a random percentage is met, and if it is, drop an extra item.
     *
     * @param event The {@code BlockEvent.BreakEvent} event that carries the information about the
     *              broken block.
     * @see net.minecraftforge.event.world.BlockEvent.BreakEvent
     */
    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event)
    {
        PlayerEntity player = event.getPlayer();
        World world = (World) event.getWorld();
        if (world.isRemote || player.isCreative() || player.isSpectator()) return;

        // TODO Make configuration values.
        Block blockBroken = event.getState().getBlock();
        System.out.println("blockBroken = " + blockBroken);
        if (Blocks.GRASS_BLOCK.equals(blockBroken))
        {
            GRASS_BLOCK_DROPS.get().forEach(p ->
            {
                if (world.getRandom().nextFloat() < p.getLeft())
                    Block.spawnAsEntity(world, event.getPos(), new ItemStack(p.getMiddle(), world.getRandom().nextInt(p.getRight() + 1)));
            });
        }
        else if (Blocks.DIRT.equals(blockBroken))
        {
            DIRT_DROPS.get().forEach(p ->
            {
                if (world.getRandom().nextFloat() < p.getLeft())
                    Block.spawnAsEntity(world, event.getPos(), new ItemStack(p.getMiddle(), world.getRandom().nextInt(p.getRight() + 1)));
            });
        }
        else if (Blocks.STONE.equals(blockBroken))
        {
            STONE_DROPS.get().forEach(p ->
            {
                if (world.getRandom().nextFloat() < p.getLeft())
                    Block.spawnAsEntity(world, event.getPos(), new ItemStack(p.getMiddle(), world.getRandom().nextInt(p.getRight() + 1)));
            });
        }
        else if (Blocks.COAL_ORE.equals(blockBroken))
        {
            COAL_ORE_DROPS.get().forEach(p ->
            {
                if (world.getRandom().nextFloat() < p.getLeft())
                    Block.spawnAsEntity(world, event.getPos(), new ItemStack(p.getMiddle(), world.getRandom().nextInt(p.getRight() + 1)));
            });
        }
        else if (Blocks.NETHER_QUARTZ_ORE.equals(blockBroken))
        {
            NETHER_QUARTZ_ORE_DROPS.get().forEach(p ->
            {
                if (world.getRandom().nextFloat() < p.getLeft())
                    Block.spawnAsEntity(world, event.getPos(), new ItemStack(p.getMiddle(), world.getRandom().nextInt(p.getRight() + 1)));
            });
        }
    }
}

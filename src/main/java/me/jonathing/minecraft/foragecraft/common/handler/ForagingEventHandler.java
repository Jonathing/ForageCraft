package me.jonathing.minecraft.foragecraft.common.handler;

import com.google.common.collect.ImmutableList;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import me.jonathing.minecraft.foragecraft.common.registry.ForageTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

/**
 * This class handles the foraging loot for vanilla blocks in the game. If I ever need to make loot for any of my own
 * blocks, I will do so with data generation.
 *
 * @author Jonathing
 * @see #onBlockBroken(BlockEvent.BreakEvent)
 * @since 2.0.0
 */
// TODO Make configuration values.
@Mod.EventBusSubscriber
public class ForagingEventHandler
{
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when grass is broken,
     * and the item itself. The list uses a {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> GRASS_BLOCK_DROPS = () -> Arrays.asList(
            Triple.of(0.09f, Items.STICK, 1),
            Triple.of(0.02f, Items.CARROT, 1),
            Triple.of(0.025f, Items.POTATO, 1),
            Triple.of(0.01f, Items.POISONOUS_POTATO, 1),
            Triple.of(0.02f, Items.BEETROOT, 1),
            Triple.of(0.01f, Items.BONE, 9),
            Triple.of(0.005f, Items.SKELETON_SKULL, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when dirt is mined,
     * and the item itself. The list uses a {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> DIRT_DROPS = () -> Arrays.asList(
            Triple.of(0.07f, Items.STICK, 1),
            Triple.of(0.04f, Items.FLINT, 1),
            Triple.of(0.02f, Items.POTATO, 1),
            Triple.of(0.01f, Items.BONE, 9),
            Triple.of(0.005f, Items.SKELETON_SKULL, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when stone is mined,
     * and the item itself. The list uses a {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> STONE_DROPS = () -> Arrays.asList(
            Triple.of(0.005f, Items.GOLD_NUGGET, 1),
            Triple.of(0.05f, Items.FLINT, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when coal ore is
     * mined, and the item itself. The list uses a {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> COAL_ORE_DROPS = () -> Arrays.asList(
            Triple.of(0.001f, Items.DIAMOND, 1),
            Triple.of(0.001f, Items.EMERALD, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing the chance for an item to be dropped when nether quartz ore
     * is mined, and the item itself. The list uses a {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> NETHER_QUARTZ_ORE_DROPS = () -> Arrays.asList(
            Triple.of(0.5f, Items.GOLD_NUGGET, 9));

    // TODO: Document this.
    private static final Map<Block, List<Triple<Float, Item, Integer>>> FORAGE_EVENT_REGISTRY = new HashMap<>();

    // TODO: Document this
    public static void init()
    {
        FORAGE_EVENT_REGISTRY.put(Blocks.GRASS_BLOCK, GRASS_BLOCK_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.DIRT, DIRT_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.STONE, STONE_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.COAL_ORE, COAL_ORE_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.NETHER_QUARTZ_ORE, NETHER_QUARTZ_ORE_DROPS.get());
    }

    // TODO: Document this
    public static void registerDrop(Block block, List<Triple<Float, Item, Integer>> drop)
    {
        if (!FORAGE_EVENT_REGISTRY.containsKey(block))
        {
            FORAGE_EVENT_REGISTRY.put(block, drop);
        }
        else
        {
            // TODO: Test this later.
            FORAGE_EVENT_REGISTRY.get(block).addAll(drop);
        }
    }

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
        if (((World) event.getWorld()).isRemote || player.isCreative() || player.isSpectator()) return;

        Block blockBroken = event.getState().getBlock();

        if (FORAGE_EVENT_REGISTRY.containsKey(blockBroken))
            forageDrop(FORAGE_EVENT_REGISTRY.get(blockBroken), event);
    }

    // TODO: Document this
    private static void forageDrop(List<Triple<Float, Item, Integer>> dropList, BlockEvent.BreakEvent event)
    {
        World world = ((World) event.getWorld());
        Random random = world.getRandom();
        ServerPlayerEntity playerEntity = (ServerPlayerEntity) event.getPlayer();
        Block blockBroken = event.getState().getBlock();

        Triple<Float, Item, Integer> drop = dropList.get(world.getRandom().nextInt(dropList.size()));
        float chance = drop.getLeft();
        Item item = drop.getMiddle();
        int maxStack = drop.getRight();

        if (random.nextFloat() < chance)
        {
            LOGGER.trace(String.format("%s DROPPING %s", blockBroken.toString(), item.toString()));
            Block.spawnAsEntity(world, event.getPos(), new ItemStack(item, random.nextInt(maxStack) + 1));

            ForageTriggers.FORAGING_TRIGGER.trigger(playerEntity, blockBroken, item);
        }
    }
}

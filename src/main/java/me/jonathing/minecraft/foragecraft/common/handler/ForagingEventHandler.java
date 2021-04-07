package me.jonathing.minecraft.foragecraft.common.handler;

import me.jonathing.minecraft.foragecraft.common.registry.ForageCapabilities;
import me.jonathing.minecraft.foragecraft.common.registry.ForageTriggers;
import me.jonathing.minecraft.foragecraft.common.util.MathUtil;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.event.TickEvent;
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
@Mod.EventBusSubscriber(modid = ForageInfo.MOD_ID)
public class ForagingEventHandler
{
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Once true, the {@link #init()} method will do nothing.
     *
     * @see #init()
     * @since 2.1.0
     */
    private static boolean mainDropsInitialized = false;

    private static Map<UUID, Integer> cooldownMap = new HashMap<>();

    /**
     * Contains a {@link List} of {@link Triple}s containing drop information when a {@link Blocks#GRASS_BLOCK} is
     * broken. The {@link Float} on the left is the chance of the item dropping (1.00F being 100%), the {@link Item} in
     * the middle is the item to be dropped, and the {@link Integer} on the right is the max stack size. The list uses a
     * {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> GRASS_BLOCK_DROPS = () -> Arrays.asList(
            Triple.of(0.09f, Items.STICK, 1),
            Triple.of(0.01f, Items.CARROT, 1),
            Triple.of(0.01f, Items.POTATO, 1),
            Triple.of(0.01f, Items.POISONOUS_POTATO, 1),
            Triple.of(0.01f, Items.BEETROOT, 1),
            Triple.of(0.005f, Items.BONE, 9),
            Triple.of(0.0025f, Items.SKELETON_SKULL, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing drop information when a {@link Blocks#DIRT} is broken. The
     * {@link Float} on the left is the chance of the item dropping (1.00F being 100%), the {@link Item} in the middle
     * is the item to be dropped, and the {@link Integer} on the right is the max stack size. The list uses a
     * {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> DIRT_DROPS = () -> Arrays.asList(
            Triple.of(0.07f, Items.STICK, 1),
            Triple.of(0.04f, Items.FLINT, 1),
            Triple.of(0.01f, Items.POTATO, 1),
            Triple.of(0.01f, Items.POISONOUS_POTATO, 1),
            Triple.of(0.005f, Items.BONE, 9),
            Triple.of(0.005f, Items.SKELETON_SKULL, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing drop information when a {@link Blocks#STONE} is broken. The
     * {@link Float} on the left is the chance of the item dropping (1.00F being 100%), the {@link Item} in the middle
     * is the item to be dropped, and the {@link Integer} on the right is the max stack size. The list uses a
     * {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> STONE_DROPS = () -> Arrays.asList(
            Triple.of(0.005f, Items.GOLD_NUGGET, 1),
            Triple.of(0.05f, Items.FLINT, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing drop information when a {@link Blocks#COAL_ORE} is broken.
     * The {@link Float} on the left is the chance of the item dropping (1.00F being 100%), the {@link Item} in the
     * middle is the item to be dropped, and the {@link Integer} on the right is the max stack size. The list uses a
     * {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> COAL_ORE_DROPS = () -> Arrays.asList(
            Triple.of(0.001f, Items.DIAMOND, 1),
            Triple.of(0.001f, Items.EMERALD, 1));

    /**
     * Contains a {@link List} of {@link Triple}s containing drop information when a {@link Blocks#NETHER_QUARTZ_ORE} is
     * broken. The {@link Float} on the left is the chance of the item dropping (1.00F being 100%), the {@link Item} in
     * the middle is the item to be dropped, and the {@link Integer} on the right is the max stack size.
     */
    private static final Supplier<List<Triple<Float, Item, Integer>>> NETHER_QUARTZ_ORE_DROPS = () -> Arrays.asList(
            Triple.of(0.5f, Items.GOLD_NUGGET, 9));

    /**
     * This {@link Map} contains all of the {@link List}s for all of the block drops. This includes the constant lists
     * specified in the class ({@link #GRASS_BLOCK_DROPS}, {@link #DIRT_DROPS}, {@link #STONE_DROPS},
     * {@link #COAL_ORE_DROPS}, {@link #NETHER_QUARTZ_ORE_DROPS}), but also any other lists that might be added with the
     * {{@link #registerDrop(Block, List)}.
     *
     * @see #init()
     * @see #registerDrop(Block, List)
     */
    private static final Map<Block, List<Triple<Float, Item, Integer>>> FORAGE_EVENT_REGISTRY = new HashMap<>();

    /**
     * Registers all of the main ForageCraft drop lists into the {@link #FORAGE_EVENT_REGISTRY}. Can only be run once.
     *
     * @see #mainDropsInitialized
     * @since 2.1.0
     */
    public static void init()
    {
        if (mainDropsInitialized) return;

        LOGGER.info("Initializing ForageCraft foraging drops");
        FORAGE_EVENT_REGISTRY.put(Blocks.GRASS_BLOCK, GRASS_BLOCK_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.DIRT, DIRT_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.STONE, STONE_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.COAL_ORE, COAL_ORE_DROPS.get());
        FORAGE_EVENT_REGISTRY.put(Blocks.NETHER_QUARTZ_ORE, NETHER_QUARTZ_ORE_DROPS.get());
        mainDropsInitialized = true;
    }

    /**
     * This method is used to register additional foraging drops of your own!
     * <p>
     * Here's an example if how you would register a new drop for {@link Blocks#STONE} so that there is a 15% chance for
     * it to drop a {@link Items#GOLD_NUGGET} with a max stack of 2. Please make sure that this is run in a method that
     * is called after block and item registries have been run (i.e.
     * {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}.
     * <pre>
     *     ForagingEventHandler.registerDrop(Blocks.STONE, ImmutableList.of(Triple.of(0.15F, Items.GOLD_NUGGET, 2)));
     * </pre>
     *
     * @param block The block to register foraging drops for.
     * @param drop  The list of tripless which contains the drop information. The left of the triple must be a
     *              {@link Float} with the chance of the drop (with 1.00F being 100%), the middle of the triple must be
     *              the {@link Item} that could be dropped, and the right must be an {@link Integer} that indicates the
     *              max stack of the dropped item.
     * @see #FORAGE_EVENT_REGISTRY
     * @since 2.1.0
     */
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
     * @param event The block break event that carries the information about the broken block.
     * @see net.minecraftforge.event.world.BlockEvent.BreakEvent
     */
    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event)
    {
        PlayerEntity player = event.getPlayer();
        if (((World) event.getWorld()).isClientSide || player.isCreative() || player.isSpectator()) return;

        Block blockBroken = event.getState().getBlock();

        if (FORAGE_EVENT_REGISTRY.containsKey(blockBroken) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) == 0)
            forageDrop(FORAGE_EVENT_REGISTRY.get(blockBroken), event);
    }

    /**
     * This method runs through what happens when a block is broken and the block has a foraging drop that has been
     * registered in the {@link #FORAGE_EVENT_REGISTRY}. This is also where the {@link ForageTriggers#FORAGING_TRIGGER}
     * is triggered, but it is only triggered if the block actually succeeds in dropping a forage drop.
     *
     * @param dropList The list of tripless that contain the drop information.
     * @param event    The block break event that holds crucial information for the foraging drop such as the
     *                 {@link World}, the {@link ServerPlayerEntity}, and the {@link net.minecraft.block.BlockState}
     *                 which contains the {@link Block} that was broken.
     * @see #onBlockBroken(BlockEvent.BreakEvent)
     * @since 2.1.0
     */
    private static void forageDrop(List<Triple<Float, Item, Integer>> dropList, BlockEvent.BreakEvent event)
    {
        World level = ((World) event.getWorld());
        Random random = level.getRandom();
        Block blockBroken = event.getState().getBlock();

        ServerPlayerEntity playerEntity = (ServerPlayerEntity) event.getPlayer();
        if (cooldownMap.containsKey(playerEntity.getUUID())) return;

        Collections.shuffle(dropList, random);

        for (Triple<Float, Item, Integer> drop : dropList)
        {
            float chance = drop.getLeft();
            Item item = drop.getMiddle();
            int maxStack = drop.getRight();

            if (random.nextFloat() < chance)
            {
                LOGGER.trace(String.format("%s DROPPING %s with chance %f", blockBroken, item, chance));
                Block.popResource(level, event.getPos(), new ItemStack(item, random.nextInt(maxStack) + 1));

                IChunk chunk = level.getChunk(event.getPos());
                if (chunk instanceof Chunk)
                {
                    ((Chunk) chunk).getCapability(ForageCapabilities.CHUNK).ifPresent(entry ->
                    {
                        entry.forage();
                        ((Chunk) chunk).markUnsaved();
                    });
                }

                ForageTriggers.FORAGING_TRIGGER.trigger(playerEntity, blockBroken, item);
                cooldownMap.put(playerEntity.getUUID(), MathUtil.secondsToWorldTicks(10));
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.world.isClientSide) return;
        cooldownMap.replaceAll((k, v) -> v - 1);
        cooldownMap.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }
}

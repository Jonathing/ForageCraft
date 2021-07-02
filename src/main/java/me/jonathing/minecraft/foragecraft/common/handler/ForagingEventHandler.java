package me.jonathing.minecraft.foragecraft.common.handler;

import me.jonathing.minecraft.foragecraft.common.capability.base.IForageChunk;
import me.jonathing.minecraft.foragecraft.common.config.ForageCraftConfig;
import me.jonathing.minecraft.foragecraft.common.event.BlockForagedEvent;
import me.jonathing.minecraft.foragecraft.common.registry.ForageCapabilities;
import me.jonathing.minecraft.foragecraft.common.registry.ForageTriggers;
import me.jonathing.minecraft.foragecraft.common.util.MathUtil;
import me.jonathing.minecraft.foragecraft.data.objects.ForagingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.*;

import static me.jonathing.minecraft.foragecraft.ForageCraft.LOGGER;

/**
 * This class handles the foraging loot for vanilla blocks in the game. If I ever need to make loot for any of my own
 * blocks, I will do so with data generation.
 *
 * @author Jonathing
 * @see #onBlockBroken(BlockEvent.BreakEvent)
 * @see BlockForagedEvent
 * @since 2.0.0
 */
public class ForagingEventHandler
{
    private static final Marker MARKER = MarkerManager.getMarker(ForagingEventHandler.class.getSimpleName());

    private static boolean errorDisplayed = false;

    /**
     * This {@link Map} contains {@link UUID}s of players who are on cooldown and thus are not able to forage.
     */
    private static final Map<UUID, Integer> PLAYERS_ON_COOLDOWN = new HashMap<>();

    /**
     * This {@link Map} contains all of the {@link List}s for all of the block drops. This includes drops provided in
     * ForageCraft's data, but also any other drops that might be added with datapacks or the
     * {{@link #registerDrops(Block, List)} or {@link #registerDrop(Block, IItemProvider, int, float)} methods.
     *
     * @see #registerDrops(Block, List)
     * @see #registerDrop(Block, IItemProvider, int, float)
     */
    private static final Map<Block, List<Triple<IItemProvider, Integer, Float>>> FORAGE_DROP_REGISTRY = new HashMap<>();

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
     * @see #FORAGE_DROP_REGISTRY
     * @since 2.1.0
     */
    public static void registerDrops(Block block, List<Triple<IItemProvider, Integer, Float>> drop)
    {
        if (!FORAGE_DROP_REGISTRY.containsKey(block))
            FORAGE_DROP_REGISTRY.put(block, drop);
        else
            FORAGE_DROP_REGISTRY.get(block).addAll(drop);
    }

    public static void registerDrop(Block block, IItemProvider item, int maxDrops, float chance)
    {
        registerDrops(block, new ArrayList<>(Collections.singletonList(Triple.of(item, maxDrops, chance))));
    }

    public static void reloadDrops(Map<ResourceLocation, ForagingRecipe> data)
    {
        FORAGE_DROP_REGISTRY.entrySet().removeIf(entry -> true);
        data.forEach((k, v) ->
        {
            LOGGER.debug(MARKER, String.format("Loading foraging drop %s with data {%s, %s, %d, %f}",
                    k, v.getInput(), v.getResult(), v.getMaxDrops(), v.getChance()));
            registerDrop(v.getInput(), v.getResult(), v.getMaxDrops(), v.getChance());
        });
    }

    /**
     * This event method runs through the {@link #FORAGE_DROP_REGISTRY} and then calls the
     * {@link #forageDrop(List, BlockEvent.BreakEvent)} method if there is data for the block that was broken.
     *
     * @param event The block break event that carries the information about the broken block.
     * @see BlockEvent.BreakEvent
     */
    static void onBlockBroken(BlockEvent.BreakEvent event)
    {
        PlayerEntity player = event.getPlayer();
        if (((World) event.getWorld()).isClientSide || player.isCreative() || player.isSpectator()) return;

        Block blockBroken = event.getState().getBlock();

        if (FORAGE_DROP_REGISTRY.containsKey(blockBroken) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) == 0)
            forageDrop(FORAGE_DROP_REGISTRY.get(blockBroken), event);
    }

    /**
     * This method runs through what happens when a block is broken and the block has a foraging drop that has been
     * registered in the {@link #FORAGE_DROP_REGISTRY}. This is also where the {@link ForageTriggers#FORAGING_TRIGGER}
     * is triggered, but it is only triggered if the block actually succeeds in dropping a forage drop.
     *
     * @param dropList The list of triples that contain the drop information.
     * @param event    The block break event that holds crucial information for the foraging drop such as the
     *                 {@link World}, the {@link ServerPlayerEntity}, and the {@link net.minecraft.block.BlockState}
     *                 which contains the {@link Block} that was broken.
     * @see #onBlockBroken(BlockEvent.BreakEvent)
     * @since 2.1.0
     */
    private static void forageDrop(List<Triple<IItemProvider, Integer, Float>> dropList, BlockEvent.BreakEvent event)
    {
        World level = ((World) event.getWorld());
        Random random = level.getRandom();

        ServerPlayerEntity playerEntity = (ServerPlayerEntity) event.getPlayer();
        if (PLAYERS_ON_COOLDOWN.containsKey(playerEntity.getUUID())) return;

        Chunk chunk = level.getChunkAt(event.getPos());
        LazyOptional<IForageChunk> forageChunk = chunk.getCapability(ForageCapabilities.CHUNK);
        forageChunk.ifPresent(c ->
        {
            PLAYERS_ON_COOLDOWN.put(playerEntity.getUUID(), MathUtil.secondsToWorldTicks(ForageCraftConfig.SERVER.getUnsuccessfulForagingCooldown()));
            if (c.getTimesForaged() >= ForageCraftConfig.SERVER.getMaxForagesPerChunk()
                    && ForageCraftConfig.SERVER.getMaxForagesPerChunk() >= 0) return;

            Collections.shuffle(dropList, random);
            for (Triple<IItemProvider, Integer, Float> drop : dropList)
            {
                IItemProvider item = drop.getLeft();
                int maxStack = drop.getMiddle();
                float chance = drop.getRight();

                if (random.nextFloat() < chance)
                {
                    BlockForagedEvent forageEvent = new BlockForagedEvent(event, item, random.nextInt(maxStack) + 1);
                    if (!postEvent(forageEvent))
                    {
                        c.forage();
                        chunk.markUnsaved();
                    }

                    break;
                }
            }
        });

        if (!forageChunk.isPresent() && !errorDisplayed)
        {
            errorDisplayed = true;
            LOGGER.fatal(MARKER, "Chunk capability not present! ForageCraft will not function!");
            LOGGER.fatal(MARKER, "I have no idea how in God's name this is happening. Please report the issue!");
            LOGGER.fatal(MARKER, "https://github.com/Jonathing/ForageCraft/issues");
        }
    }

    private static boolean postEvent(BlockForagedEvent event)
    {
        World level = (World) event.getWorld();
        BlockPos pos = event.getPos();

        Block block = event.getState().getBlock();
        ServerPlayerEntity player = event.getPlayer();

        if (!MinecraftForge.EVENT_BUS.post(event))
        {
            ItemStack stack = event.getStackToDrop();
            IItemProvider item = event.getItem();

            // Drop the foraged item.
            Block.popResource(level, pos, stack);

            // Put the player on cooldown.
            ForageTriggers.FORAGING_TRIGGER.trigger(player, block, item);
            PLAYERS_ON_COOLDOWN.put(player.getUUID(), MathUtil.secondsToWorldTicks(ForageCraftConfig.SERVER.getSuccessfulForagingCooldown()));
        }

        return event.isCanceled();
    }

    static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.world.isClientSide) return;
        PLAYERS_ON_COOLDOWN.replaceAll((k, v) -> v - 1);
        PLAYERS_ON_COOLDOWN.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }
}

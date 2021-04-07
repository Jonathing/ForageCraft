package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * This is the main registry class for ForageCraft. The bulk of registration happens here.
 *
 * @author Jonathing
 * @see ForageBlocks
 * @see ForageItems
 * @since 2.0.0
 */
public class ForageRegistry
{
    public static void addEventListeners(IEventBus mod, IEventBus forge)
    {
        mod.addGenericListener(Block.class, ForageRegistry::registerBlocks);
        forge.addListener(ForageBlocks.BurnTimes::onFurnaceFuelBurnTime);
        mod.addGenericListener(Item.class, ForageRegistry::registerItems);
        forge.addGenericListener(Chunk.class, ForageCapabilities::onAttachChunkCapability);
        forge.addListener(ForageFeatures::biomeLoadingEvent);
    }

    /**
     * This event method calls the {@link ForageBlocks#init(RegistryEvent.Register)} method and prepares to register all
     * of the {@link Block}s of ForageCraft into the game.
     *
     * @param event The register event for blocks to register the blocks into.
     * @see RegistryEvent.Register
     */
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        ForageBlocks.init(event);
    }

    /**
     * This event method calls the {@link ForageItems#init(RegistryEvent.Register)} method and prepares to register all
     * of the {@link Item}s of ForageCraft into the game.
     *
     * @param event The register event for items to register the items and block items into.
     * @see RegistryEvent.Register
     */
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        ForageItems.init(event);
    }

    public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String key, T object)
    {
        register(registry, ForageCraft.locate(key), object);
    }

    public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, ResourceLocation key, T object)
    {
        object.setRegistryName(key);
        registry.register(object);
    }
}

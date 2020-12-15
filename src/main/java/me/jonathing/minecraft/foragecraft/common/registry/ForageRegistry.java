package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    /**
     * This event method calls the {@link ForageBlocks#init(RegistryEvent.Register)} method and prepares to register all
     * of the {@link Block}s of ForageCraft into the game.
     *
     * @see RegistryEvent.Register
     */
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        ForageBlocks.init(event);
    }

    /**
     * This event method calls the {@link ForageItems#init(RegistryEvent.Register)} method and prepares to register all
     * of the {@link Item}s of ForageCraft into the game.
     *
     * @see RegistryEvent.Register
     */
    @SubscribeEvent
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

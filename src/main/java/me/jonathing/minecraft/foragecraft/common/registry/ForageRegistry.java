package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ForageRegistry
{
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        ForageBlocks.init(event);
    }

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

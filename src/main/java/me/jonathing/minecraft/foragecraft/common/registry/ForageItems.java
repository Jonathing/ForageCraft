package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.items.ForageItemGroups;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForageItems
{
    public static Item straw;
    public static Item stick_bundle;
    public static Item spaghetti;

    private static IForgeRegistry<Item> iItemRegistry;
    public static List<Item> coloredTools = new ArrayList<>();

    public static void init(Register<Item> event)
    {
        ForageItems.iItemRegistry = event.getRegistry();

        straw = register("straw", new Item(new Item.Properties().group(ForageItemGroups.FORAGECRAFT)));

        stick_bundle = register("stick_bundle", new Item(new Item.Properties().group(ForageItemGroups.FORAGECRAFT)));

        spaghetti = register("spaghetti", new Item(new Item.Properties().group(ForageItemGroups.FORAGECRAFT).food(new Food.Builder().hunger(11).saturation(0.375F).build())));

        registerBlockItems();
    }

    private static void registerBlockItems()
    {
        for (Map.Entry<Block, ItemGroup> entry : ForageBlocks.blockItemMap.entrySet())
        {
            Block block = entry.getKey();
            ForageRegistry.register(iItemRegistry, block.getRegistryName(), new BlockItem(block, new Item.Properties().group(entry.getValue())));
        }
        ForageBlocks.blockItemMap.clear();

        for (Map.Entry<Block, Item.Properties> entry : ForageBlocks.blockItemPropertiesMap.entrySet())
        {
            Block block = entry.getKey();
            ForageRegistry.register(iItemRegistry, block.getRegistryName(), new BlockItem(block, entry.getValue()));
        }
        ForageBlocks.blockItemPropertiesMap.clear();
    }

    private static Item registerTool(String name, Item item)
    {
        coloredTools.add(item);
        return register(name, item);
    }

    private static Item register(String name, Item item)
    {
        ForageRegistry.register(iItemRegistry, name, item);
        return item;
    }
}

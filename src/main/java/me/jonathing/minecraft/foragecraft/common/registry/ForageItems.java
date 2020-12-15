package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.items.ForageItemGroups;
import me.jonathing.minecraft.foragecraft.common.items.LeekItem;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Map;

/**
 * This class holds all of the items in ForageCraft.
 *
 * @author Jonathing
 * @see #init(Register)
 * @since 2.0.0
 */
public class ForageItems
{
    public static Item straw;
    public static Item stick_bundle;
    public static Item spaghetti, leek;
    public static Item leek_seeds;

    private static IForgeRegistry<Item> iItemRegistry;

    public static void init(Register<Item> event)
    {
        ForageItems.iItemRegistry = event.getRegistry();

        straw = register("straw",
                new Item(new Item.Properties()
                        .group(ForageInfo.IDE ? ForageItemGroups.FORAGECRAFT : ItemGroup.MISC)));

        stick_bundle = register("stick_bundle",
                new Item(new Item.Properties()
                        .group(ForageInfo.IDE ? ForageItemGroups.FORAGECRAFT : ItemGroup.MISC)));

        spaghetti = register("spaghetti",
                new Item(new Item.Properties()
                        .group(ForageInfo.IDE ? ForageItemGroups.FORAGECRAFT : ItemGroup.FOOD)
                        .food(new Food.Builder().hunger(11).saturation(0.375F).build())));
        leek = register("leek",
                new LeekItem(new Item.Properties()
                        .group(ForageInfo.IDE ? ForageItemGroups.FORAGECRAFT : ItemGroup.FOOD)
                        .food(new Food.Builder().hunger(2).saturation(0.1F).build())));

        leek_seeds = register("leek_seeds",
                new BlockNamedItem(ForageBlocks.leek_crop, new Item.Properties()
                        .group(ForageInfo.IDE ? ForageItemGroups.FORAGECRAFT : ItemGroup.MISC)));

        registerBlockItems();
    }

    /**
     * This method registers items for all of the registered blocks in ForageCraft.
     *
     * @see ForageBlocks
     */
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

    /**
     * Queues an {@link Item} for item registry and then returns the item afterwards.
     *
     * @param name The name of the item to register.
     * @param item The item instance to register.
     * @return The registered item instance.
     */
    private static Item register(String name, Item item)
    {
        ForageRegistry.register(iItemRegistry, name, item);
        return item;
    }
}

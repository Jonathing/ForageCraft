package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.item.GatheringKnifeItem;
import me.jonathing.minecraft.foragecraft.common.item.LeekItem;
import me.jonathing.minecraft.foragecraft.common.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Map;

import static me.jonathing.minecraft.foragecraft.common.registry.ForageItemGroups.getItemGroup;

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
    public static Item gathering_knife;

    private static IForgeRegistry<Item> iItemRegistry;

    /**
     * This method registers all of the items into the item registry.
     *
     * @param event The {@link Register} event for item registration.
     */
    public static void init(Register<Item> event)
    {
        ForageItems.iItemRegistry = event.getRegistry();

        straw = register("straw",
                ItemGroup.TAB_MISC);

        stick_bundle = register("stick_bundle",
                ItemGroup.TAB_MISC,
                MathUtil.secondsToTicks(5 * 9));

        spaghetti = register("spaghetti",
                new SoupItem(new Item.Properties()
                        .tab(getItemGroup(ItemGroup.TAB_FOOD))
                        .stacksTo(1)
                        .food(new Food.Builder().nutrition(11).saturationMod(0.375F).build())));
        leek = register("leek",
                new LeekItem(new Item.Properties()
                        .tab(getItemGroup(ItemGroup.TAB_FOOD))
                        .food(new Food.Builder().nutrition(2).saturationMod(0.1F).build())));

        leek_seeds = register("leek_seeds",
                new BlockNamedItem(ForageBlocks.leek_crop, new Item.Properties()
                        .tab(getItemGroup(ItemGroup.TAB_MISC))));

        gathering_knife = register("gathering_knife",
                new GatheringKnifeItem(new Item.Properties()
                        .tab(getItemGroup(ItemGroup.TAB_TOOLS))
                        .stacksTo(1)
                        .durability(60)));

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
            ForageRegistry.register(iItemRegistry, block.getRegistryName(), new BlockItem(block, new Item.Properties().tab(getItemGroup(entry.getValue()))));
        }
        ForageBlocks.blockItemMap.clear();

        for (Map.Entry<Block, Item.Properties> entry : ForageBlocks.blockItemPropertiesMap.entrySet())
        {
            Block block = entry.getKey();
            ForageRegistry.register(iItemRegistry, block.getRegistryName(), new BlockItem(block, entry.getValue()));
        }
        ForageBlocks.blockItemPropertiesMap.clear();

        for (Map.Entry<Block, Lazy<BlockItem>> entry : ForageBlocks.customBlockItemMap.entrySet())
        {
            Block block = entry.getKey();
            ForageRegistry.register(iItemRegistry, block.getRegistryName(), entry.getValue().get());
        }
        ForageBlocks.customBlockItemMap.clear();
    }

    /**
     * Queues an {@link Item} for item registry and then returns the item afterwards.
     *
     * @param name The name of the item to register.
     * @param item The item instance to register.
     * @param <I>  The item type.
     * @return The registered item instance.
     */
    private static <I extends Item> I register(String name, I item)
    {
        ForageRegistry.register(iItemRegistry, name, item);
        return item;
    }

    private static Item register(String name, Item.Properties properties)
    {
        return register(name, new Item(properties));
    }

    private static Item register(String name, ItemGroup defaultItemGroup)
    {
        return register(name, new Item.Properties().tab(getItemGroup(defaultItemGroup)));
    }

    private static Item register(String name, Item.Properties properties, int burnTime)
    {
        return register(name, new Item(properties)
        {
            @Override
            public int getBurnTime(ItemStack itemStack)
            {
                return burnTime;
            }
        });
    }

    private static Item register(String name, ItemGroup defaultItemGroup, int burnTime)
    {
        return register(name, new Item.Properties().tab(getItemGroup(defaultItemGroup)), burnTime);
    }
}

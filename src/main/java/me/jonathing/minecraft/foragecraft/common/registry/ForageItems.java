package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.item.GatheringKnifeItem;
import me.jonathing.minecraft.foragecraft.common.item.LeekItem;
import me.jonathing.minecraft.foragecraft.common.item.util.ForageItemTier;
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
    public static Item gathering_knife;
    public static Item primitive_pickaxe, primitive_axe, primitive_combat_knife;

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
                new Item(new Item.Properties()
                        .tab(getItemGroup(ItemGroup.TAB_MISC))));

        stick_bundle = register("stick_bundle",
                new Item(new Item.Properties()
                        .tab(getItemGroup(ItemGroup.TAB_MISC))));

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

        primitive_pickaxe = register("primitive_pickaxe",
                new PickaxeItem(ForageItemTier.PRIMITIVE, 1, -2.4F, new Item.Properties()
                        .tab(getItemGroup(ItemGroup.TAB_TOOLS))));
        primitive_axe = register("primitive_axe",
                new AxeItem(ForageItemTier.PRIMITIVE, 4.5F, -3.0F, (new Item.Properties())
                        .tab(getItemGroup(ItemGroup.TAB_TOOLS))));
        primitive_combat_knife = register("primitive_combat_knife",
                new SwordItem(ForageItemTier.PRIMITIVE, 3, -2.2F, (new Item.Properties())
                        .tab(getItemGroup(ItemGroup.TAB_COMBAT))));

        registerBlockItems();
    }

    private static ItemGroup getItemGroup(ItemGroup itemGroup)
    {
        return ForageInfo.IDE && !ForageInfo.DATAGEN ? ForageItemGroups.FORAGECRAFT : itemGroup;
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
            ForageRegistry.register(iItemRegistry, block.getRegistryName(), new BlockItem(block, new Item.Properties().tab(entry.getValue())));
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

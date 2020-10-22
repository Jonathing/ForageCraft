package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.items.ForageItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ForageBlocks
{
    public static Block straw_bale, fascine;

    public static Map<Block, ItemGroup> blockItemMap = new LinkedHashMap<>();
    public static Map<Block, Item.Properties> blockItemPropertiesMap = new LinkedHashMap<>();

    private static IForgeRegistry<Block> iBlockRegistry;

    public static void init(Register<Block> event)
    {
        ForageBlocks.iBlockRegistry = event.getRegistry();

        straw_bale = registerForage("straw_bale", new HayBlock(Block.Properties.from(Blocks.HAY_BLOCK)));
        fascine = registerForage("fascine", new HayBlock(Block.Properties.from(Blocks.HAY_BLOCK)));

//        // Crops
//        winter_leaves = registerBlock("winter_leaves", new SkyCropBlock(() -> SkiesItems.winter_leaf_seeds, 10));
//        fiery_beans = registerBlock("fiery_beans", new SkyCropBlock(() -> SkiesItems.fiery_bean_seeds, 14));
//        solnuts = registerBlock("solnuts", new SkyCropBlock(() -> SkiesItems.solnut, 13));
//        cryo_roots = registerBlock("cryo_roots", new SkyCropBlock(() -> SkiesItems.cryo_root, 8));
//        scalefruits = registerBlock("scalefruits", new SkyDoubleCropBlock(() -> SkiesItems.scalefruit_seeds, 16));
//        pine_fruits = registerBlock("pine_fruits", new SkyDoubleCropBlock(() -> SkiesItems.pine_fruit_seeds, 16));
    }

    /**
     * Register a block and queue it for item registration.
     *
     * @param key
     * @param block
     * @param itemGroup
     * @return
     */
    public static <B extends Block> B register(String key, B block, ItemGroup itemGroup)
    {
        blockItemMap.put(block, itemGroup);
        return registerBlock(key, block);
    }

    public static <B extends Block> B registerItemProperties(String key, B block, Item.Properties itemProperties)
    {
        blockItemPropertiesMap.put(block, itemProperties);
        return registerBlock(key, block);
    }

    public static <B extends Block> B registerBlock(String key, B block)
    {
        ForageRegistry.register(iBlockRegistry, key, block);
        return block;
    }

    public static <B extends Block> B registerForage(String key, B block)
    {
        return register(key, block, ForageItemGroups.FORAGECRAFT);
    }
}

package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.blocks.*;
import me.jonathing.minecraft.foragecraft.common.items.ForageItemGroups;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class holds all of the blocks in ForageCraft.
 *
 * @author Jonathing
 * @see #init(Register)
 * @since 2.0.0
 */
public class ForageBlocks
{
    public static Block rock, flat_rock, stick;
    public static Block straw_bale, fascine;
    public static Block paving_stones;
    public static Block leek_crop;

    public static Map<Block, ItemGroup> blockItemMap = new LinkedHashMap<>();
    public static Map<Block, Item.Properties> blockItemPropertiesMap = new LinkedHashMap<>();

    private static IForgeRegistry<Block> iBlockRegistry;

    public static void init(Register<Block> event)
    {
        ForageBlocks.iBlockRegistry = event.getRegistry();

        rock = register("rock",
                new RockBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).doesNotBlockMovement().nonOpaque().zeroHardnessAndResistance()),
                ItemGroup.MISC,
                true);
        flat_rock = register("flat_rock",
                new FlatRockBlock(Block.Properties.from(rock)),
                ItemGroup.MISC,
                true);
        stick = register("stick",
                new StickBlock(Block.Properties.from(Blocks.OAK_PLANKS).doesNotBlockMovement().nonOpaque().zeroHardnessAndResistance()),
                null,
                false);

        straw_bale = register("straw_bale",
                new HayBlock(Block.Properties.from(Blocks.HAY_BLOCK)),
                ItemGroup.BUILDING_BLOCKS,
                true);
        fascine = register("fascine",
                new HayBlock(Block.Properties.from(Blocks.HAY_BLOCK)),
                ItemGroup.BUILDING_BLOCKS,
                true);

        paving_stones = register("paving_stones",
                new PavingStoneBlock(Block.Properties.from(Blocks.STONE)),
                ItemGroup.BUILDING_BLOCKS,
                true);

        leek_crop = register("leek_crop",
                new LeekCropBlock(Block.Properties.from(Blocks.WHEAT)),
                null,
                false);
    }

    /**
     * Register a block and queue it for item registration.
     *
     * @param key
     * @param block
     * @param defaultItemGroup
     * @return
     */
    public static <B extends Block> B register(String key, B block, ItemGroup defaultItemGroup, boolean registerItem)
    {
        if (registerItem)
            blockItemMap.put(block, ForageInfo.IDE ? ForageItemGroups.FORAGECRAFT : defaultItemGroup);

        ForageRegistry.register(iBlockRegistry, key, block);
        return block;
    }

    public static <B extends Block> B register(String key, B block, Item.Properties itemProperties, ItemGroup defaultItemGroup, boolean registerItem)
    {
        blockItemPropertiesMap.put(block, itemProperties);
        return register(key, block, defaultItemGroup, registerItem);
    }
}

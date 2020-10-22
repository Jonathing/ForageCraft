package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.blocks.*;
import me.jonathing.minecraft.foragecraft.common.items.ForageItemGroups;
import net.minecraft.block.AbstractBlock;
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

        rock = registerForage("rock", new RockBlock(Block.Properties.from(Blocks.STONE).doesNotBlockMovement().nonOpaque().zeroHardnessAndResistance()));
        flat_rock = registerForage("flat_rock", new FlatRockBlock(Block.Properties.from(rock)));
        stick = registerForage("stick", new StickBlock(Block.Properties.from(Blocks.OAK_PLANKS).doesNotBlockMovement().nonOpaque().zeroHardnessAndResistance()));

        straw_bale = registerForage("straw_bale", new HayBlock(Block.Properties.from(Blocks.HAY_BLOCK)));
        fascine = registerForage("fascine", new HayBlock(Block.Properties.from(Blocks.HAY_BLOCK)));

        paving_stones = registerForage("paving_stones", new PavingStoneBlock(Block.Properties.from(Blocks.STONE)));

        leek_crop = registerBlock("leek_crop", new LeekCropBlock(Block.Properties.from(Blocks.WHEAT)));
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

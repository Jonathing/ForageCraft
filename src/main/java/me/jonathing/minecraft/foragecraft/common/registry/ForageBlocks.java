package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.block.LeekCropBlock;
import me.jonathing.minecraft.foragecraft.common.block.RockBlock;
import me.jonathing.minecraft.foragecraft.common.block.StickBlock;
import me.jonathing.minecraft.foragecraft.common.block.template.DecorativeBlock;
import me.jonathing.minecraft.foragecraft.common.block.template.ForageHayBlock;
import me.jonathing.minecraft.foragecraft.common.block.template.ForageSpeedBlock;
import me.jonathing.minecraft.foragecraft.common.util.MathUtil;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

import static me.jonathing.minecraft.foragecraft.common.registry.ForageItemGroups.getItemGroup;

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
    public static Block blackstone_rock, blackstone_flat_rock;
    public static Block straw_bale, fascine;
    public static Block paving_stones;
    public static Block leek_crop;

    public static Map<Block, ItemGroup> blockItemMap = new LinkedHashMap<>();
    public static Map<Block, Item.Properties> blockItemPropertiesMap = new LinkedHashMap<>();
    public static Map<Block, Lazy<BlockItem>> customBlockItemMap = new LinkedHashMap<>();

    private static IForgeRegistry<Block> iBlockRegistry;

    /**
     * This method registers all of the blocks into the block registry.
     *
     * @param event The register event for block registration.
     */
    public static void init(Register<Block> event)
    {
        ForageBlocks.iBlockRegistry = event.getRegistry();

        rock = register("rock",
                new RockBlock(DecorativeBlock.ROCK_SHAPE, () -> ForageBlocks.rock.asItem()),
                ItemGroup.TAB_MISC,
                true);
        flat_rock = register("flat_rock",
                new RockBlock(DecorativeBlock.FLAT_ROCK_SHAPE, () -> ForageBlocks.flat_rock.asItem()),
                ItemGroup.TAB_MISC,
                true);
        stick = register("stick",
                new StickBlock(),
                null,
                false);

        blackstone_rock = register("blackstone_rock",
                new RockBlock(DecorativeBlock.ROCK_SHAPE, () -> ForageBlocks.blackstone_rock.asItem()),
                ItemGroup.TAB_MISC,
                true);
        blackstone_flat_rock = register("blackstone_flat_rock",
                new RockBlock(DecorativeBlock.FLAT_ROCK_SHAPE, () -> ForageBlocks.blackstone_flat_rock.asItem()),
                ItemGroup.TAB_MISC,
                true);

        straw_bale = register("straw_bale",
                new ForageHayBlock(0.3F, AbstractBlock.Properties.copy(Blocks.HAY_BLOCK)),
                ItemGroup.TAB_BUILDING_BLOCKS,
                true);
        fascine = register("fascine",
                new ForageHayBlock(0.5F, AbstractBlock.Properties.copy(Blocks.HAY_BLOCK)),
                ItemGroup.TAB_BUILDING_BLOCKS,
                MathUtil.secondsToTicks(5 * 9 * 9));

        paving_stones = register("paving_stones",
                new ForageSpeedBlock(1.5F, AbstractBlock.Properties.copy(Blocks.STONE)),
                ItemGroup.TAB_BUILDING_BLOCKS,
                true);

        leek_crop = register("leek_crop",
                new LeekCropBlock(AbstractBlock.Properties.copy(Blocks.WHEAT)),
                null,
                false);
    }

    /**
     * This method registers a {@link Block} and then queues it for block item registration.
     *
     * @param key              The key to be used for the block's registration name.
     * @param block            The block to be registered (i.e. {@link #rock}).
     * @param defaultItemGroup The default item group that the block should be added to. If in a development environment
     *                         ({@link ForageInfo#IDE}), all of the ForageCraft blocks and items will be put into the
     *                         {@link ForageItemGroups#FORAGECRAFT} category.
     * @param registerItem     If false, a block item will not be registered for the given block.
     * @param <B>              The generic type that extends {@link Block} for registration.
     * @return The new registered block that the assigned variable now holds.
     * @see #init(Register)
     */
    public static <B extends Block> B register(String key, B block, ItemGroup defaultItemGroup, boolean registerItem)
    {
        if (registerItem)
            blockItemMap.put(block, defaultItemGroup);

        ForageRegistry.register(iBlockRegistry, key, block);
        return block;
    }

    /**
     * This method registers a {@link Block} with specific {@link Item.Properties} and then queues it for block item
     * registration.
     *
     * @param key              The key to be used for the block's registration name.
     * @param block            The block to be registered (i.e. {@link #rock}).
     * @param itemProperties   The item properties that the specified block should hold.
     * @param defaultItemGroup The default item group that the block should be added to. If in a development environment
     *                         ({@link ForageInfo#IDE}), all of the ForageCraft blocks and items will be put into the
     *                         {@link ForageItemGroups#FORAGECRAFT} category.
     * @param registerItem     If false, a block item will not be registered for the given block.
     * @param <B>              The generic type that extends {@link Block} for registration.
     * @return The new registered {@link Block} that the assigned variable now holds.
     * @see #register(String, Block, ItemGroup, boolean)
     * @see #init(Register)
     */
    public static <B extends Block> B register(String key, B block, Item.Properties itemProperties)
    {
        blockItemPropertiesMap.put(block, itemProperties);

        ForageRegistry.register(iBlockRegistry, key, block);
        return block;
    }

    public static <B extends Block> B register(String key, B block, Lazy<BlockItem> blockItem)
    {
        customBlockItemMap.put(block, blockItem);

        ForageRegistry.register(iBlockRegistry, key, block);
        return block;
    }

    public static <B extends Block> B register(String key, B block, Item.Properties properties, int burnTime)
    {
        return register(key, block, () -> new BlockItem(block, properties)
        {
            @Override
            public int getBurnTime(ItemStack itemStack)
            {
                return burnTime;
            }
        });
    }

    public static <B extends Block> B register(String key, B block, ItemGroup defaultItemGroup, int burnTime)
    {
        return register(key, block, new Item.Properties().tab(getItemGroup(defaultItemGroup)), burnTime);
    }
}

package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.block.RockBlock;
import me.jonathing.minecraft.foragecraft.common.block.SpeedBlock;
import me.jonathing.minecraft.foragecraft.common.block.StickBlock;
import me.jonathing.minecraft.foragecraft.common.util.MathUtil;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
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

        rock = block("rock",
                new RockBlock(RockBlock.ROCK_SHAPE.get(), () -> ForageBlocks.rock.asItem()),
                ItemGroup.TAB_MISC,
                true);
        flat_rock = block("flat_rock",
                new RockBlock(RockBlock.FLAT_ROCK_SHAPE.get(), () -> ForageBlocks.flat_rock.asItem()),
                ItemGroup.TAB_MISC,
                true);
        stick = block("stick",
                new StickBlock(),
                null,
                false);

        blackstone_rock = block("blackstone_rock",
                new RockBlock(RockBlock.ROCK_SHAPE.get(), () -> ForageBlocks.blackstone_rock.asItem()),
                ItemGroup.TAB_MISC,
                true);
        blackstone_flat_rock = block("blackstone_flat_rock",
                new RockBlock(RockBlock.FLAT_ROCK_SHAPE.get(), () -> ForageBlocks.blackstone_flat_rock.asItem()),
                ItemGroup.TAB_MISC,
                true);

        straw_bale = block("straw_bale",
                hayBlock(0.3F, AbstractBlock.Properties.copy(Blocks.HAY_BLOCK)),
                ItemGroup.TAB_BUILDING_BLOCKS,
                true);
        fascine = blockWithBurnTime("fascine",
                hayBlock(0.5F, AbstractBlock.Properties.copy(Blocks.HAY_BLOCK)),
                ItemGroup.TAB_BUILDING_BLOCKS,
                MathUtil.secondsToTicks(5 * 9 * 9));

        paving_stones = block("paving_stones",
                new SpeedBlock(1.5F, AbstractBlock.Properties.copy(Blocks.STONE)),
                ItemGroup.TAB_BUILDING_BLOCKS,
                true);

        leek_crop = block("leek_crop",
                cropsBlock(() -> ForageItems.leek_seeds, AbstractBlock.Properties.copy(Blocks.WHEAT)),
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
    public static <B extends Block> B block(String key, B block, ItemGroup defaultItemGroup, boolean registerItem)
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
     * @param key            The key to be used for the block's registration name.
     * @param block          The block to be registered (i.e. {@link #rock}).
     * @param itemProperties The item properties that the specified block should hold.
     * @param <B>            The generic type that extends {@link Block} for registration.
     * @return The new registered {@link Block} that the assigned variable now holds.
     * @see #block(String, Block, ItemGroup, boolean)
     * @see #init(Register)
     */
    public static <B extends Block> B block(String key, B block, Item.Properties itemProperties)
    {
        blockItemPropertiesMap.put(block, itemProperties);

        ForageRegistry.register(iBlockRegistry, key, block);
        return block;
    }

    public static <B extends Block> B blockWithCustomBlockItem(String key, B block, Lazy<BlockItem> blockItem)
    {
        customBlockItemMap.put(block, blockItem);

        ForageRegistry.register(iBlockRegistry, key, block);
        return block;
    }

    public static <B extends Block> B blockWithBurnTime(String key, B block, Item.Properties properties, int burnTime)
    {
        return blockWithCustomBlockItem(key, block, () -> new BlockItem(block, properties)
        {
            @Override
            public int getBurnTime(ItemStack itemStack)
            {
                return burnTime;
            }
        });
    }

    public static <B extends Block> B blockWithBurnTime(String key, B block, ItemGroup defaultItemGroup, int burnTime)
    {
        return blockWithBurnTime(key, block, new Item.Properties().tab(getItemGroup(defaultItemGroup)), burnTime);
    }

    private static CropsBlock cropsBlock(Lazy<IItemProvider> seedItem, AbstractBlock.Properties properties)
    {
        return new CropsBlock(properties)
        {
            @Override
            @Nonnull
            protected IItemProvider getBaseSeedId()
            {
                return seedItem.get();
            }
        };
    }

    private static HayBlock hayBlock(float fallDamageMultiplier, AbstractBlock.Properties properties)
    {
        return new HayBlock(properties)
        {
            @Override
            @ParametersAreNonnullByDefault
            public void fallOn(World world, BlockPos pos, Entity entity, float damage)
            {
                entity.causeFallDamage(damage, fallDamageMultiplier);
            }
        };
    }
}

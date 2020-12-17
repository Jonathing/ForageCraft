package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.jonathing.minecraft.foragecraft.common.block.LeekCropBlock;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The provider for all of the loot tables in ForageCraft.
 *
 * @author Jonathing
 * @author Silver_David
 * @see LootTableProvider
 * @since 2.0.0
 */
public class ForageLootProvider extends LootTableProvider
{
    public ForageLootProvider(DataGenerator generator)
    {
        super(generator);
    }

    /**
     * @see LootTableProvider#getTables()
     */
    @Override
    @Nonnull
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return ImmutableList.of(Pair.of(BlockLoot::new, LootParameterSets.BLOCK));
    }

    /**
     * @see LootTableProvider#validate(Map, ValidationTracker)
     */
    @Override
    @ParametersAreNonnullByDefault
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker)
    {
        map.forEach((location, table) ->
        {
            LootTableManager.validate(validationtracker, location, table);
        });
    }

    @Override
    @Nonnull
    public String getName()
    {
        return "ForageCraft Loot Tables";
    }

    /**
     * Interface with basic loot table generators
     *
     * @author Silver_David
     */
    public interface LootPoolUtil
    {
        /**
         * Creates a table from the given loot pools.
         *
         * @param pools
         * @return
         */
        default LootTable.Builder tableOf(List<LootPool.Builder> pools)
        {
            LootTable.Builder table = LootTable.builder();
            pools.forEach(pool -> table.addLootPool(pool));
            return table;
        }

        /**
         * Creates a table from the given loot pool.
         *
         * @param pool
         * @return
         */
        default LootTable.Builder tableOf(LootPool.Builder pool)
        {
            return LootTable.builder().addLootPool(pool);
        }

        /**
         * Creates a loot pool with the given item. Gives an amount between the min and
         * max.
         *
         * @param item
         * @param min
         * @param max
         * @return
         */
        default LootPool.Builder basicPool(IItemProvider item, int min, int max)
        {
            return LootPool.builder().addEntry(basicEntry(item, min, max));
        }

        /**
         * Creates a loot pool with the given item. Will only give one item.
         *
         * @param item
         * @return
         */
        default LootPool.Builder basicPool(IItemProvider item)
        {
            return LootPool.builder().addEntry(basicEntry(item));
        }

        /**
         * Creates a loot pool that will give a random item from the list.
         *
         * @param items
         * @return
         */
        default LootPool.Builder randItemPool(List<IItemProvider> items)
        {
            return poolOf(items.stream().map((i) -> basicEntry(i)).collect(Collectors.toList()));
        }

        /**
         * Creates a loot pool with multiple entries. One of these entries will be
         * picked at random each time the pool rolls.
         *
         * @param lootEntries
         * @return
         */
        default LootPool.Builder poolOf(List<LootEntry.Builder<?>> lootEntries)
        {
            LootPool.Builder pool = LootPool.builder();
            lootEntries.forEach(entry -> pool.addEntry(entry));
            return pool;
        }

        /**
         * Creates a loot entry for the given item. Gives an amount between the min and
         * max.
         *
         * @param item
         * @param min
         * @param max
         * @return
         */
        default ItemLootEntry.Builder<?> basicEntry(IItemProvider item, int min, int max)
        {
            return basicEntry(item).acceptFunction(SetCount.builder(RandomValueRange.of(min, max)));
        }

        /**
         * Creates a loot entry for the given item. Will only give one item.
         *
         * @param item
         * @return
         */
        default ItemLootEntry.Builder<?> basicEntry(IItemProvider item)
        {
            return ItemLootEntry.builder(item);
        }
    }

    private static class BlockLoot extends BlockLootTables implements LootPoolUtil
    {
        private final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
        private final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
        private final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
        private final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();
        private final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        @Override
        protected void addTables()
        {
            blocks().forEach(block ->
            {
                if (block.equals(ForageBlocks.stick))
                {
                    registerDropping(block, Items.STICK);
                }
                else if (block.equals(ForageBlocks.leek_crop))
                {
                    ILootCondition.IBuilder growthCondition = BlockStateProperty.builder(block).properties(StatePropertiesPredicate.Builder.create().exactMatch(LeekCropBlock.AGE, ((LeekCropBlock) block).getMaxAge()));
                    this.registerLootTable(block, (b) -> crop(growthCondition, b, ForageItems.leek, ForageItems.leek_seeds));
                }
                else
                {
                    registerDropSelfLootTable(block);
                }
            });
        }

        @Override
        @Nonnull
        protected Iterable<Block> getKnownBlocks()
        {
            return blocks()::iterator;
        }

        private Stream<Block> blocks()
        {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(b -> b.getRegistryName().getNamespace().equals(ForageInfo.MOD_ID) && !b.getLootTable().equals(LootTables.EMPTY));
        }

        private void droppingSeedTag(Block block, ITag.INamedTag<Item> tag)
        {
            this.registerLootTable(block, droppingWithShears(block, withExplosionDecay(block, (TagLootEntry.func_216176_b(tag).acceptCondition(RandomChance.builder(0.125F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE, 2)))));
        }

        private void silkOrElse(Block withSilk, IItemProvider without)
        {
            this.registerLootTable(withSilk, (b) -> droppingWithSilkTouch(b, without));
        }

        private LootTable.Builder leaves(Block block, IItemProvider sapling, IItemProvider stick)
        {
            return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(sapling)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, DEFAULT_SAPLING_DROP_RATES))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS).addEntry(withExplosionDecay(block, ItemLootEntry.builder(stick).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
        }

        private LootTable.Builder leavesFruit(Block block, IItemProvider sapling, IItemProvider stick, IItemProvider fruit)
        {
            float baseChance = 0.05F;
            float[] fortuneChances = new float[]{1.11111114F, 1.25F, 1.6666668F, 5.0F};
            return leaves(block, sapling, stick).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS).addEntry(withSurvivesExplosion(block, ItemLootEntry.builder(fruit)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, baseChance, baseChance * fortuneChances[0], baseChance * fortuneChances[1], baseChance * fortuneChances[2], baseChance * fortuneChances[3]))));
        }

        private LootTable.Builder crop(ILootCondition.IBuilder growthCondition, Block block, IItemProvider food)
        {
            return crop(growthCondition, block, food, food);
        }

        private LootTable.Builder crop(ILootCondition.IBuilder growthCondition, Block block, IItemProvider food, IItemProvider seed)
        {
            LootPool.Builder seedPool = LootPool.builder().addEntry(ItemLootEntry.builder(seed).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3).acceptCondition(growthCondition)));
            LootPool.Builder foodPool = LootPool.builder().acceptCondition(growthCondition).addEntry(ItemLootEntry.builder(food).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 1)));

            return withExplosionDecay(block, LootTable.builder().addLootPool(seedPool).addLootPool(foodPool));
        }

//        private LootTable.Builder doubleCrop(ILootCondition.IBuilder growthCondition, Block block, IItemProvider food, IItemProvider seed)
//        {
//            ILootCondition.IBuilder topHalf = BlockStateProperty.builder(block).properties(StatePropertiesPredicate.Builder.create().exactMatch(SkyDoubleCropBlock.HALF, DoubleBlockHalf.UPPER));
//            ILootCondition.IBuilder lowerHalf = BlockStateProperty.builder(block).properties(StatePropertiesPredicate.Builder.create().exactMatch(SkyDoubleCropBlock.HALF, DoubleBlockHalf.LOWER));
//            LootPool.Builder seedPool = LootPool.builder().addEntry(ItemLootEntry.builder(seed).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 2).acceptCondition(growthCondition)));
//            LootPool.Builder topPool = LootPool.builder().acceptCondition(growthCondition).acceptCondition(topHalf).addEntry(ItemLootEntry.builder(food).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 2)));
//            LootPool.Builder lowerPool = LootPool.builder().acceptCondition(growthCondition).acceptCondition(lowerHalf).addEntry(ItemLootEntry.builder(food).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 1)));
//
//            return withExplosionDecay(block, LootTable.builder().addLootPool(seedPool).addLootPool(topPool).addLootPool(lowerPool));
//        }
    }
}

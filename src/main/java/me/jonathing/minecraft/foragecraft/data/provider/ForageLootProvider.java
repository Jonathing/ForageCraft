package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
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
         * @param pools The list of loot pool builders.
         * @return The table of the list of builders.
         */
        default LootTable.Builder tableOf(List<LootPool.Builder> pools)
        {
            LootTable.Builder table = LootTable.lootTable();
            pools.forEach(pool -> table.withPool(pool));
            return table;
        }

        /**
         * Creates a table from the given loot pool.
         *
         * @param pool The loot pool builder to get the table of.
         * @return The table of the loot pool builder.
         */
        default LootTable.Builder tableOf(LootPool.Builder pool)
        {
            return LootTable.lootTable().withPool(pool);
        }

        /**
         * Creates a loot pool with the given item. Gives an amount between the min and max.
         *
         * @param item The item to be used in the loot pool.
         * @param min  The minimum amount the loot pool should have.
         * @param max  The maximum amount the loot pool should have.
         * @return The builder with the new loot pool.
         */
        default LootPool.Builder basicPool(IItemProvider item, int min, int max)
        {
            return LootPool.lootPool().add(basicEntry(item, min, max));
        }

        /**
         * Creates a loot pool with the given item. Will only give one item.
         *
         * @param item The item to make the loot pool with.
         * @return The builder containing the new loot pool.
         */
        default LootPool.Builder basicPool(IItemProvider item)
        {
            return LootPool.lootPool().add(basicEntry(item));
        }

        /**
         * Creates a loot pool that will give a random item from the list.
         *
         * @param items The list of items to make the loot pool with.
         * @return The builder containing the new loot pool.
         */
        default LootPool.Builder randItemPool(List<IItemProvider> items)
        {
            return poolOf(items.stream().map((i) -> basicEntry(i)).collect(Collectors.toList()));
        }

        /**
         * Creates a loot pool with multiple entries. One of these entries will be picked at random each time the pool
         * rolls.
         *
         * @param lootEntries The list of loot entries to use in the loot pool.
         * @return The new loot pool with the entries added.
         */
        default LootPool.Builder poolOf(List<LootEntry.Builder<?>> lootEntries)
        {
            LootPool.Builder pool = LootPool.lootPool();
            lootEntries.forEach(entry -> pool.add(entry));
            return pool;
        }

        /**
         * Creates a loot entry for the given item. Gives an amount between the min and max.
         *
         * @param item The item that should be used in the loot pool.
         * @param min  The minimum amount that the loot entry should have.
         * @param max  The maximum amount that the loot entry should have.
         * @return The new loot entry.
         */
        default StandaloneLootEntry.Builder<?> basicEntry(IItemProvider item, int min, int max)
        {
            return basicEntry(item).apply(SetCount.setCount(RandomValueRange.between(min, max)));
        }

        /**
         * Creates a loot entry for the given item. Will only give one item.
         *
         * @param item The item to use for the loot entry.
         * @return The new loot entry.
         */
        default StandaloneLootEntry.Builder<?> basicEntry(IItemProvider item)
        {
            return ItemLootEntry.lootTableItem(item);
        }
    }

    private static class BlockLoot extends BlockLootTables implements LootPoolUtil
    {
        private final ILootCondition.IBuilder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
        private final ILootCondition.IBuilder SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
        private final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.or(SILK_TOUCH);
        private final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.invert();
        private final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        @Override
        protected void addTables()
        {
            blocks().forEach(block ->
            {
                if (block.equals(ForageBlocks.stick))
                {
                    dropOther(block, Items.STICK);
                }
                else if (block.equals(ForageBlocks.leek_crop))
                {
                    ILootCondition.IBuilder growthCondition = BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropsBlock.AGE, ((CropsBlock) block).getMaxAge()));
                    this.add(block, (b) -> crop(growthCondition, b, ForageItems.leek, ForageItems.leek_seeds));
                }
                else
                {
                    dropSelf(block);
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
            this.add(block, createShearsDispatchTable(block, applyExplosionDecay(block, (TagLootEntry.expandTag(tag).when(RandomChance.randomChance(0.125F))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2)))));
        }

        private void silkOrElse(Block withSilk, IItemProvider without)
        {
            this.add(withSilk, (b) -> createSingleItemTableWithSilkTouch(b, without));
        }

        private LootTable.Builder leaves(Block block, IItemProvider sapling, IItemProvider stick)
        {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, ItemLootEntry.lootTableItem(sapling)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, DEFAULT_SAPLING_DROP_RATES))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(applyExplosionDecay(block, ItemLootEntry.lootTableItem(stick).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
        }

        private LootTable.Builder leavesFruit(Block block, IItemProvider sapling, IItemProvider stick, IItemProvider fruit)
        {
            float baseChance = 0.05F;
            float[] fortuneChances = new float[]{1.11111114F, 1.25F, 1.6666668F, 5.0F};
            return leaves(block, sapling, stick).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(applyExplosionCondition(block, ItemLootEntry.lootTableItem(fruit)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, baseChance, baseChance * fortuneChances[0], baseChance * fortuneChances[1], baseChance * fortuneChances[2], baseChance * fortuneChances[3]))));
        }

        private LootTable.Builder crop(ILootCondition.IBuilder growthCondition, Block block, IItemProvider food)
        {
            return crop(growthCondition, block, food, food);
        }

        private LootTable.Builder crop(ILootCondition.IBuilder growthCondition, Block block, IItemProvider food, IItemProvider seed)
        {
            LootPool.Builder seedPool = LootPool.lootPool().add(ItemLootEntry.lootTableItem(seed).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3).when(growthCondition)));
            LootPool.Builder foodPool = LootPool.lootPool().when(growthCondition).add(ItemLootEntry.lootTableItem(food).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1)));

            return applyExplosionDecay(block, LootTable.lootTable().withPool(seedPool).withPool(foodPool));
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

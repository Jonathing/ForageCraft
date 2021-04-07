package me.jonathing.minecraft.foragecraft.data.provider;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.data.objects.ForagingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ForagingRecipeProvider extends ForageDataProvider<ForagingRecipe>
{
    private static final Supplier<List<Triple<Float, Item, Integer>>> GRASS_BLOCK_DROPS = () -> Arrays.asList(
            Triple.of(0.09f, Items.STICK, 1),
            Triple.of(0.01f, Items.CARROT, 1),
            Triple.of(0.01f, Items.POTATO, 1),
            Triple.of(0.01f, Items.POISONOUS_POTATO, 1),
            Triple.of(0.01f, Items.BEETROOT, 1),
            Triple.of(0.005f, Items.BONE, 9),
            Triple.of(0.0025f, Items.SKELETON_SKULL, 1));

    private static final Supplier<List<Triple<Float, Item, Integer>>> DIRT_DROPS = () -> Arrays.asList(
            Triple.of(0.07f, Items.STICK, 1),
            Triple.of(0.04f, Items.FLINT, 1),
            Triple.of(0.01f, Items.POTATO, 1),
            Triple.of(0.01f, Items.POISONOUS_POTATO, 1),
            Triple.of(0.005f, Items.BONE, 9),
            Triple.of(0.005f, Items.SKELETON_SKULL, 1));

    private static final Supplier<List<Triple<Float, Item, Integer>>> STONE_DROPS = () -> Arrays.asList(
            Triple.of(0.005f, Items.GOLD_NUGGET, 1),
            Triple.of(0.05f, Items.FLINT, 1));

    private static final Supplier<List<Triple<Float, Item, Integer>>> COAL_ORE_DROPS = () -> Arrays.asList(
            Triple.of(0.001f, Items.DIAMOND, 1),
            Triple.of(0.001f, Items.EMERALD, 1));

    private static final Supplier<List<Triple<Float, Item, Integer>>> NETHER_QUARTZ_ORE_DROPS = () -> Arrays.asList(
            Triple.of(0.5f, Items.GOLD_NUGGET, 9));

    public ForagingRecipeProvider(DataGenerator generator)
    {
        super(generator, ForagingRecipe.DIRECTORY);
    }

    @Override
    protected Map<ResourceLocation, ForagingRecipe> gatherData()
    {
        Map<ResourceLocation, ForagingRecipe> registry = new HashMap<>();

        GRASS_BLOCK_DROPS.get().forEach(drop -> foraging(registry, Blocks.GRASS_BLOCK, drop.getMiddle(), drop.getRight(), drop.getLeft()));
        DIRT_DROPS.get().forEach(drop -> foraging(registry, Blocks.DIRT, drop.getMiddle(), drop.getRight(), drop.getLeft()));
        STONE_DROPS.get().forEach(drop -> foraging(registry, Blocks.STONE, drop.getMiddle(), drop.getRight(), drop.getLeft()));
        COAL_ORE_DROPS.get().forEach(drop -> foraging(registry, Blocks.COAL_ORE, drop.getMiddle(), drop.getRight(), drop.getLeft()));
        NETHER_QUARTZ_ORE_DROPS.get().forEach(drop -> foraging(registry, Blocks.NETHER_QUARTZ_ORE, drop.getMiddle(), drop.getRight(), drop.getLeft()));

        return registry;
    }

    private static void foraging(Map<ResourceLocation, ForagingRecipe> registry, Block block, IItemProvider item, int maxDrops, float chance)
    {
        String name = String.format("%s_from_%s", item.asItem().getRegistryName().getPath(), block.getRegistryName().getPath());
        registry.put(ForageCraft.locate(name), new ForagingRecipe(block, item, maxDrops, chance));
    }
}

package me.jonathing.minecraft.foragecraft.data.provider;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.data.objects.ForagingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForagingRecipeProvider extends ForageDataProvider<ForagingRecipe>
{
    public ForagingRecipeProvider(DataGenerator generator)
    {
        super(generator, ForagingRecipe.DIRECTORY);
    }

    @Override
    protected Map<ResourceLocation, ForagingRecipe> gatherData()
    {
        Map<ResourceLocation, ForagingRecipe> registry = new HashMap<>();

        // Grass Block
        foraging(registry, Blocks.GRASS_BLOCK, Arrays.asList(
                Triple.of(Items.STICK, 1, 0.09f),
                Triple.of(Items.CARROT, 1, 0.01f),
                Triple.of(Items.POTATO, 1, 0.01f),
                Triple.of(Items.POISONOUS_POTATO, 1, 0.01f),
                Triple.of(Items.BEETROOT, 1, 0.01f),
                Triple.of(Items.BONE, 9, 0.005f),
                Triple.of(Items.SKELETON_SKULL, 1, 0.0025f)));

        // Dirt
        foraging(registry, Blocks.DIRT, Arrays.asList(
                Triple.of(Items.STICK, 1, 0.07f),
                Triple.of(Items.FLINT, 1, 0.04f),
                Triple.of(Items.POTATO, 1, 0.01f),
                Triple.of(Items.POISONOUS_POTATO, 1, 0.01f),
                Triple.of(Items.BONE, 9, 0.005f),
                Triple.of(Items.SKELETON_SKULL, 1, 0.005f)));

        // Stone
        foraging(registry, Blocks.STONE, Arrays.asList(
                Triple.of(Items.GOLD_NUGGET, 1, 0.005f),
                Triple.of(Items.FLINT, 1, 0.05f)));

        // Coal Ore
        foraging(registry, Blocks.COAL_ORE, Arrays.asList(
                Triple.of(Items.DIAMOND, 1, 0.001f),
                Triple.of(Items.EMERALD, 1, 0.001f)));

        // Nether Quartz Ore
        foraging(registry, Blocks.NETHER_QUARTZ_ORE, Arrays.asList(
                Triple.of(Items.GOLD_NUGGET, 9, 0.5f)));

        return registry;
    }

    private static void foraging(Map<ResourceLocation, ForagingRecipe> registry, Block block, List<Triple<IItemProvider, Integer, Float>> drops)
    {
        drops.forEach(drop -> foraging(registry, block, drop.getLeft(), drop.getMiddle(), drop.getRight()));
    }

    private static void foraging(Map<ResourceLocation, ForagingRecipe> registry, Block block, IItemProvider item, int maxDrops, float chance)
    {
        String name = String.format("%s_from_%s", item.asItem().getRegistryName().getPath(), block.getRegistryName().getPath());
        registry.put(ForageCraft.locate(name), new ForagingRecipe(block, item, maxDrops, chance));
    }

    @Nonnull
    @Override
    public String getName()
    {
        return "ForageCraft Foraging Recipes";
    }
}

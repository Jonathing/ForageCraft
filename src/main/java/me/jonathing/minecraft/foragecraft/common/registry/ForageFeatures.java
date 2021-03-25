package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.block.StickBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class holds all of the world generation features for ForageCraft.
 *
 * @author Jonathing
 * @see #init()
 * @since 2.0.0
 */
@Mod.EventBusSubscriber
public class ForageFeatures
{
    /**
     * The default random generation config that defines how the {@link ForageBlocks#rock}s,
     * {@link ForageBlocks#flat_rock}s, and {@link ForageBlocks#stick}s are generated. The feature uses a
     * {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     *
     * @see #RANDOM_EARTH_PATCH
     * @see #init()
     */
    public static final NonNullLazy<BlockClusterFeatureConfig> RANDOM_EARTH_CONFIG =
            NonNullLazy.of(() -> (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .add(ForageBlocks.flat_rock.defaultBlockState(), 1)
                    .add(ForageBlocks.rock.defaultBlockState(), 1)
                    .add(((StickBlock) ForageBlocks.stick).getStateWithRandomDirection(), 2),
                    new SimpleBlockPlacer()))
                    .whitelist(new HashSet<>(Arrays.asList(Blocks.STONE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.PODZOL, Blocks.MYCELIUM)))
                    .tries(1)
                    .build());

    public static final NonNullLazy<BlockClusterFeatureConfig> RANDOM_BLACKSTONE_CONFIG =
            NonNullLazy.of(() -> (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .add(ForageBlocks.blackstone_flat_rock.defaultBlockState(), 1)
                    .add(ForageBlocks.blackstone_rock.defaultBlockState(), 1),
                    new SimpleBlockPlacer()))
                    .xspread(12).zspread(12)
                    .yspread(10)
                    .tries(82)
                    .noProjection()
                    .build());

    /**
     * The {@link ConfiguredFeature} that dictates how the {@link #RANDOM_EARTH_CONFIG} is placed in the world. The
     * configured feature uses a {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     *
     * @see #RANDOM_EARTH_CONFIG
     * @see #init()
     */
    public static final NonNullLazy<ConfiguredFeature<?, ?>> RANDOM_EARTH_PATCH =
            NonNullLazy.of(() -> Feature.RANDOM_PATCH
                    .configured(RANDOM_EARTH_CONFIG.get())
                    .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
                    .decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));

    public static final NonNullLazy<ConfiguredFeature<?, ?>> RANDOM_BLACKSTONE_PATCH =
            NonNullLazy.of(() -> Feature.RANDOM_PATCH.configured(RANDOM_BLACKSTONE_CONFIG.get())
                    .range(126)
                    .chance(1));

    public static ConfiguredFeature<?, ?> randomEarthPatch;
    public static ConfiguredFeature<?, ?> randomBlackstonePatch;

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerConfiguredFeature(String nameIn, ConfiguredFeature<FC, ?> featureIn)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, nameIn, featureIn);
    }

    /**
     * Initializes all of the world generation features for ForageCraft. This is called on
     * {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent} in the
     * {@link me.jonathing.minecraft.foragecraft.ForageCraft#commonSetup(FMLCommonSetupEvent)} method.
     *
     * @see me.jonathing.minecraft.foragecraft.ForageCraft#commonSetup(FMLCommonSetupEvent)
     */
    public static void init()
    {
        randomEarthPatch = registerConfiguredFeature("forage_random_rocks", RANDOM_EARTH_PATCH.get());
        randomBlackstonePatch = registerConfiguredFeature("forage_random_blackstone_rocks", RANDOM_BLACKSTONE_PATCH.get());
    }

    /**
     * This list is populated on the first {@link BiomeLoadingEvent} and is given all of the {@link ResourceLocation}s
     * of every biome that belongs to the {@link BiomeDictionary.Type#OVERWORLD} type. This way, I can prevent
     * decorative blocks from spawning in modded dimensions, which is something I do not want unless I have specific mod
     * compatibility.
     *
     * @since 2.1.2
     */
    private static List<ResourceLocation> overworldBiomes;

    /**
     * This list is populated on the first {@link BiomeLoadingEvent} and is given all of the {@link ResourceLocation}s
     * of every biome that belongs to the {@link BiomeDictionary.Type#NETHER} type.
     *
     * @since Not Yet Implemented
     */
    private static List<ResourceLocation> netherBiomes;

    /**
     * This list is populated on the first {@link BiomeLoadingEvent} and is given all of the {@link ResourceLocation}s
     * of every biome that belongs to the {@link BiomeDictionary.Type#END} type.
     *
     * @since Not Yet Implemented
     */
    private static List<ResourceLocation> theEndBiomes;

    /**
     * This event method ensures that specific features <em>do not</em> spawn on specific biomes.
     *
     * @see BiomeLoadingEvent
     */
    @SubscribeEvent
    public static void biomeLoadingEvent(BiomeLoadingEvent event)
    {
        if (overworldBiomes == null)
        {
            overworldBiomes = BiomeDictionary.getBiomes(BiomeDictionary.Type.OVERWORLD)
                    .stream().map(RegistryKey::location)
                    .distinct().collect(Collectors.toList());
        }

        if (netherBiomes == null)
        {
            netherBiomes = BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER)
                    .stream().map(RegistryKey::location)
                    .distinct().collect(Collectors.toList());
        }

        if (theEndBiomes == null)
        {
            theEndBiomes = BiomeDictionary.getBiomes(BiomeDictionary.Type.END)
                    .stream().map(RegistryKey::location)
                    .distinct().collect(Collectors.toList());
        }

        if (overworldBiomes.contains(event.getName())
                && !event.getCategory().equals(Biome.Category.BEACH)
                && !event.getCategory().equals(Biome.Category.DESERT)
                && !event.getCategory().equals(Biome.Category.OCEAN)
                && !event.getCategory().equals(Biome.Category.ICY)
                && !event.getCategory().equals(Biome.Category.NONE))
        {
            event.getGeneration().addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, randomEarthPatch);
        }
        else if (netherBiomes.contains(event.getName()))
        {
            System.out.println("event.getName() = " + event.getName());
            event.getGeneration().addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, randomBlackstonePatch);
        }
    }
}

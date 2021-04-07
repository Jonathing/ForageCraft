package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.world.ForageBlockPlacer;
import me.jonathing.minecraft.foragecraft.data.objects.ForageBlockTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class holds all of the world generation features for ForageCraft.
 *
 * @author Jonathing
 * @see #init()
 * @since 2.0.0
 */
public class ForageFeatures
{
    /**
     * The default random generation config that defines how the {@link ForageBlocks#rock}s and
     * {@link ForageBlocks#flat_rock}s are generated. The feature uses a {@link NonNullLazy} to prevent an
     * {@link ExceptionInInitializerError}.
     *
     * @see #ROCK_FEATURE
     * @see #init()
     */
    public static final NonNullLazy<BlockClusterFeatureConfig> ROCK_CONFIG =
            NonNullLazy.of(() -> (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .add(ForageBlocks.flat_rock.defaultBlockState(), 1)
                    .add(ForageBlocks.rock.defaultBlockState(), 1),
                    new ForageBlockPlacer(ForageBlockTags.ROCK_PLACEABLE)))
                    .tries(1)
                    .build());

    /**
     * The default random generation config that defines how the {@link ForageBlocks#stick}s are generated. The feature
     * uses a {@link NonNullLazy} to prevent an {@link ExceptionInInitializerError}.
     *
     * @see #STICK_FEATURE
     * @see #init()
     */
    public static final NonNullLazy<BlockClusterFeatureConfig> STICK_CONFIG =
            NonNullLazy.of(() -> (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .add(ForageBlocks.stick.defaultBlockState(), 1),
                    new ForageBlockPlacer(ForageBlockTags.STICK_PLACEABLE)))
                    .tries(2)
                    .build());

    /**
     * The default random generation config that defines how the {@link ForageBlocks#blackstone_rock}s are generated.
     * The feature uses a {@link NonNullLazy} to prevent an {@link ExceptionInInitializerError}.
     *
     * @see #BLACKSTONE_FEATURE
     * @see #init()
     */
    public static final NonNullLazy<BlockClusterFeatureConfig> BLACKSTONE_CONFIG =
            NonNullLazy.of(() -> (new BlockClusterFeatureConfig.Builder((new SimpleBlockStateProvider(ForageBlocks.blackstone_rock.defaultBlockState())),
                    new ForageBlockPlacer(ForageBlockTags.BLACKSTONE_ROCK_PLACEABLE)))
                    .xspread(12).zspread(12)
                    .yspread(10)
                    .tries(90)
                    .noProjection()
                    .build());

    /**
     * The default random generation config that defines how the {@link ForageBlocks#blackstone_flat_rock}s are
     * generated. The feature uses a {@link NonNullLazy} to prevent an {@link ExceptionInInitializerError}.
     *
     * @see #BLACKSTONE_FLAT_FEATURE
     * @see #init()
     */
    public static final NonNullLazy<BlockClusterFeatureConfig> BLACKSTONE_FLAT_CONFIG =
            NonNullLazy.of(() -> (new BlockClusterFeatureConfig.Builder((new SimpleBlockStateProvider(ForageBlocks.blackstone_flat_rock.defaultBlockState())),
                    new ForageBlockPlacer(ForageBlockTags.BLACKSTONE_ROCK_PLACEABLE)))
                    .xspread(12).zspread(12)
                    .yspread(10)
                    .tries(90)
                    .noProjection()
                    .build());

    /**
     * The {@link ConfiguredFeature} that dictates how the {@link #ROCK_CONFIG} is placed in the world. The
     * configured feature uses a {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     *
     * @see #ROCK_CONFIG
     * @see #init()
     */
    public static final NonNullLazy<ConfiguredFeature<?, ?>> ROCK_FEATURE =
            NonNullLazy.of(() -> Feature.RANDOM_PATCH
                    .configured(ROCK_CONFIG.get())
                    .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
                    .decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));

    /**
     * The {@link ConfiguredFeature} that dictates how the {@link #STICK_CONFIG} is placed in the world. The
     * configured feature uses a {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     *
     * @see #STICK_CONFIG
     * @see #init()
     */
    public static final NonNullLazy<ConfiguredFeature<?, ?>> STICK_FEATURE =
            NonNullLazy.of(() -> Feature.RANDOM_PATCH
                    .configured(STICK_CONFIG.get())
                    .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
                    .decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));

    /**
     * The {@link ConfiguredFeature} that dictates how the {@link #BLACKSTONE_CONFIG} is placed in the world. The
     * configured feature uses a {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     *
     * @see #BLACKSTONE_CONFIG
     * @see #init()
     */
    public static final NonNullLazy<ConfiguredFeature<?, ?>> BLACKSTONE_FEATURE =
            NonNullLazy.of(() -> Feature.RANDOM_PATCH.configured(BLACKSTONE_CONFIG.get())
                    .range(126)
                    .chance(1));

    /**
     * The {@link ConfiguredFeature} that dictates how the {@link #BLACKSTONE_FLAT_CONFIG} is placed in the world. The
     * configured feature uses a {@link NonNullLazy} to prevent {@link ExceptionInInitializerError}.
     *
     * @see #BLACKSTONE_FLAT_CONFIG
     * @see #init()
     */
    public static final NonNullLazy<ConfiguredFeature<?, ?>> BLACKSTONE_FLAT_FEATURE =
            NonNullLazy.of(() -> Feature.RANDOM_PATCH.configured(BLACKSTONE_FLAT_CONFIG.get())
                    .range(126)
                    .chance(1));

    public static ConfiguredFeature<?, ?> rockConfiguredFeature;
    public static ConfiguredFeature<?, ?> stickConfiguredFeature;
    public static ConfiguredFeature<?, ?> blackstoneConfiguredFeature;
    public static ConfiguredFeature<?, ?> blackstoneFlatConfiguredFeature;

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
        rockConfiguredFeature = registerConfiguredFeature("forage_random_rocks", ROCK_FEATURE.get());
        stickConfiguredFeature = registerConfiguredFeature("forage_random_sticks", STICK_FEATURE.get());
        blackstoneConfiguredFeature = registerConfiguredFeature("forage_random_blackstone_rocks", BLACKSTONE_FEATURE.get());
        blackstoneFlatConfiguredFeature = registerConfiguredFeature("forage_random_blackstone_rocks", BLACKSTONE_FLAT_FEATURE.get());
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
     * @since 2.2.0
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
     * @param event The biome loading event to use to add the features into the biomes.
     * @see BiomeLoadingEvent
     */
    static void biomeLoadingEvent(BiomeLoadingEvent event)
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

        Biome.Category biomeCategory = event.getCategory();
        BiomeGenerationSettingsBuilder biomeGenerator = event.getGeneration();

        if (overworldBiomes.contains(event.getName()))
        {
            // biomes that should contain both rocks and sticks
            if (!biomeCategory.equals(Biome.Category.BEACH)
                    && !biomeCategory.equals(Biome.Category.DESERT)
                    && !biomeCategory.equals(Biome.Category.OCEAN)
                    && !biomeCategory.equals(Biome.Category.ICY)
                    && !biomeCategory.equals(Biome.Category.NONE))
            {
                biomeGenerator.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, rockConfiguredFeature);

                // biomes that should contain only sticks
                if (!biomeCategory.equals(Biome.Category.MUSHROOM))
                {
                    biomeGenerator.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, stickConfiguredFeature);
                }
            }
        }

        // all nether biomes should attempt to have blackstone generate
        if (netherBiomes.contains(event.getName()))
        {
            biomeGenerator.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, blackstoneConfiguredFeature);
            biomeGenerator.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, blackstoneFlatConfiguredFeature);
        }
    }
}

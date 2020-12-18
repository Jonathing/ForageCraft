package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.common.block.StickBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.function.Supplier;

/**
 * This class holds all of the world generation features for ForageCraft.
 *
 * @author Jonathing
 * @see #initFeatures()
 * @since 2.0.0
 */
@Mod.EventBusSubscriber
public class ForageFeatures
{
    public static final Supplier<BlockClusterFeatureConfig> RANDOM_EARTH_CONFIG =
            () -> (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .addState(ForageBlocks.flat_rock.getDefaultState(), 1)
                    .addState(ForageBlocks.rock.getDefaultState(), 1)
                    .addState(((StickBlock) ForageBlocks.stick).getStateWithRandomDirection(), 2),
                    new SimpleBlockPlacer())).tries(1).build();

    public static final Supplier<ConfiguredFeature<?, ?>> RANDOM_EARTH_PATCH = () -> Feature.RANDOM_PATCH
            .configure(RANDOM_EARTH_CONFIG.get())
            .decorate(Features.Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
            .decorate(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 5, 10)));

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
    public static void initFeatures()
    {
        registerConfiguredFeature("forage_random_rocks", RANDOM_EARTH_PATCH.get());
    }

    /**
     * This event method ensures that specific features <em>do not</em> spawn on specific biomes.
     *
     * @see BiomeLoadingEvent
     */
    @SubscribeEvent
    public static void biomeLoadingEvent(BiomeLoadingEvent event)
    {
        if (!event.getCategory().equals(Biome.Category.BEACH)
                && !event.getCategory().equals(Biome.Category.DESERT)
                && !event.getCategory().equals(Biome.Category.NETHER)
                && !event.getCategory().equals(Biome.Category.THEEND)
                && !event.getCategory().equals(Biome.Category.NONE))
            event.getGeneration().feature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, RANDOM_EARTH_PATCH.get());
    }
}

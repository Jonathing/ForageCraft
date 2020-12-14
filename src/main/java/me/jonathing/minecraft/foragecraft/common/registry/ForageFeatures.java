package me.jonathing.minecraft.foragecraft.common.registry;

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

import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class ForageFeatures
{
    public static final Supplier<BlockClusterFeatureConfig> ROCK_STICK_CONFIG =
            () -> (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .addState(ForageBlocks.flat_rock.getDefaultState(), 1)
                    .addState(ForageBlocks.rock.getDefaultState(), 1)
                    .addState(ForageBlocks.stick.getDefaultState(), 2),
                    new SimpleBlockPlacer())).tries(1).build();

    public static final Supplier<ConfiguredFeature<?, ?>> ROCKS_STICKS_PATCH = () -> Feature.RANDOM_PATCH
            .configure(ROCK_STICK_CONFIG.get())
            .decorate(Features.Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
            .decorate(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 5, 10)));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerConfiguredFeature(String nameIn, ConfiguredFeature<FC, ?> featureIn)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, nameIn, featureIn);
    }

    public static void initFeatures()
    {
        registerConfiguredFeature("forage_random_rocks", ROCKS_STICKS_PATCH.get());
    }

    @SubscribeEvent
    public static void biomeLoadingEvent(BiomeLoadingEvent event)
    {
        if (!event.getCategory().equals(Biome.Category.BEACH)
                && !event.getCategory().equals(Biome.Category.DESERT)
                && !event.getCategory().equals(Biome.Category.NETHER)
                && !event.getCategory().equals(Biome.Category.THEEND)
                && !event.getCategory().equals(Biome.Category.NONE))
            event.getGeneration().feature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ROCKS_STICKS_PATCH.get());
    }
}

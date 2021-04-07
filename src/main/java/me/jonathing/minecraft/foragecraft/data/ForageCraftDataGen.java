package me.jonathing.minecraft.foragecraft.data;

import me.jonathing.minecraft.foragecraft.data.provider.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * This class handles all of data generation. The providers are just in separate classes and packages.
 *
 * @author Jonathing
 * @since 2.0.0
 */
public class ForageCraftDataGen
{
    /**
     * This is where we begin our data generation. Pretty important for this mod in particular.
     *
     * @param event The gather data event to use to run data generation with.
     * @see GatherDataEvent
     */
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();

        BlockTagsProvider blockTagsProvider = new ForageTagProvider.BlockTagProvider(generator, event.getExistingFileHelper());
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new ForageTagProvider.ItemTagProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        generator.addProvider(new ForageRecipeProvider(generator));
        generator.addProvider(new ForageLootProvider(generator));
        generator.addProvider(new ForagingRecipeProvider(generator));
        generator.addProvider(new ForageAdvancementProvider(generator));
    }
}

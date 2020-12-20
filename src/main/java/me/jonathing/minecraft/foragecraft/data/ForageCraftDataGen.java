package me.jonathing.minecraft.foragecraft.data;

import me.jonathing.minecraft.foragecraft.data.provider.ForageAdvancementProvider;
import me.jonathing.minecraft.foragecraft.data.provider.ForageLootProvider;
import me.jonathing.minecraft.foragecraft.data.provider.ForageRecipeProvider;
import me.jonathing.minecraft.foragecraft.data.provider.ForageTagProvider;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
     * @see GatherDataEvent
     */
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();

        BlockTagsProvider blockTagsProvider = new ForageTagProvider.BlockTagProvider(generator, event.getExistingFileHelper());
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new ForageTagProvider.ItemTagProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        generator.addProvider(new ForageRecipeProvider(generator));
        generator.addProvider(new ForageLootProvider(generator));
        generator.addProvider(new ForageAdvancementProvider(generator));
    }
}

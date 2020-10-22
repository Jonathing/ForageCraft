package me.jonathing.minecraft.foragecraft.data;

import me.jonathing.minecraft.foragecraft.data.provider.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ForageCraftDataGen
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();

        BlockTagsProvider blockTagsProvider = new ForageTagProvider.BlockTagProvider(generator, event.getExistingFileHelper());
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new ForageTagProvider.ItemTagProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        generator.addProvider(new ForageRecipeProvider(generator));
        generator.addProvider(new ForageLootProvider(generator));
    }
}

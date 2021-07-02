package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.jonathing.minecraft.foragecraft.data.objects.IToJson;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static me.jonathing.minecraft.foragecraft.data.ForageCraftData.LOGGER;

public abstract class ForageDataProvider<D extends IToJson<D>> implements IDataProvider
{
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    protected Map<ResourceLocation, D> data;
    protected final DataGenerator generator;
    protected final String name;

    public ForageDataProvider(DataGenerator generator, String name)
    {
        this.generator = generator;
        this.name = ForageInfo.MOD_ID + "/" + name;
    }

    @Override
    public void run(@Nonnull DirectoryCache cache)
    {
        this.data = this.gatherData();

        Path outputFolder = this.generator.getOutputFolder();

        for (Map.Entry<ResourceLocation, D> dataEntry : this.data.entrySet())
        {
            Path outputFile = this.createPath(outputFolder, dataEntry.getKey());

            try
            {
                JsonObject json = dataEntry.getValue().toJson();
                IDataProvider.save(GSON, cache, json, outputFile);
            }
            catch (IOException e)
            {
                LOGGER.error(String.format("Couldn't save %s %s", this.name, outputFile), e);
            }
        }
    }

    protected abstract Map<ResourceLocation, D> gatherData();

    protected Path createPath(Path outputFolder, ResourceLocation dataEntryName)
    {
        return outputFolder.resolve("data/" + dataEntryName.getNamespace() + "/" + this.name + "/" + dataEntryName.getPath() + ".json");
    }

    @Override
    @Nonnull
    public abstract String getName();
}

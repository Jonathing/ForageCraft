package me.jonathing.minecraft.foragecraft.common.handler.data;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import me.jonathing.minecraft.foragecraft.data.objects.ForagingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class ForagingRecipeHandler extends ForageDataHandler<ResourceLocation, ForagingRecipe>
{
    public ForagingRecipeHandler()
    {
        super(ForagingRecipe.DIRECTORY);
    }

    public Pair<CompoundNBT, CompoundNBT> entryToNBT(ResourceLocation key, ForagingRecipe value)
    {
        CompoundNBT keyNbt = new CompoundNBT();
        keyNbt.putString("name", key.toString());
        return Pair.of(keyNbt, value.toNBT());
    }

    public Pair<ResourceLocation, ForagingRecipe> entryFromNbt(CompoundNBT key, CompoundNBT value)
    {
        return Pair.of(new ResourceLocation(key.getString("name")), ForagingRecipe.fromNBT(value));
    }

    @Override
    protected Pair<ResourceLocation, ForagingRecipe> parseJson(JsonObject json, ResourceLocation name) throws MissingRegistryObjectException
    {
        return Pair.of(name, ForagingRecipe.fromJson(json));
    }
}

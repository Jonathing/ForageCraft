package me.jonathing.minecraft.foragecraft.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/*
 * I don't feel the need to document this utility class. If you want to know how to use it, you can see my advancement
 * data generation.
 */
public final class JsonUtil
{
    public static final class Reader
    {
        @ParametersAreNonnullByDefault
        public static LazyOptional<Block> getBlock(JsonObject json, String key)
        {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(json, key));
            return LazyUtil.LazyOptionalOf(() -> ForgeRegistries.BLOCKS.getValue(resourcelocation));
        }

        @ParametersAreNonnullByDefault
        public static LazyOptional<Item> getItem(JsonObject json, String key)
        {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(json, key));
            return LazyUtil.LazyOptionalOf(() -> ForgeRegistries.ITEMS.getValue(resourcelocation));
        }
    }

    public static final class Writer
    {
        @ParametersAreNonnullByDefault
        public static void writeBlock(JsonObject json, Block block, String key) throws NullPointerException
        {
            json.addProperty(key, ForgeRegistries.BLOCKS.getKey(block).toString());
        }

        @ParametersAreNonnullByDefault
        public static void writeItem(JsonObject json, IItemProvider item, String key) throws NullPointerException
        {
            json.addProperty(key, ForgeRegistries.ITEMS.getKey(item.asItem()).toString());
        }
    }
}

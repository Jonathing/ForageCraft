package me.jonathing.minecraft.foragecraft.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public final class JsonUtil
{
    @MethodsReturnNonnullByDefault
    public static final class Reader
    {
        public static Block getBlock(@Nonnull JsonObject json) throws JsonParseException, NullPointerException
        {
            if (json.has("block"))
            {
                ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(json, "block"));
                Block block = ForgeRegistries.BLOCKS.getValue(resourcelocation);

                if (block == null) throw new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");

                return block;
            }

            throw new NullPointerException("A block is required in the trigger JSON for the foraging trigger!");
        }

        public static Item getItem(@Nonnull JsonObject json) throws JsonParseException, NullPointerException
        {
            if (json.has("item"))
            {
                ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(json, "item"));
                Item item = ForgeRegistries.ITEMS.getValue(resourcelocation);

                if (item == null) throw new JsonSyntaxException("Unknown item type '" + resourcelocation + "'");

                return item;
            }

            throw new NullPointerException("An item is required in the trigger JSON for the foraging trigger!");
        }
    }

    public static final class Writer
    {
        @ParametersAreNonnullByDefault
        public static void writeBlock(JsonObject json, Block block) throws NullPointerException
        {
            json.addProperty("block", ForgeRegistries.BLOCKS.getKey(block).toString());
        }

        @ParametersAreNonnullByDefault
        public static void writeItem(JsonObject json, Item item) throws NullPointerException
        {
            json.addProperty("item", ForgeRegistries.ITEMS.getKey(item).toString());
        }
    }
}

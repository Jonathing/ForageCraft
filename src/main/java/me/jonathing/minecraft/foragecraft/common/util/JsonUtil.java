package me.jonathing.minecraft.foragecraft.common.util;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Utility class for reading and writing JSON files.
 *
 * @author Jonathing
 * @since a while
 */
public final class JsonUtil
{
    public static final class Reader
    {
        /**
         * This method uses a generic type that implements {@link IForgeRegistry} to read a string from a JSON file and
         * get the related object from its respective registry.
         *
         * @param type The class of the object to use.
         * @param json The json to read from.
         * @param key The key in the {@link JsonObject} to read the string from.
         * @param <T> A type that extends {@link IForgeRegistry} so it is guaranteed that a registry exists for it.
         * @return The item from registry, if it (hopefully) exists.
         */
        @ParametersAreNonnullByDefault
        public static <T extends IForgeRegistryEntry<T>> LazyOptional<T> fromRegistry(Class<T> type, JsonObject json, String key)
        {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getAsString(json, key));
            return LazyUtil.LazyOptionalOf(() -> RegistryManager.ACTIVE.getRegistry(type).getValue(resourceLocation));
        }
    }

    public static final class Writer
    {
        /**
         * This method uses a generic type that implements {@link IForgeRegistry} to write a string to a JSON file based
         * on the related object from its respective registry.
         *
         * @param type The class of the object to use.
         * @param object The object to write into the {@link JsonObject}.
         * @param json The json to write into.
         * @param key The key to write the object into.
         * @param <T> A type that extends {@link IForgeRegistry} so it is guaranteed that a registry exists for it.
         */
        @ParametersAreNonnullByDefault
        public static <T extends IForgeRegistryEntry<T>> void fromRegistry(Class<T> type, T object, JsonObject json, String key)
        {
            json.addProperty(key, RegistryManager.ACTIVE.getRegistry(type).getKey(object).toString());
        }
    }
}

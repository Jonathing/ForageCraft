package me.jonathing.minecraft.foragecraft.asm.js;

import com.google.common.collect.ImmutableList;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.List;
import java.util.Map;

/**
 * This class contains hooks used in the {@link net.minecraft.item.crafting.RecipeManager} ASM code.
 * <p>
 * I was originally going to write a Mixin based off of this, but I didn't find a feasible way to do so without
 * rewriting the entire method, and I thought that this would be a cool way to learn and play with ASM, so this is the
 * implementation of that.
 *
 * @author Jonathing
 * @see net.minecraft.item.crafting.RecipeManager#apply(Map, IResourceManager, IProfiler)
 * @since 2.1.2
 */
public class RecipeManagerJS
{
    /**
     * List of all of the Patchouli recipes to skip parsing.
     */
    private static final List<ResourceLocation> PATCHOULI_RECIPES = ImmutableList.of(
            ForageCraft.locate("guide_book")
    );

    /**
     * Used in the JS ASM hack to skip any specific recipe parsing. It uses the {@link ModList} to check if a particular
     * mod is <em>not</em> loaded. This way, I can disable recipes specific to that mod so the console doesn't shit
     * itself when it tries to parse through that recipe.
     *
     * @param resourceLocation The given resource location to check.
     * @return The result of the check.
     */
    public static boolean skipRecipe(ResourceLocation resourceLocation)
    {
        boolean result = false;

        if (!ModList.get().isLoaded("patchouli"))
            result = PATCHOULI_RECIPES.contains(resourceLocation);

        return result;
    }
}

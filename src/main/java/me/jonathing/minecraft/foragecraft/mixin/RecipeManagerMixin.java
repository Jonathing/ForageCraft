package me.jonathing.minecraft.foragecraft.mixin;

import com.google.gson.JsonElement;
import me.jonathing.minecraft.foragecraft.data.ForageCraftData;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

import static me.jonathing.minecraft.foragecraft.ForageCraft.LOGGER;

/**
 * This mixin class is used to make certain modifications to the {@link RecipeManager} class to ForageCraft's needs.
 *
 * @author Jonathing
 * @see RecipeManager
 * @since 2.1.3
 */
@Mixin(RecipeManager.class)
public class RecipeManagerMixin
{
    private static final Marker FC_MARKER = MarkerManager.getMarker("RecipeManagerMixin");

    /**
     * This method hooks into the {@link org.spongepowered.asm.mixin.injection.points.MethodHead} of the
     * {@link RecipeManager#apply(Map, IResourceManager, IProfiler)} method to modify the {@link Map} of
     * {@link ResourceLocation}s and {@link JsonElement}s containing all of the recipes to be parsed on world load. It
     * uses the {@link ModList} to check if a particular mod is <em>not</em> loaded. This way, I can disable recipes
     * specific to that mod so the console doesn't shit itself when it tries to parse through that recipe.
     *
     * @param objectIn          The {@link Map} of {@link ResourceLocation}s and {@link JsonElement} to edit.
     * @param resourceManagerIn The {@link IResourceManager} that handles the recipes. <strong>Unused in this
     *                          mixin.</strong>
     * @param profilerIn        The {@link IProfiler} that is used during the method. <strong>Unused in this
     *                          mixin.</strong>
     * @param callback          Mixin's way of returning a method. <strong>Unused in this mixin.</strong>
     * @see RecipeManager#apply(Map, IResourceManager, IProfiler)
     */
    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/resources/IResourceManager;Lnet/minecraft/profiler/IProfiler;)V",
            at = @At("HEAD")
    )
    private void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn, CallbackInfo callback)
    {
        ForageCraftData.OPTIONAL_RECIPES.forEach((modid, recipes) ->
        {
            if (!ModList.get().isLoaded(modid))
            {
                int size = recipes.size();
                LOGGER.debug(FC_MARKER, "Skipping {} recipe{} since {} is not installed.", size, size == 1 ? "" : "s", modid);
                ForageCraftData.OPTIONAL_RECIPES.get(modid).forEach(objectIn::remove);
            }
        });
    }
}

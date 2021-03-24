package me.jonathing.minecraft.foragecraft.data;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.asm.mixin.main.RecipeManagerMixin;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ForageDataLists
{
    /**
     * List of all of the Patchouli recipes to skip parsing if Patchouli is not installed.
     *
     * @see RecipeManagerMixin#apply(Map, IResourceManager, IProfiler, CallbackInfo)
     */
    public static final List<ResourceLocation> PATCHOULI_RECIPES = Collections.singletonList(
            ForageCraft.locate("guide_book")
    );
}

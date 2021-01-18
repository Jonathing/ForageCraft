package me.jonathing.minecraft.foragecraft.data;

import com.google.common.collect.ImmutableList;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

public class ForageDataLists
{
	/**
	 * List of all of the Patchouli recipes to skip parsing if Patchouli is not installed.
	 *
	 * @see me.jonathing.minecraft.foragecraft.asm.mixin.RecipeManagerMixin#modify$objectIn(Map)
	 */
	public static final List<ResourceLocation> PATCHOULI_RECIPES = ImmutableList.of(
			ForageCraft.locate("guide_book")
	);
}

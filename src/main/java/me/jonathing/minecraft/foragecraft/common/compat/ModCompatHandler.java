package me.jonathing.minecraft.foragecraft.common.compat;

import me.jonathing.minecraft.foragecraft.common.compat.patchouli.PatchouliHelper;
import net.minecraftforge.fml.ModList;

/**
 * This class handles mod compatibility and executes setup events based on what mods are loaded.
 *
 * @author Jonathing
 * @since 2.0.1
 */
public class ModCompatHandler
{
    public static void init()
    {
        if (ModList.get().isLoaded("patchouli"))
            PatchouliHelper.registerMultiblocks();
    }
}

package me.jonathing.minecraft.foragecraft.common.compat;

import me.jonathing.minecraft.foragecraft.common.compat.patchouli.PatchouliHelper;
import net.minecraftforge.fml.ModList;

public class ModCompatHandler
{
    public static void preInit()
    {

    }

    public static void init()
    {
        if (ModList.get().isLoaded("patchouli"))
            PatchouliHelper.registerMultiblocks();
    }

    public static void postInit()
    {

    }
}

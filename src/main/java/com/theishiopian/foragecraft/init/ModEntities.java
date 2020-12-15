package com.theishiopian.foragecraft.init;

import com.theishiopian.foragecraft.ForageCraftMod;
import com.theishiopian.foragecraft.entity.EntityCrow;
import com.theishiopian.foragecraft.entity.EntityRockFlat;
import com.theishiopian.foragecraft.entity.EntityRockNormal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
    static int id = 1;

    public static void init()
    {
        EntityRegistry.registerModEntity(new ResourceLocation(ForageCraftMod.MODID, "rock_normal"), EntityRockNormal.class, "rock_normal", id++, ForageCraftMod.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(ForageCraftMod.MODID, "rock_flat"), EntityRockFlat.class, "rock_flat", id++, ForageCraftMod.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(ForageCraftMod.MODID, "crow"), EntityCrow.class, "crow", id, ForageCraftMod.instance, 64, 3, true, 0, 0);
    }
}

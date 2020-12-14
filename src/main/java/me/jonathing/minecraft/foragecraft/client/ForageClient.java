package me.jonathing.minecraft.foragecraft.client;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ForageClient
{
    public static void clientSetup(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ForageBlocks.leek_crop, RenderType.getCutout());
    }
}

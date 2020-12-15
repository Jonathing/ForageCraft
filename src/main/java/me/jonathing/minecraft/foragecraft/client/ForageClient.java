package me.jonathing.minecraft.foragecraft.client;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * This class contains methods that are only ever executed on the {@link net.minecraftforge.api.distmarker.Dist#CLIENT}.
 *
 * @author Jonathing
 * @see net.minecraftforge.api.distmarker.Dist#CLIENT
 * @see ForageCraft#ForageCraft()
 * @since 2.0.0
 */
public class ForageClient
{
    /**
     * Runs tasks specific to the client. It is called from {@link ForageCraft#ForageCraft()}.
     *
     * @see ForageCraft#ForageCraft()
     * @see FMLClientSetupEvent
     */
    public static void clientSetup(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ForageBlocks.leek_crop, RenderType.getCutout());
    }
}

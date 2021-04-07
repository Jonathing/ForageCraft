package me.jonathing.minecraft.foragecraft.client;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public static void addEventListeners(IEventBus mod, IEventBus forge)
    {
        mod.addListener(ForageClient::clientSetup);
    }

    /**
     * Runs tasks specific to the client. It is called from {@link ForageCraft#ForageCraft()}.
     *
     * @param event The modloader client setup event to use for client setup.
     * @see ForageCraft#ForageCraft()
     * @see FMLClientSetupEvent
     */
    private static void clientSetup(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ForageBlocks.leek_crop, RenderType.cutout());
    }
}

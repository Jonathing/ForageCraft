package com.theishiopian.foragecraft.handler;

import net.minecraftforge.common.MinecraftForge;


public class EventHandler
{
    public void register()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /* FML Events */
    /*
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) //IDK how to get this one to work.
    {
        String developerWarning = "Developer mode is enabled. Please check the console for extra logging features.";
        EntityPlayer player = event.player;
        //World world = event.player.world;
        ForageLogger.printChat(player, ("[ForageCraft] " + developerWarning));
        ForageLogger.printInfo("Attempted to print to player: \"" + developerWarning + "\"");
    }
    */
}

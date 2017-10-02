package com.theishiopian.foragecraft.handler;

import com.theishiopian.foragecraft.ForageLogger;
import com.theishiopian.foragecraft.config.ConfigVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

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

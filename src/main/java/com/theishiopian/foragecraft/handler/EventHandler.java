package com.theishiopian.foragecraft.handler;

import com.theishiopian.foragecraft.ForageLogger;
import com.theishiopian.foragecraft.config.ConfigVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
    /* FML Events */
    @SubscribeEvent
    public static void developerWarningClient(EntityJoinWorldEvent event) //TODO: This kinda isn't working right now
    {
        if(ConfigVariables.developerMode)
        {
            String developerWarning = "Developer mode is enabled. Please check the console for extra logging features.";
            Entity entity = event.getEntity();
            World world = entity.world;
            if (!world.isRemote)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;
                    ForageLogger.printChat(player, developerWarning);
                }
                else
                {
                    ForageLogger.printError("EntityJoinWorldEvent entity not instanceof EntityPlayer. Aborting send message.");
                    ForageLogger.printWarn("Error while sending message to player. Message meant to send: \"" + developerWarning + "\"");
                }
            }
            else
            {
                ForageLogger.printError("world isRemote. Aborting send message.");
                ForageLogger.printWarn("Error while sending message to player. Message meant to send: \"" + developerWarning + "\"");
            }
        }
    }
}

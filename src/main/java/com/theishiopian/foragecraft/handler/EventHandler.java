package com.theishiopian.foragecraft.handler;

import com.theishiopian.foragecraft.ForageLogger;
import com.theishiopian.foragecraft.config.ConfigVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class EventHandler
{
    /* FML Events */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void developerWarningClient(EntityJoinWorldEvent event)
    {
        if(ConfigVariables.developerMode)
        {
            Entity entity = event.getEntity();
            World world = entity.world;
            if (!world.isRemote)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;
                    ForageLogger.printChat(player, "Developer mode is enabled. Please check the console for extra logging features.");
                }
            }
        }
    }
}

package com.theishiopian.foragecraft.handler;

import com.theishiopian.foragecraft.world.ScarecrowTracking;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnHandler
{
    @SubscribeEvent
    public void spawnHandler(LivingSpawnEvent.CheckSpawn event)
    {
        if ((!(event.getEntity() instanceof EntityPlayer)) && (!(event.getWorld().isRemote)))
        {
            if (ScarecrowTracking.get(event.getWorld()).inRange(event.getEntity()))
            {
                if (event.getWorld().getBlockState(event.getEntity().getPosition()).getBlock() == Blocks.FARMLAND)
                {
                    event.setResult(Result.DENY);
                }
                if (event.getEntity() instanceof EntityBat)
                {
                    event.setResult(Result.DENY);
                    // I hate bats
                }
            }
        }
    }
}

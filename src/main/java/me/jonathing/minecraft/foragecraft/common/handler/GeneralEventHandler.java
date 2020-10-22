package me.jonathing.minecraft.foragecraft.common.handler;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GeneralEventHandler
{
    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        if (event.getItemStack() == null || event.getFace() == null) return;

        if (event.getItemStack().getItem().equals(Items.STICK))
        {
            event.getWorld().setBlockState(event.getPos().offset(event.getFace()), ForageBlocks.stick.getDefaultState());
            event.getPlayer().swingArm(event.getHand());
        }
    }
}

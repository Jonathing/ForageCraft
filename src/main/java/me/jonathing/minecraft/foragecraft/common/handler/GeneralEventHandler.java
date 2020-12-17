package me.jonathing.minecraft.foragecraft.common.handler;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * This class is a general event handler that handles some events that do not necessarily need a class of their own.
 *
 * @author Jonathing
 * @since 2.0.0
 */
@Mod.EventBusSubscriber
public class GeneralEventHandler
{
    /**
     * This event method sets the fuel burn time for specific items or blocks in ForageCraft. Since they cannot be
     * defined in item properties, they are instead defined here.
     *
     * @see FurnaceFuelBurnTimeEvent
     * @since 2.0.0
     */
    @SubscribeEvent
    public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event)
    {
        if (event.getItemStack().getItem().equals(ForageBlocks.fascine.asItem()))
        {
            event.setBurnTime(20 * 5 * 9 * 9);
        }
        else if (event.getItemStack().getItem().equals(ForageItems.stick_bundle))
        {
            event.setBurnTime(20 * 5 * 9);
        }
    }
}

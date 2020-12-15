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
     * This event method allows the {@link Items#STICK} to be placed on the ground as a {@link ForageBlocks#stick} even
     * though that is not its proper function in vanilla. Various statements are commented in this method for reference.
     *
     * @see PlayerInteractEvent.RightClickBlock
     * @see me.jonathing.minecraft.foragecraft.common.blocks.StickBlock
     * @since 2.0.0
     */
    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        if (event.getItemStack() == null || event.getFace() == null) return;

        if (event.getItemStack().getItem().equals(Items.STICK) && event.getWorld().getBlockState(event.getPos().offset(event.getFace()).down()).isSolid())
        {
            // set the blockstate
            event.getWorld().setBlockState(event.getPos().offset(event.getFace()), ForageBlocks.stick.getDefaultState());

            // swing the hand
            event.getPlayer().swingArm(event.getHand());

            // play wood placement sound
            event.getPlayer().playSound(SoundEvents.BLOCK_WOOD_PLACE, 1.0F, 1.0F);

            // shrink the stack if not in creative
            if (!event.getPlayer().isCreative())
                event.getItemStack().shrink(1);
        }
    }

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

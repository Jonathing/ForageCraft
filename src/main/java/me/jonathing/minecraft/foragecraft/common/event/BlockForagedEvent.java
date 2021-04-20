package me.jonathing.minecraft.foragecraft.common.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Whenever a block has been foraged, the {@link me.jonathing.minecraft.foragecraft.common.handler.ForagingEventHandler}
 * will post a new block foraged event, which is an extension of {@link BlockEvent.BreakEvent} but with additional info
 * as to the forage drop itself. This way, you are able to change the attributes of a forage drop depending on your
 * specific needs (i.e. dimension, biome, etc.).
 * <p>
 * Please <strong>do not</strong> go changing one of these events on a whim. The attributes should only be changed if
 * your mod dictates that they need to be for a specific purpose, such as if your biome has some sort of custom stick
 * or something along those lines. If you want to make your own forage drops, you can use data for that.
 *
 * @author Jonathing
 * @since 2.2.2
 */
@Cancelable
public class BlockForagedEvent extends BlockEvent
{
    private final PlayerEntity player;
    private IItemProvider item;
    private int itemCount;

    private ItemStack forceStack;

    /**
     * This constructor creates a new {@link BlockForagedEvent} to be dispatched into Forge's event bus. If the given
     * {@link PlayerEntity} is not an instance of a {@link ServerPlayerEntity}, the event is automatically
     * {@link Cancelable} canceled.
     *
     * @param parent    The block break event that contains information regarding the broken block and the
     *                  {@link PlayerEntity} responsible.
     * @param item      The item that will be dropped by this forage drop.
     * @param itemCount The amount of the item that will be dropped.
     * @see me.jonathing.minecraft.foragecraft.common.handler.ForagingEventHandler#postEvent(BlockForagedEvent)
     */
    public BlockForagedEvent(BlockEvent.BreakEvent parent, IItemProvider item, int itemCount)
    {
        super(parent.getWorld(), parent.getPos(), parent.getState());
        this.player = parent.getPlayer();
        this.item = item;
        this.itemCount = itemCount;

        this.forceStack = null;

        if (!(this.player instanceof ServerPlayerEntity))
            this.setCanceled(true);
    }

    public ServerPlayerEntity getPlayer()
    {
        return (ServerPlayerEntity) this.player;
    }

    public IItemProvider getItem()
    {
        return this.item;
    }

    public int getItemCount()
    {
        return this.itemCount;
    }

    public void setItem(IItemProvider item)
    {
        this.item = item;
    }

    public void setItemCount(int itemCount)
    {
        this.itemCount = itemCount;
    }

    /**
     * This method forces this event to use a specific {@link ItemStack} for the
     * {@link me.jonathing.minecraft.foragecraft.common.handler.ForagingEventHandler} to drop.
     *
     * @param stack The item stack to drop.
     * @deprecated Please use {@link #forceItemStack(ItemStack, IItemProvider)} instead as you will be able to define
     * your own default {@link IItemProvider}, which is safer for the event to use.
     */
    @Deprecated
    public void forceItemStack(ItemStack stack)
    {
        this.forceItemStack(stack, stack.getItem());
    }

    /**
     * This method forces this event to use a specific {@link ItemStack} for the
     * {@link me.jonathing.minecraft.foragecraft.common.handler.ForagingEventHandler} to drop.
     *
     * @param stack The item stack to drop.
     * @param item  The item provider for the event to use.
     */
    public void forceItemStack(ItemStack stack, IItemProvider item)
    {
        this.forceStack = stack;
        this.item = item;
    }

    public ItemStack getStackToDrop()
    {
        return this.forceStack == null ? new ItemStack(this.item, this.itemCount) : this.forceStack;
    }

    @Override
    public String toString()
    {
        return String.format("%s{%s, %s, %d}", this.getClass().getSimpleName(), this.getState().getBlock(), this.getItem(), this.getItemCount());
    }
}

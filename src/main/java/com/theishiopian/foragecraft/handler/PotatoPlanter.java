package com.theishiopian.foragecraft.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotatoPlanter
{
    @SubscribeEvent
    public void poisonousPotatoRightClick(RightClickBlock event)
    {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();

        event.getEntityPlayer().getHeldItemMainhand();
        if (player.getHeldItemMainhand().getItem() == Items.POISONOUS_POTATO && event.getFace() == EnumFacing.UP && world.getBlockState(event.getPos()).getBlock().canSustainPlant(world.getBlockState(event.getPos()), world, event.getPos(), EnumFacing.UP, (IPlantable) Items.POTATO))
        {
            if (!world.isRemote)
            {
                if (!player.capabilities.isCreativeMode)
                {
                    player.inventory.clearMatchingItems(Items.POISONOUS_POTATO, -1, 1, null);
                }
                if (world.getBlockState(event.getPos().up(+1)).getBlock() == Blocks.AIR)
                {
                    world.setBlockState(event.getPos().up(+1), Blocks.POTATOES.getDefaultState());
                }
            }
        }
    }
}


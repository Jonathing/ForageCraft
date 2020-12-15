package com.theishiopian.foragecraft.handler;

import com.theishiopian.foragecraft.config.ConfigVariables;
import com.theishiopian.foragecraft.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class BlockForageHandler
{
    // RNG's for randomness
    public static Random CHANCE = new java.util.Random();
    public static Random AMOUNT = new java.util.Random();

    @SubscribeEvent
    public void blockBreak(HarvestDropsEvent event)
    {
        World world = event.getWorld();
        if (!world.isRemote)
        {
            Block target = event.getState().getBlock();
            // leaves handler
            if (target == Blocks.LEAVES || target == Blocks.LEAVES2)
            {
                if (CHANCE.nextFloat() < ConfigVariables.branchChance)
                {
                    event.getDrops().add(new ItemStack(Items.STICK, AMOUNT.nextInt(ConfigVariables.branchMaxAmount + 1)));
                }
            }
            // grass block handler
            if (target == Blocks.GRASS)
            {
                if (CHANCE.nextFloat() < ConfigVariables.rootChance)
                {
                    event.getDrops().add(new ItemStack(Items.STICK, AMOUNT.nextInt(ConfigVariables.rootMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.rockChance)
                {
                    event.getDrops().add(new ItemStack(ModBlocks.rock_normal, AMOUNT.nextInt(ConfigVariables.rockMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.wildCarrotChance)
                {
                    event.getDrops().add(new ItemStack(Items.CARROT, AMOUNT.nextInt(ConfigVariables.wildCarrotMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.wildPotatoChance)
                {
                    event.getDrops().add(new ItemStack(Items.POTATO, AMOUNT.nextInt(ConfigVariables.wildPotatoMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.wildToxicPotatoChance)
                {
                    event.getDrops().add(new ItemStack(Items.POISONOUS_POTATO, AMOUNT.nextInt(ConfigVariables.wildToxicPotatoMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.wildBeetRootChance)
                {
                    event.getDrops().add(new ItemStack(Items.BEETROOT, AMOUNT.nextInt(ConfigVariables.wildBeetRootMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.buriedBonesChance)
                {
                    event.getDrops().add(new ItemStack(Items.BONE, AMOUNT.nextInt(ConfigVariables.buriedBonesMaxBoneAmount + 1)));
                    event.getDrops().add(new ItemStack(Items.SKULL, AMOUNT.nextInt(ConfigVariables.buriedBonesMaxSkullAmount + 1)));
                }
            }
            // dirt handler
            if (target == Blocks.DIRT)
            {
                if (CHANCE.nextFloat() < ConfigVariables.deepRootChance)
                {
                    event.getDrops().add(new ItemStack(Items.STICK, AMOUNT.nextInt(ConfigVariables.deepRootMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.rockChance)
                {
                    event.getDrops().add(new ItemStack(ModBlocks.rock_normal, AMOUNT.nextInt(ConfigVariables.rockMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.buriedFlintChance)
                {
                    event.getDrops().add(new ItemStack(Items.FLINT, AMOUNT.nextInt(ConfigVariables.buriedFlintMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.buriedBonesChance)
                {
                    event.getDrops().add(new ItemStack(Items.BONE, AMOUNT.nextInt(ConfigVariables.buriedBonesMaxBoneAmount + 1)));
                    event.getDrops().add(new ItemStack(Items.SKULL, AMOUNT.nextInt(ConfigVariables.buriedBonesMaxSkullAmount + 1)));
                }
            }
            // stone handler
            if (target == Blocks.STONE)
            {
                if (CHANCE.nextFloat() < ConfigVariables.goldChance)
                {
                    event.getDrops().add(new ItemStack(Items.GOLD_NUGGET, AMOUNT.nextInt(ConfigVariables.goldMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.rockChance)
                {
                    event.getDrops().add(new ItemStack(ModBlocks.rock_normal, AMOUNT.nextInt(ConfigVariables.rockMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.flintChipChance)
                {
                    event.getDrops().add(new ItemStack(Items.FLINT, AMOUNT.nextInt(ConfigVariables.flintChipMaxAmount + 1)));
                }
            }
            // coal_ore handler
            if (target == Blocks.COAL_ORE)
            {
                if (CHANCE.nextFloat() < ConfigVariables.coalDiamondChance)
                {
                    event.getDrops().add(new ItemStack(Items.DIAMOND, AMOUNT.nextInt(ConfigVariables.coalDiamondMaxAmount + 1)));
                }
                if (CHANCE.nextFloat() < ConfigVariables.coalEmeraldChance)
                {
                    event.getDrops().add(new ItemStack(Items.EMERALD, AMOUNT.nextInt(ConfigVariables.coalEmeraldMaxAmount + 1)));
                }
            }
            // nether quartz handler
            if (target == Blocks.QUARTZ_ORE)
            {
                if (CHANCE.nextFloat() < ConfigVariables.netherGoldChance)
                {
                    event.getDrops().add(new ItemStack(Items.GOLD_NUGGET, AMOUNT.nextInt(ConfigVariables.netherGoldMaxAmount + 1)));
                }
            }
            // gravel handler
            if (target == Blocks.GRAVEL)
            {
                if (CHANCE.nextFloat() < ConfigVariables.rockChance)
                {
                    event.getDrops().add(new ItemStack(ModBlocks.rock_normal, AMOUNT.nextInt(ConfigVariables.rockMaxAmount + 1)));
                }
            }
        }
    }
}				


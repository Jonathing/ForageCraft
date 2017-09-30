package com.theishiopian.foragecraft.integration.jei;

import javax.annotation.Nonnull;

import com.theishiopian.foragecraft.ForageLogger;
import com.theishiopian.foragecraft.config.ConfigVariables;
import com.theishiopian.foragecraft.init.ModBlocks;
import com.theishiopian.foragecraft.init.ModItems;
import mezz.jei.api.BlankModPlugin;
//import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

// There's a bunch of deprecated methods here. Idk what the other methods are so I'm leaving them as is.
@SuppressWarnings("deprecation")
@mezz.jei.api.JEIPlugin
public class ForageJEIPlugin extends BlankModPlugin
{
    @Override
    public void register(IModRegistry registry) {

        //IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        registry.addDescription(new ItemStack(ModItems.stick_bundle), "jei.description.foraging.foragecraft.stick_bundle");
        registry.addDescription(new ItemStack(ModItems.straw), "jei.description.foraging.foragecraft.straw");
        registry.addDescription(new ItemStack(ModBlocks.fascine), "jei.description.foraging.foragecraft.fascine");
        registry.addDescription(new ItemStack(ModBlocks.road_stone), "jei.description.foraging.foragecraft.road_stone");
        registry.addDescription(new ItemStack(ModBlocks.scarecrow), "jei.description.foraging.foragecraft.scarecrow");
        registry.addDescription(new ItemStack(ModBlocks.straw_bale), "jei.description.foraging.foragecraft.straw_bale");
        if(ConfigVariables.jeiVanillaInt)
        {
            registry.addDescription(new ItemStack(Items.STICK), "jei.description.foraging.minecraft.stick");
            registry.addDescription(new ItemStack(Items.CARROT), "jei.description.foraging.minecraft.carrot");
            registry.addDescription(new ItemStack(Items.POTATO), "jei.description.foraging.minecraft.potato");
            registry.addDescription(new ItemStack(Items.POISONOUS_POTATO), "jei.description.foraging.minecraft.toxic_potato");
            registry.addDescription(new ItemStack(Items.BEETROOT), "jei.description.foraging.minecraft.beetroot");
            registry.addDescription(new ItemStack(Items.BONE), "jei.description.foraging.minecraft.bone");
            registry.addDescription(new ItemStack(Items.SKULL), "jei.description.foraging.minecraft.skulls");
            registry.addDescription(new ItemStack(Items.FLINT), "jei.description.foraging.minecraft.flint");
            registry.addDescription(new ItemStack(Items.GOLD_NUGGET), "jei.description.foraging.minecraft.gold_nugget");
            registry.addDescription(new ItemStack(Items.DIAMOND), "jei.description.foraging.minecraft.diamond");
            registry.addDescription(new ItemStack(Items.EMERALD), "jei.description.foraging.minecraft.emerald");
            ForageLogger.printDevelop("Vanilla items registered");
        }


    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
    }
}
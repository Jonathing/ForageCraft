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
// Even though they seem to be deprecated, they still work.
@SuppressWarnings("deprecation")
@mezz.jei.api.JEIPlugin
public class ForageJEIPlugin extends BlankModPlugin
{
    @Override
    public void register(IModRegistry r) {

        //IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        
        regJEIDesc(r, new ItemStack(ModItems.stick_bundle), "jei.description.foraging.foragecraft.stick_bundle");
        regJEIDesc(r, new ItemStack(ModItems.straw), "jei.description.foraging.foragecraft.straw");
        regJEIDesc(r, new ItemStack(ModBlocks.fascine), "jei.description.foraging.foragecraft.fascine");
        regJEIDesc(r, new ItemStack(ModBlocks.road_stone), "jei.description.foraging.foragecraft.road_stone");
        regJEIDesc(r, new ItemStack(ModBlocks.scarecrow), "jei.description.foraging.foragecraft.scarecrow");
        regJEIDesc(r, new ItemStack(ModBlocks.straw_bale), "jei.description.foraging.foragecraft.straw_bale");
        regJEIDesc(r, new ItemStack(ModItems.leek), "jei.description.foraging.foragecraft.leek");
        ForageLogger.printDevelop("ForageCraft items registered into JEI.");
        if(ConfigVariables.jeiVanillaInt)
        {
            regJEIDesc(r, new ItemStack(Items.STICK), "jei.description.foraging.minecraft.stick");
            regJEIDesc(r, new ItemStack(Items.CARROT), "jei.description.foraging.minecraft.carrot");
            regJEIDesc(r, new ItemStack(Items.POTATO), "jei.description.foraging.minecraft.potato");
            regJEIDesc(r, new ItemStack(Items.POISONOUS_POTATO), "jei.description.foraging.minecraft.toxic_potato");
            regJEIDesc(r, new ItemStack(Items.BEETROOT), "jei.description.foraging.minecraft.beetroot");
            regJEIDesc(r, new ItemStack(Items.BONE), "jei.description.foraging.minecraft.bone");
            regJEIDesc(r, new ItemStack(Items.SKULL), "jei.description.foraging.minecraft.skulls");
            regJEIDesc(r, new ItemStack(Items.FLINT), "jei.description.foraging.minecraft.flint");
            regJEIDesc(r, new ItemStack(Items.GOLD_NUGGET), "jei.description.foraging.minecraft.gold_nugget");
            regJEIDesc(r, new ItemStack(Items.DIAMOND), "jei.description.foraging.minecraft.diamond");
            regJEIDesc(r, new ItemStack(Items.EMERALD), "jei.description.foraging.minecraft.emerald");
            ForageLogger.printDevelop("Vanilla item descriptions from ForageCraft registered into JEI.");
        }
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime)
    {
    }

    public static void regJEIDesc(IModRegistry r, ItemStack i, String desc)
    {
        r.addDescription(i, desc);
    }
}
package com.theishiopian.foragecraft.integration.jei;

import javax.annotation.Nonnull;

import com.theishiopian.foragecraft.ConfigVariables;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class ForageJEIPlugin extends BlankModPlugin
{
    @Override
    public void register(IModRegistry registry) {

        // IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        // Deprecated methods
        if(ConfigVariables.jeiVanillaInt)
        {
            registry.addDescription(new ItemStack(Items.STICK), "jei.description.foraging.stick");
            registry.addDescription(new ItemStack(Items.CARROT), "jei.description.foraging.carrot");
            registry.addDescription(new ItemStack(Items.POTATO), "jei.description.foraging.potato");
            registry.addDescription(new ItemStack(Items.POISONOUS_POTATO), "jei.description.foraging.toxicpotato");
            registry.addDescription(new ItemStack(Items.BEETROOT), "jei.description.foraging.beetroot");
            registry.addDescription(new ItemStack(Items.BONE), "jei.description.foraging.bone");
            registry.addDescription(new ItemStack(Items.SKULL), "jei.description.foraging.skulls");
            registry.addDescription(new ItemStack(Items.FLINT), "jei.description.foraging.flint");
            registry.addDescription(new ItemStack(Items.GOLD_NUGGET), "jei.description.foraging.goldnugget");
            registry.addDescription(new ItemStack(Items.DIAMOND), "jei.description.foraging.diamond");
            registry.addDescription(new ItemStack(Items.EMERALD), "jei.description.foraging.emerald");
        }

    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
    }
}
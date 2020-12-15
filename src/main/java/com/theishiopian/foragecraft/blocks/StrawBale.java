package com.theishiopian.foragecraft.blocks;

import net.minecraft.block.BlockHay;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StrawBale extends BlockHay
{
    public StrawBale()
    {
        this.setTranslationKey("straw_bale");
        this.setRegistryName("straw_bale");
        this.setSoundType(SoundType.PLANT);
    }

    @SideOnly(Side.CLIENT)
    public void initModel()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

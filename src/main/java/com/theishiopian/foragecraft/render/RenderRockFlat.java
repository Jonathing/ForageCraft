package com.theishiopian.foragecraft.render;

import com.theishiopian.foragecraft.entity.EntityRockFlat;
import com.theishiopian.foragecraft.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRockFlat extends RenderSnowball<EntityRockFlat>
{

    public static final Factory FACTORY = new Factory(); // Rev up those fryers


    public RenderRockFlat(RenderManager renderManagerIn, RenderItem itemRendererIn)
    {

        super(renderManagerIn, Item.getItemFromBlock(ModBlocks.rock_flat), itemRendererIn);
    }

    public static class Factory implements IRenderFactory<EntityRockFlat>
    {
        @Override
        public Render<? super EntityRockFlat> createRenderFor(RenderManager manager)
        {
            return new RenderRockFlat(manager, Minecraft.getMinecraft().getRenderItem());
        }
    }
}

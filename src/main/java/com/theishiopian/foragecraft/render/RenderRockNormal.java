package com.theishiopian.foragecraft.render;

import com.theishiopian.foragecraft.entity.EntityRockNormal;
import com.theishiopian.foragecraft.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRockNormal extends RenderSnowball<EntityRockNormal>
{

    public static final Factory FACTORY = new Factory();


    public RenderRockNormal(RenderManager renderManagerIn, RenderItem itemRendererIn)
    {

        super(renderManagerIn, Item.getItemFromBlock(ModBlocks.rock_normal), itemRendererIn);
    }

    public static class Factory implements IRenderFactory<EntityRockNormal>
    {
        @Override
        public Render<? super EntityRockNormal> createRenderFor(RenderManager manager)
        {
            return new RenderRockNormal(manager, Minecraft.getMinecraft().getRenderItem());
        }
    }
}

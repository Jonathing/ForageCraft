package com.theishiopian.foragecraft.render;

import com.theishiopian.foragecraft.ForageCraftMod;
import com.theishiopian.foragecraft.entity.EntityCrow;

import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCrow extends RenderLiving<EntityCrow>
{

	public static final Factory FACTORY = new Factory();
	
	public RenderCrow(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ModelParrot(), 0.3F);
	}

	 /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCrow entity)
    {
        return new ResourceLocation(ForageCraftMod.MODID+":textures/entity/crow.png");
    }
	
	 /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    public float handleRotationFloat(EntityCrow livingBase, float partialTicks)
    {
        return this.getCustomBob(livingBase, partialTicks);
    }

    private float getCustomBob(EntityCrow crow, float p_192861_2_)
    {
        float f = crow.oFlap + (crow.flap - crow.oFlap) * p_192861_2_;
        float f1 = crow.oFlapSpeed + (crow.flapSpeed - crow.oFlapSpeed) * p_192861_2_;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    public static class Factory implements IRenderFactory<EntityCrow>
	{
		@Override
		public Render<? super EntityCrow> createRenderFor(RenderManager manager)
		{
			return new RenderCrow(manager);
		}
	}
}

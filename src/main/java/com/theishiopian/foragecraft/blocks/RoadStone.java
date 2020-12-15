package com.theishiopian.foragecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

public class RoadStone extends Block
{

	public RoadStone()
	{
		super(Material.ROCK, MapColor.GRAY);
		this.setTranslationKey("road_stone");
		this.setRegistryName("road_stone");
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setSoundType(SoundType.STONE);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@ParametersAreNonnullByDefault
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        super.onEntityWalk(worldIn, pos, entityIn);
        
        entityIn.setVelocity(entityIn.motionX*=1.5, 0, entityIn.motionZ*=1.5);
    }
}

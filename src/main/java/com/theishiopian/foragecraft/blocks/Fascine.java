package com.theishiopian.foragecraft.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;

public class Fascine extends BlockRotatedPillar
{
	public Fascine()
	{
		super(Material.GRASS, MapColor.BROWN);
		
		setUnlocalizedName("fascine");
		setRegistryName("fascine");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
	}
}

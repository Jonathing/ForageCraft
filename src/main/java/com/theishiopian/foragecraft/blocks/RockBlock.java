package com.theishiopian.foragecraft.blocks;

import javax.annotation.Nullable;

import com.theishiopian.foragecraft.init.ModBlocks;
import com.theishiopian.foragecraft.init.ModBlocks.RockType;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RockBlock extends BlockFalling
{
	RockType type;

	public RockBlock(RockType t)
	{
		super(Material.ROCK);

		String name = null;

		switch(t)
		{
			case NORMAL: name = "rock_normal";
			break;
			case FLAT: name = "rock_flat";
			break;
		}
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.DECORATIONS);
		setSoundType(SoundType.STONE);
		type = t;
	}

	public boolean isTopSolid(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return null;
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return this.canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		IBlockState stateDown = worldIn.getBlockState(pos.down());

		if (stateDown.isTopSolid() && state.getBlock().equals(Blocks.AIR))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/*
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		System.out.println("crack cocaine");
		if(neighbor == pos.down())
		{
			((World) world).setBlockToAir(pos);
		}
	}
	*/

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		//Moved stuff to removedByPlayer
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (willHarvest)
			return true;
		if(!world.isRemote)
		{
			ItemStack stack = null;

			switch(type)
			{
				case FLAT: stack  = new ItemStack(Item.getItemFromBlock(ModBlocks.rock_flat));
					break;
				case NORMAL: stack  = new ItemStack(Item.getItemFromBlock(ModBlocks.rock_normal));
					break;
			}

			if(!player.capabilities.isCreativeMode)
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
		{
			ItemStack stack = null;

			switch(type)
			{
				case FLAT: stack  = new ItemStack(Item.getItemFromBlock(ModBlocks.rock_flat));
					break;
				case NORMAL: stack  = new ItemStack(Item.getItemFromBlock(ModBlocks.rock_normal));
					break;
			}

			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
			worldIn.setBlockToAir(pos);
		}

		return false;
	}

	 /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
	@Override
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }
}

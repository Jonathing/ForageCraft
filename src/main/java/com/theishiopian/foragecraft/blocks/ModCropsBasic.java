package com.theishiopian.foragecraft.blocks;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//extend, don't instantiate
public abstract class ModCropsBasic extends BlockCrops
{
	// creates a basic, vanilla style crop block, similar to wheat or potatoes.
	// Currently used for leeks
	public ModCropsBasic()
	{
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
		this.setTickRandomly(true);
		this.setCreativeTab((CreativeTabs) null); //sure, why not?
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.disableStats();//???
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	//shouldnt need to modify this too much, hopefully. If that ends up not being the case, I'll add a probability modifier to the constructor
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> dropList = new java.util.ArrayList<ItemStack>();

		if(this.getAge(state)<7)dropList.add(new ItemStack(this.getSeed()));
		else
		{
			Random rand = new Random();
			dropList.add(new ItemStack(this.getCrop(), rand.nextInt(2)+1));
			dropList.add(new ItemStack(this.getSeed(), rand.nextInt(1)+1));
		}
		
		return dropList;
	}
}

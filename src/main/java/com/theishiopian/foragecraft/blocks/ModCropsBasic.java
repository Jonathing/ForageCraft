package com.theishiopian.foragecraft.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

//extend, don't instantiate. Instantiating causes crops to drop air blocks. Not ideal.
public abstract class ModCropsBasic extends BlockCrops
{
	// creates a basic, vanilla style crop block, similar to wheat or potatoes.
	// Currently used for leeks
	public ModCropsBasic()
	{
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
		this.setTickRandomly(true);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.disableStats();
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	//these three methods are important:
	
	@Override
	public abstract int getMaxAge();
   
	@Override
	@Nonnull
	public abstract Item getSeed();

	@Override
	@Nonnull
	public abstract Item getCrop();
	
	//don't forget to override these things and return shit

	//shouldn't need to modify this too much, hopefully. If that ends up not being the case, I'll add a probability modifier to the constructor
	@Override
	@Nonnull
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
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

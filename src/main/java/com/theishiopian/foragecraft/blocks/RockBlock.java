package com.theishiopian.foragecraft.blocks;

import com.theishiopian.foragecraft.init.ModBlocks;
import com.theishiopian.foragecraft.init.ModBlocks.RockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class RockBlock extends BlockFalling
{
    RockType type;

//	private static AxisAlignedBB bounds_normal = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.25D, 0.7D);
//	private static AxisAlignedBB bounds_flat= new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.125D, 0.8D);

    @Nonnull
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        //a compromise between realism and practicality
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
    }

    public RockBlock(RockType type)
    {
        super(Material.ROCK);

        String name;

        switch (type)
        {
            case NORMAL:
                name = "rock_normal";
                break;
            case FLAT:
                name = "rock_flat";
                break;
            default:
                throw new IllegalStateException(String.format("Unable to register a ForageCraft rock of type %s!", type));
        }

        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setSoundType(SoundType.STONE);
        this.type = type;
    }

    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
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
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return this.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        IBlockState stateDown = worldIn.getBlockState(pos.down());

        return stateDown.isSideSolid(worldIn, pos, EnumFacing.DOWN) && state.getBlock().equals(Blocks.AIR);
    }

    //whats the difference between this and blockDestryoedByPlayer?
    //this method allowed me to add so that creative mode players can't get the block
    @Override
    @ParametersAreNonnullByDefault
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest)
            return true;

        if (!world.isRemote)
        {
            if (!player.capabilities.isCreativeMode)
                world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), getItemStackFromType()));
        }

        return super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), getItemStackFromType()));
            worldIn.setBlockToAir(pos);
        }

        return false;
    }

    private ItemStack getItemStackFromType()
    {
        switch (this.type)
        {
            case FLAT:
                return new ItemStack(Item.getItemFromBlock(ModBlocks.rock_flat));
            case NORMAL:
                return new ItemStack(Item.getItemFromBlock(ModBlocks.rock_normal));
            default:
                throw new IllegalStateException(String.format("Unable to get a ForageCraft rock of type %s!", this.type));
        }
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }
}

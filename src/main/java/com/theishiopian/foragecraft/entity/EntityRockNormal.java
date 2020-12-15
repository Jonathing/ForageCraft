package com.theishiopian.foragecraft.entity;

import com.theishiopian.foragecraft.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

public class EntityRockNormal extends EntityThrowable
{
    public EntityRockNormal(World worldIn)
    {
        super(worldIn);
    }

    public EntityRockNormal(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityRockNormal(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 3)
        {
            for (int i = 0; i < 8; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, 0.0D, 0.0D,
                        0.0D, Block.getIdFromBlock(Blocks.STONE));
            }
        }
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (result.typeOfHit == Type.BLOCK)
            {
                if (world.getBlockState(result.getBlockPos()).getCollisionBoundingBox(world,
                        result.getBlockPos()) != null)
                {
                    Material impactMaterial = world.getBlockState(result.getBlockPos()).getBlock()
                            .getMaterial(world.getBlockState(result.getBlockPos()));
                    Block impactBlock = world.getBlockState(result.getBlockPos()).getBlock();

                    if (impactMaterial == Material.GLASS)
                    {
                        this.world.setBlockToAir(result.getBlockPos());
                        this.world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_GLASS_BREAK,
                                SoundCategory.HOSTILE, 1, 1);
                    }
                    else
                    {
                        this.world.playSound(null, posX, posY, posZ, impactBlock.getSoundType().getBreakSound(),
                                SoundCategory.HOSTILE, 1, 1);

                        ItemStack refund = new ItemStack(Item.getItemFromBlock(ModBlocks.rock_normal));

                        world.spawnEntity(new EntityItem(world, this.posX, this.posY, this.posZ, refund));

                        setDead();
                    }
                }
            }
            else if (result.entityHit != null)
            {
                int damage = 3;
                ItemStack refund = new ItemStack(Item.getItemFromBlock(ModBlocks.rock_normal));

                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),
                        (float) damage);

                setDead();
                world.spawnEntity(new EntityItem(world, this.posX, this.posY, this.posZ, refund));
            }
        }
    }
}

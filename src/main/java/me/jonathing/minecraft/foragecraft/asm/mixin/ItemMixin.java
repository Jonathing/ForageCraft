package me.jonathing.minecraft.foragecraft.asm.mixin;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin class is used to make certain modifications to the {@link Item} class to ForageCraft's needs.
 *
 * @author Jonathing
 * @see Item
 * @since 2.0.1
 */
@Mixin(Item.class)
public class ItemMixin
{
    /**
     * The ForageCraft {@link ForageBlocks#stick} is a very special case. It does not have its own {@link BlockItem}
     * since we use the Minecraft {@link Items#STICK} to place it down in the world. This has led to issues in
     * ForageCraft prior to version {@code 2.0.1} where right-clicking with the Minecraft stick in hand in certain
     * situations.
     * <p>
     * The solution: Mixins!
     * <p>
     * What I have done here is essentually copy-pasted a bunch of vanilla code that gives block items their
     * functionality, and I have tweaked it slightly to fit the ForageCraft's stick's needs. To summarize, this mixin
     * fools Minecraft into believing that {@link Items#STICK} has a {@link BlockItem}, which in this case is
     * {@link ForageBlocks#stick}.
     *
     * @param itemUseContext The item context in which the player right clicks any block with an item in hand.
     * @param callback Returns the resulting {@link ActionResultType} of the action.
     */
    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/item/Item;onItemUse(Lnet/minecraft/item/ItemUseContext;)Lnet/minecraft/util/ActionResultType;", cancellable = true)
    public void onItemUse(ItemUseContext itemUseContext, CallbackInfoReturnable<ActionResultType> callback)
    {
        if (itemUseContext.getItem().getItem().equals(Items.STICK))
        {
            BlockItemUseContext useContext = new BlockItemUseContext(itemUseContext);

            if (!canPlace(useContext))
            {
                callback.setReturnValue(ActionResultType.FAIL);
            }
            else
            {
                BlockState blockstate = ForageBlocks.stick.getDefaultState();
                if (!useContext.getWorld().setBlockState(useContext.getPos(), blockstate, 11))
                {
                    callback.setReturnValue(ActionResultType.FAIL);
                }
                else
                {
                    BlockPos blockpos = useContext.getPos();
                    World world = useContext.getWorld();
                    PlayerEntity playerentity = useContext.getPlayer();
                    ItemStack itemstack = useContext.getItem();
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    if (block == blockstate.getBlock())
                    {
                        blockstate1 = this.func_219985_a(blockpos, world, itemstack, blockstate1);
                        BlockItem.setTileEntityNBT(world, playerentity, blockpos, itemstack);
                        block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                        if (playerentity instanceof ServerPlayerEntity)
                        {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(world, blockpos, useContext.getPlayer());
                    world.playSound(playerentity, blockpos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (playerentity == null || !playerentity.abilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }

                    callback.setReturnValue(ActionResultType.success(world.isRemote));
                }
            }
        }
    }

    /**
     * Checks with the {@link BlockItemUseContext#canPlace()} method along with some extra conditions for the
     * {@link ForageBlocks#stick} in particular.
     *
     * @return The result of the check.
     */
    private static boolean canPlace(BlockItemUseContext useContext)
    {
        World world = useContext.getWorld();
        BlockPos pos = useContext.getPos();
        return world.getBlockState(pos.down()).isSolid()
                && world.getBlockState(pos).getBlock() instanceof AirBlock
                && useContext.canPlace();
    }

    /**
     * Copy-pasted vanilla code from the {@link BlockItem} class.
     *
     * @see BlockItem#func_219985_a(BlockPos, World, ItemStack, BlockState)
     */
    private BlockState func_219985_a(BlockPos blockPos, World world, ItemStack itemStack, BlockState blockState)
    {
        BlockState blockstate = blockState;
        CompoundNBT compoundnbt = itemStack.getTag();
        if (compoundnbt != null)
        {
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
            StateContainer<Block, BlockState> statecontainer = blockState.getBlock().getStateContainer();

            for (String s : compoundnbt1.keySet())
            {
                Property<?> property = statecontainer.getProperty(s);
                if (property != null)
                {
                    String s1 = compoundnbt1.get(s).getString();
                    blockstate = func_219988_a(blockstate, property, s1);
                }
            }
        }

        if (blockstate != blockState)
        {
            world.setBlockState(blockPos, blockstate, 2);
        }

        return blockstate;
    }

    /**
     * Copy-pasted vanilla code from the {@link BlockItem} class.
     *
     * @see BlockItem#func_219988_a(BlockState, Property, String)
     */
    private static <T extends Comparable<T>> BlockState func_219988_a(BlockState blockState, Property<T> property, String s)
    {
        return property.parseValue(s).map((p_219986_2_) -> blockState.with(property, p_219986_2_)).orElse(blockState);
    }
}

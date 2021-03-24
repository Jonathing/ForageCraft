package me.jonathing.minecraft.foragecraft.asm.mixin.main;

import me.jonathing.minecraft.foragecraft.common.block.StickBlock;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
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
 * @since 2.1.0
 */
@Mixin(Item.class)
public class ItemMixin
{
    /**
     * The ForageCraft {@link ForageBlocks#stick} is a very special case. It does not have its own {@link BlockItem}
     * since we use the Minecraft {@link Items#STICK} to place it down in the world. This has led to issues in
     * ForageCraft prior to version {@code 2.1.0} where right-clicking with the Minecraft stick in hand in certain
     * situations.
     * <p>
     * The solution: Mixins!
     * <p>
     * What I have done here is essentially copy-pasted a bunch of vanilla code that gives block items their
     * functionality, and I have tweaked it slightly to fit the ForageCraft's stick's needs. To summarize, this mixin
     * hooks into the {@link org.spongepowered.asm.mixin.injection.points.MethodHead} of the
     * {@link Item#useOn(ItemUseContext)} method to fool Minecraft into believing that {@link Items#STICK} has a
     * {@link BlockItem}, which in this case is the {@link ForageBlocks#stick}.
     *
     * @param itemUseContext The item context in which the player right clicks any block with an item in hand.
     * @param callback       Mixin's way of returning the resulting {@link ActionResultType} of the action.
     * @see Item#useOn(ItemUseContext)
     */
    @Inject(at = @At("HEAD"), method = "useOn(Lnet/minecraft/item/ItemUseContext;)Lnet/minecraft/util/ActionResultType;", cancellable = true)
    public void useOn(ItemUseContext itemUseContext, CallbackInfoReturnable<ActionResultType> callback)
    {
        if (itemUseContext.getItemInHand().getItem().equals(Items.STICK))
        {
            BlockItemUseContext useContext = new BlockItemUseContext(itemUseContext);

            if (!canPlace(useContext))
            {
                callback.setReturnValue(ActionResultType.FAIL);
            }
            else
            {
                BlockState blockState = ForageBlocks.stick.getStateForPlacement(useContext);
                blockState = blockState == null ? ((StickBlock) ForageBlocks.stick).getStateWithRandomDirection() : blockState;
                if (!useContext.getLevel().setBlock(useContext.getClickedPos(), blockState, 11))
                {
                    callback.setReturnValue(ActionResultType.FAIL);
                }
                else
                {
                    BlockPos pos = useContext.getClickedPos();
                    World world = useContext.getLevel();
                    PlayerEntity player = useContext.getPlayer();
                    ItemStack itemStack = useContext.getItemInHand();
                    BlockState blockStateInWorld = world.getBlockState(pos);
                    Block block = blockStateInWorld.getBlock();
                    if (block == blockState.getBlock())
                    {
                        blockStateInWorld = ((BlockItem) (Object) this).updateBlockStateFromTag(pos, world, itemStack, blockStateInWorld);
                        BlockItem.updateCustomBlockEntityTag(world, player, pos, itemStack);
                        block.setPlacedBy(world, pos, blockStateInWorld, player, itemStack);
                        if (player instanceof ServerPlayerEntity)
                        {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, itemStack);
                        }
                    }

                    SoundType soundtype = blockStateInWorld.getSoundType(world, pos, useContext.getPlayer());
                    world.playSound(player, pos, SoundEvents.WOOD_PLACE, SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (player == null || !player.abilities.instabuild)
                    {
                        itemStack.shrink(1);
                    }

                    callback.setReturnValue(ActionResultType.sidedSuccess(world.isClientSide));
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
        World world = useContext.getLevel();
        BlockPos pos = useContext.getClickedPos();
        return world.getBlockState(pos.below()).canOcclude()
                && (world.getBlockState(pos).getBlock() instanceof AirBlock
                || world.getBlockState(pos).getFluidState().equals(Fluids.WATER.getSource(false)))
                && useContext.canPlace();
    }
}

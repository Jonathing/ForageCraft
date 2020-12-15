package me.jonathing.minecraft.foragecraft.asm.mixin;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

/**
 * This mixin class is used to make certain modifications to the {@link Entity} class to ForageCraft's needs.
 *
 * @author Jonathing
 * @see Entity
 * @since 2.0.0
 */
@Mixin(Entity.class)
public abstract class EntityMixin
{
    /**
     * @see Entity#entityDropItem(IItemProvider, int)
     */
    @Shadow
    @Nullable
    public ItemEntity entityDropItem(IItemProvider p_199702_1_, int p_199702_2_)
    {
        throw new IllegalStateException("Mixin was unable to shadow method entityDropItem(IItemProvider, int)");
    }

    /**
     * This method injects into the {@link Entity#entityDropItem(IItemProvider)} method and tells the game that if an
     * {@link IItemProvider} ever wants to drop a {@link ForageBlocks#stick} to stop what its doing and to drop a
     * {@link Items#STICK} instead.
     *
     * @see Entity#entityDropItem(IItemProvider)
     */
    @Inject(at = @At("HEAD"), method = "entityDropItem(Lnet/minecraft/util/IItemProvider;)Lnet/minecraft/entity/item/ItemEntity;", cancellable = true)
    private void entityDropItem(IItemProvider itemProvider, CallbackInfoReturnable<ItemEntity> callback)
    {
        if (itemProvider.asItem().equals(ForageBlocks.stick.asItem()))
            callback.setReturnValue(this.entityDropItem(Items.STICK, 0));
    }
}

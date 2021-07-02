package me.jonathing.minecraft.foragecraft.mixin;

import me.jonathing.minecraft.foragecraft.common.block.template.DecorativeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.IItemProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

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
     * This method hooks into the {@link org.spongepowered.asm.mixin.injection.points.BeforeInvoke} call of the
     * {@link Entity#spawnAtLocation(IItemProvider, int)} method in the {@link Entity#spawnAtLocation(IItemProvider)} to
     * modify the {@link IItemProvider} argument of the method. If the argument is an instance of a
     * {@link DecorativeBlock}, the argument to be passed will instead be the value returned by
     * {@link DecorativeBlock#getDecorativeItem()}.
     *
     * @see Entity#spawnAtLocation(IItemProvider)
     * @see Entity#spawnAtLocation(IItemProvider, int)
     */
    @ModifyArg(
            method = "spawnAtLocation(Lnet/minecraft/util/IItemProvider;)Lnet/minecraft/entity/item/ItemEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/entity/Entity.spawnAtLocation(Lnet/minecraft/util/IItemProvider;I)Lnet/minecraft/entity/item/ItemEntity;"
            )
    )
    private IItemProvider modify$p_199702_1_(IItemProvider p_199702_1_)
    {
        if (p_199702_1_ instanceof DecorativeBlock)
            return ((DecorativeBlock) p_199702_1_).getDecorativeItem();

        return p_199702_1_;
    }
}

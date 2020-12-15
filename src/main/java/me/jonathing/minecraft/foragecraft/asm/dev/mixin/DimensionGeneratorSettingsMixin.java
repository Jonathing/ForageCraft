package me.jonathing.minecraft.foragecraft.asm.dev.mixin;

import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin class is used to disable the "experimental world settings" screen whenever a new world is created.
 * <p>
 * <strong>This mixin is only enabled in a development environment and will never be used in game!</strong>
 *
 * @author Jonathing
 * @see DimensionGeneratorSettings
 * @since 2.0.0
 */
@Mixin(DimensionGeneratorSettings.class)
public class DimensionGeneratorSettingsMixin
{
    /**
     * Tells the game to skip the "experimental world settings" screen.
     *
     * @see DimensionGeneratorSettings#method_28611()
     */
    @Inject(at = @At("HEAD"), method = "method_28611()Z", cancellable = true)
    private void onCheckExperimental(CallbackInfoReturnable<Boolean> info)
    {
        info.setReturnValue(true);
    }
}

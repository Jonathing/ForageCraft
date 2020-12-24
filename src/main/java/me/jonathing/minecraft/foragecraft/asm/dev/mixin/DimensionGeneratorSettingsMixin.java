package me.jonathing.minecraft.foragecraft.asm.dev.mixin;

import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(DimensionGeneratorSettings.class);

    /**
     * This method hooks into the {@link org.spongepowered.asm.mixin.injection.points.MethodHead} of the
     * {@link DimensionGeneratorSettings#func_236234_o_()} method to tell the game to skip the "experimental world
     * settings" screen.
     *
     * @see DimensionGeneratorSettings#func_236234_o_()
     */
    @Inject(at = @At("HEAD"), method = "func_236234_o_()Z", cancellable = true)
    private void onCheckExperimental(CallbackInfoReturnable<Boolean> info)
    {
        LOGGER.debug("Skipping over the experimental settings screen since ForageCraft's development mixins have been enabled.");
        info.setReturnValue(true);
    }
}

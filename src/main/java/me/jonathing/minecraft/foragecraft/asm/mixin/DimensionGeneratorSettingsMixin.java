package me.jonathing.minecraft.foragecraft.asm.mixin;

import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionGeneratorSettings.class)
public class DimensionGeneratorSettingsMixin
{
    @Inject(at = @At("HEAD"), method = "method_28611()Z", cancellable = true)
    private void onCheckExperimental(CallbackInfoReturnable<Boolean> info)
    {
        info.setReturnValue(true);
    }
}

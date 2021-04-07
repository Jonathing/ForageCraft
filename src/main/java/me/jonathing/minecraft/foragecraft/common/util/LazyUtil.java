package me.jonathing.minecraft.foragecraft.common.util;

import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public final class LazyUtil
{
    @Nonnull
    public static <T> LazyOptional<T> LazyOptionalOf(final @Nullable Supplier<T> instanceSupplier)
    {
        return instanceSupplier == null ? LazyOptional.empty() : LazyOptional.of(instanceSupplier::get);
    }
}

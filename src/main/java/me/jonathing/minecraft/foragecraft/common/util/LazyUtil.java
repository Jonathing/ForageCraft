package me.jonathing.minecraft.foragecraft.common.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
public final class LazyUtil
{
    public static <T> LazyOptional<T> LazyOptionalOf(final @Nullable Supplier<T> instanceSupplier)
    {
        return instanceSupplier == null ? LazyOptional.empty() : LazyOptional.of(instanceSupplier::get);
    }

    public static <T> Lazy<T> supplierToLazy(final @Nonnull Supplier<T> instanceSupplier)
    {
        return Lazy.of(instanceSupplier);
    }

    public static <T> NonNullLazy<T> supplierToLazy(final @Nonnull NonNullSupplier<T> instanceSupplier)
    {
        return NonNullLazy.of(instanceSupplier);
    }
}

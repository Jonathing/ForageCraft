package me.jonathing.minecraft.foragecraft.common.capability;

import me.jonathing.minecraft.foragecraft.common.registry.ForageCapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The forage chunk capability adds to each chunk the number of times its been foraged from. This way, a limit can be
 * set on each chunk to incentivise exploration.
 *
 * @author Jonathing
 * @see ForageCapabilities#chunk
 * @since 2.3.0
 */
public class ForageChunk implements INBTSerializable<CompoundNBT>
{
    private int timesForaged = 0;

    public void forage()
    {
        this.timesForaged++;
    }

    public int getTimesForaged()
    {
        return this.timesForaged;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("timesForaged", this.timesForaged);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        this.timesForaged = nbt.getInt("timesForaged");
    }

    @SuppressWarnings("unchecked")
    public static <E extends INBTSerializable<CompoundNBT>> ICapabilitySerializable<CompoundNBT> serializeableProvider(E defaultInstance)
    {
        return new ICapabilitySerializable<CompoundNBT>()
        {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
            {
                if (cap == ForageCapabilities.chunk) return LazyOptional.of(() -> (T) defaultInstance);
                return LazyOptional.empty();
            }

            @Override
            public CompoundNBT serializeNBT()
            {
                return defaultInstance.serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt)
            {
                defaultInstance.deserializeNBT(nbt);
            }
        };
    }
}

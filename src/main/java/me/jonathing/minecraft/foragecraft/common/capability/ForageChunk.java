package me.jonathing.minecraft.foragecraft.common.capability;

import me.jonathing.minecraft.foragecraft.common.capability.base.IForageChunk;
import me.jonathing.minecraft.foragecraft.common.registry.ForageCapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The forage chunk capability adds to each chunk the number of times its been foraged from. This way, a limit can be
 * set on each chunk to incentivise exploration.
 *
 * @author Jonathing
 * @see ForageCapabilities#CHUNK
 * @since 2.2.2
 */
public class ForageChunk implements IForageChunk
{
    @CapabilityInject(IForageChunk.class)
    public static Capability<IForageChunk> INSTANCE = null;

    @Override
    public Capability<IForageChunk> getDefaultInstance()
    {
        return INSTANCE;
    }

    private int timesForaged = 0;

    @Override
    public void forage()
    {
        this.timesForaged++;
    }

    @Override
    public int getTimesForaged()
    {
        return this.timesForaged;
    }

    @Override
    public void setTimesForaged(int timesForaged)
    {
        this.timesForaged = timesForaged;
    }

    @Override
    @Nonnull
    public CompoundNBT writeAdditional(CompoundNBT nbt)
    {
        nbt.putInt("timesForaged", this.timesForaged);
        return nbt;
    }

    @Override
    public void read(@Nonnull CompoundNBT nbt)
    {
        this.timesForaged = nbt.getInt("timesForaged");
    }
}

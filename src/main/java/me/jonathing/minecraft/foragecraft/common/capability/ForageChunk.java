package me.jonathing.minecraft.foragecraft.common.capability;

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
 * @since 2.3.0
 */
public class ForageChunk implements IForageChunk
{
    @CapabilityInject(IForageChunk.class)
    public static Capability<IForageChunk> INSTANCE = null;

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

    public static Capability.IStorage<IForageChunk> storage()
    {
        return new Capability.IStorage<IForageChunk>()
        {
            @Nullable
            @Override
            public INBT writeNBT(Capability<IForageChunk> capability, IForageChunk instance, Direction side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IForageChunk> capability, IForageChunk instance, Direction side, INBT nbt)
            {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        };
    }

    public static ICapabilitySerializable<CompoundNBT> provider(IForageChunk instance)
    {
        return new ICapabilitySerializable<CompoundNBT>()
        {
            private final LazyOptional<IForageChunk> chunkHandler = LazyOptional.of(() -> instance);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
            {
                return cap == ForageChunk.INSTANCE ? this.chunkHandler.cast() : LazyOptional.empty();
            }

            @Override
            public CompoundNBT serializeNBT()
            {
                return this.chunkHandler.orElseThrow(NullPointerException::new).serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt)
            {
                this.chunkHandler.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
            }
        };
    }
}

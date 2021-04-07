package me.jonathing.minecraft.foragecraft.common.capability;

import me.jonathing.minecraft.foragecraft.common.registry.ForageCapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
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
    @CapabilityInject(ForageChunk.class)
    public static Capability<ForageChunk> INSTANCE = null;

    private int timesForaged = 0;

    public void forage()
    {
        this.timesForaged++;
    }

    public int getTimesForaged()
    {
        return this.timesForaged;
    }

    public static Storage storage()
    {
        return new Storage();
    }

    public static Provider provide(ForageChunk instance)
    {
        return new Provider(instance);
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

    public static class Storage implements Capability.IStorage<ForageChunk>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<ForageChunk> capability, ForageChunk instance, Direction side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<ForageChunk> capability, ForageChunk instance, Direction side, INBT nbt)
        {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT>
    {
        private final LazyOptional<ForageChunk> chunkHandler;

        public Provider(ForageChunk forageChunk)
        {
            this.chunkHandler = LazyOptional.of(() -> forageChunk);
        }

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
    }
}

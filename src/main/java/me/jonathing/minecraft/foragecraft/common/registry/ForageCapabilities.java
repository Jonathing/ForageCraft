package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.capability.ForageChunk;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ForageCapabilities
{
    public static final ResourceLocation CHUNK_KEY = ForageCraft.locate("foraged_chunk");

    @CapabilityInject(ForageChunk.class)
    public static Capability<ForageChunk> CHUNK = null;

    public static void init(IEventBus eventBus)
    {
        CapabilityManager.INSTANCE.register(ForageChunk.class, serializeableStorage(), ForageChunk::new);
        eventBus.addGenericListener(Chunk.class, ForageCapabilities::attachChunkCapability);
    }

    private static void attachChunkCapability(AttachCapabilitiesEvent<Chunk> event)
    {
        event.addCapability(CHUNK_KEY, serializeableProvider(CHUNK.getDefaultInstance()));
    }

    private static <E extends INBTSerializable<CompoundNBT>> ICapabilitySerializable<CompoundNBT> serializeableProvider(E defaultInstance) {
        return new ICapabilitySerializable<CompoundNBT>() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CHUNK) {
                    return LazyOptional.of(() -> (T) defaultInstance);
                }
                return LazyOptional.empty();
            }

            @Override
            public CompoundNBT serializeNBT() {
                return defaultInstance.serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                defaultInstance.deserializeNBT(nbt);
            }
        };
    }

    private static <T extends INBTSerializable<CompoundNBT>> Capability.IStorage<T> serializeableStorage() {
        return new Capability.IStorage<T>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        };
    }
}

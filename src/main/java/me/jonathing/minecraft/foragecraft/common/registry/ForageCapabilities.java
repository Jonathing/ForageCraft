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
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import javax.annotation.Nullable;

/**
 * This class holds all of the capabilities in ForageCraft.
 *
 * @author Jonathing
 * @see #init(IEventBus)
 * @since 2.3.0
 */
public class ForageCapabilities
{
    public static final ResourceLocation CHUNK_KEY = ForageCraft.locate("foraged_chunk");

    @CapabilityInject(ForageChunk.class)
    public static Capability<ForageChunk> chunk = null;

    public static void init(IEventBus eventBus)
    {
        CapabilityManager.INSTANCE.register(ForageChunk.class, serializeableStorage(), ForageChunk::new);
        eventBus.addGenericListener(Chunk.class, ForageCapabilities::attachChunkCapability);
    }

    private static void attachChunkCapability(AttachCapabilitiesEvent<Chunk> event)
    {
        event.addCapability(CHUNK_KEY, ForageChunk.serializeableProvider(chunk.getDefaultInstance()));
    }

    private static <T extends INBTSerializable<CompoundNBT>> Capability.IStorage<T> serializeableStorage()
    {
        return new Capability.IStorage<T>()
        {
            @Nullable
            @Override
            public INBT writeNBT(Capability<T> capability, T instance, Direction side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt)
            {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        };
    }
}

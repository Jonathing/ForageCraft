package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.capability.ForageChunk;
import me.jonathing.minecraft.foragecraft.common.capability.base.IForageChunk;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * This class holds all of the capabilities in ForageCraft.
 *
 * @author Jonathing
 * @see #init()
 * @since 2.2.2
 */
public class ForageCapabilities
{
    @CapabilityInject(IForageChunk.class)
    public static Capability<IForageChunk> CHUNK = null;

    public static void init()
    {
        register(IForageChunk.class, ForageChunk::storage, ForageChunk::new);
    }

    private static <T> void register(Class<T> type, Supplier<Capability.IStorage<T>> storage, Callable<? extends T> factory)
    {
        CapabilityManager.INSTANCE.register(type, storage.get(), factory);
    }

    static void onAttachChunkCapability(AttachCapabilitiesEvent<Chunk> event)
    {
        event.addCapability(ForageCraft.locate("foraged_chunk"), ForageChunk.provider(CHUNK.getDefaultInstance()));
    }
}

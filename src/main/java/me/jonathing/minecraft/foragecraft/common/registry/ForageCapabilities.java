package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.capability.ForageChunk;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * This class holds all of the capabilities in ForageCraft.
 *
 * @author Jonathing
 * @see #init(IEventBus)
 * @since 2.3.0
 */
public class ForageCapabilities
{
    @CapabilityInject(ForageChunk.class)
    public static Capability<ForageChunk> chunk = null;

    public static void init(IEventBus eventBus)
    {
        register(ForageChunk.class, ForageChunk::storage, ForageChunk::new);
        eventBus.addGenericListener(Chunk.class, ForageCapabilities::onAttachChunkCapability);
    }

    private static <T> void register(Class<T> type, Supplier<Capability.IStorage<T>> storage, Callable<? extends T> factory)
    {
        CapabilityManager.INSTANCE.register(type, storage.get(), factory);
    }

    private static void onAttachChunkCapability(AttachCapabilitiesEvent<Chunk> event)
    {
        event.addCapability(ForageCraft.locate("foraged_chunk"), ForageChunk.provide(chunk.getDefaultInstance()));
    }
}

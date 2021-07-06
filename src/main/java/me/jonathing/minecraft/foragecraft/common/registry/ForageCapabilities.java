package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.capability.ForageChunk;
import me.jonathing.minecraft.foragecraft.common.capability.base.IForageChunk;
import me.jonathing.minecraft.foragecraft.common.capability.util.CapabilityProvider;
import me.jonathing.minecraft.foragecraft.common.capability.util.CapabilityStorage;
import me.jonathing.minecraft.foragecraft.common.capability.util.IPersistentCapability;
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
        CapabilityManager.INSTANCE.register(IForageChunk.class, new CapabilityStorage<>(), ForageChunk::new);
    }

    private static <T extends IPersistentCapability<T>> void register(Class<T> clazz, Callable<? extends T> factory)
    {
        CapabilityManager.INSTANCE.register(clazz, new CapabilityStorage<>(), factory);
    }

    static void onAttachChunkCapability(AttachCapabilitiesEvent<Chunk> event)
    {
        event.addCapability(ForageCraft.locate("foraged_chunk"), new CapabilityProvider<>(CHUNK.getDefaultInstance()));
    }
}

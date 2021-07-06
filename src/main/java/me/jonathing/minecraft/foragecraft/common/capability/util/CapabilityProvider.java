package me.jonathing.minecraft.foragecraft.common.capability.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class CapabilityProvider<C extends IPersistentCapability<C>> implements ICapabilitySerializable<CompoundNBT>
{
	private final LazyOptional<C> capabilityHandler;
	private final Lazy<Capability<C>> instance;

	public CapabilityProvider(C capability)
	{
		this.capabilityHandler = LazyOptional.of(() -> capability);
		this.instance = capability::getDefaultInstance;
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return cap == this.instance.get() ? this.capabilityHandler.cast() : LazyOptional.empty();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		this.capabilityHandler.orElse(null).read(nbt);
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		return this.capabilityHandler.orElse(null).writeAdditional();
	}
}

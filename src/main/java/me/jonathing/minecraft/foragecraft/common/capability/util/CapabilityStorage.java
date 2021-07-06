package me.jonathing.minecraft.foragecraft.common.capability.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityStorage<C extends IPersistentCapability<C>> implements IStorage<C>
{
	@Override
	public INBT writeNBT(Capability<C> capability, C instance, Direction side)
	{
		CompoundNBT compound = new CompoundNBT();
		instance.writeAdditional(compound);
		return compound;
	}

	@Override
	public void readNBT(Capability<C> capability, C instance, Direction side, INBT nbt)
	{
		CompoundNBT compound = (CompoundNBT) nbt;
		instance.read(compound);
	}
}

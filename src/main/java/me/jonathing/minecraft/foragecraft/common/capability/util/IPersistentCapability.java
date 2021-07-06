package me.jonathing.minecraft.foragecraft.common.capability.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;

public interface IPersistentCapability<C>
{
	Capability<C> getDefaultInstance();

	CompoundNBT writeAdditional(CompoundNBT nbt);

	default CompoundNBT writeAdditional()
	{
		CompoundNBT nbt = new CompoundNBT();
		return this.writeAdditional(nbt);
	}

	void read(CompoundNBT nbt);
}

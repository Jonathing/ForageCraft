package me.jonathing.minecraft.foragecraft.common.capability.base;

import me.jonathing.minecraft.foragecraft.common.capability.util.IPersistentCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public interface IForageChunk extends IPersistentCapability<IForageChunk>
{
    void forage();

    int getTimesForaged();

    void setTimesForaged(int timesForaged);
}

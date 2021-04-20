package me.jonathing.minecraft.foragecraft.common.capability.base;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public interface IForageChunk extends INBTSerializable<CompoundNBT>
{
    void forage();

    int getTimesForaged();

    void setTimesForaged(int timesForaged);

    @Nonnull
    default CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("timesForaged", this.getTimesForaged());
        return nbt;
    }

    default void deserializeNBT(@Nonnull CompoundNBT nbt)
    {
        this.setTimesForaged(nbt.getInt("timesForaged"));
    }
}

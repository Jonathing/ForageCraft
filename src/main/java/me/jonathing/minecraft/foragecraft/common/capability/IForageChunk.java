package me.jonathing.minecraft.foragecraft.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IForageChunk extends INBTSerializable<CompoundNBT>
{
    void forage();

    int getTimesForaged();

    void setTimesForaged(int timesForaged);

    default CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("timesForaged", this.getTimesForaged());
        return nbt;
    }

    default void deserializeNBT(CompoundNBT nbt)
    {
        this.setTimesForaged(nbt.getInt("timesForaged"));
    }
}

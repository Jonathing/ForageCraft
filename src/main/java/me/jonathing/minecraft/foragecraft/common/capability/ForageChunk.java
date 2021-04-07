package me.jonathing.minecraft.foragecraft.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ForageChunk implements INBTSerializable<CompoundNBT>
{
    private int timesForaged = 0;

    public void forage() {
        this.timesForaged++;
        System.out.println("CHUNK FORAGED " + this.timesForaged + " TIMES");
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("timesForaged", this.timesForaged);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        this.timesForaged = nbt.getInt("timesForaged");
    }
}

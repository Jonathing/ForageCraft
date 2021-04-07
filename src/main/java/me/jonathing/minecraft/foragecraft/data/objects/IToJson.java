package me.jonathing.minecraft.foragecraft.data.objects;

import com.google.gson.JsonObject;

public interface IToJson<T extends IToJson<T>>
{
    JsonObject toJson();
}

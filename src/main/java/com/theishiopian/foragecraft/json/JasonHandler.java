package com.theishiopian.foragecraft.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.*;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class JasonHandler
{
	
	/**
	 * This turns a json file at a resourcelocation into a foragetable object
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public static ForageTable buildTableFromJSON(ResourceLocation location) throws IOException
	{
		GsonBuilder builder = new GsonBuilder();//makes a builder
		builder.setPrettyPrinting();//pretty
		Gson gson = builder.create();//makes a gson, which is basically a java to json translator
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();//read file from resourcelocation
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));//make a guy to read said file
		ForageTable table = gson.fromJson(reader, ForageTable.class);//read file and convert to foragetable, which is a wrapper for a list of forage pools.

		return table;
	}
}

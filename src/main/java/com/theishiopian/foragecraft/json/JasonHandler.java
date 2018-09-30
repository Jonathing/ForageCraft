package com.theishiopian.foragecraft.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//does the dirty work so we don't have to.

import com.google.gson.*;
import com.theishiopian.foragecraft.json.ForageTable.ForagePool;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class JasonHandler
{
	public static String deserializeTable(String table)
	{
		ForagePool[] pools = new Gson().fromJson(table, ForagePool[].class);
		
		String out = "Items: ";
		
		for(ForagePool add : pools)
		{
			out += add.toString();
		}
		
		return out;
	}

	public static ForageTable buildTableFromJSON(ResourceLocation location) throws IOException
	{
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		ForageTable test = gson.fromJson(reader, ForageTable.class);

		return test;
	}
}

package com.theishiopian.foragecraft.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//does the dirty work so we don't have to.

import com.google.gson.*;
import com.theishiopian.foragecraft.json.ForageTable.ForagePool;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class JasonHandler
{
	//test method for generating jsons, will move latter.
	public static String serializeTable()
	{
		List<ForageTable.ForagePool>table= new ArrayList<>();
		table.add(new ForageTable.ForagePool("stick", 3.0f));
		table.add(new ForageTable.ForagePool("rock", 5.0f));
		table.add(new ForageTable.ForagePool("bone", 7.0f));
		
		//ForageTable testTable = new ForageTable(table);
		
		String testJSON = new Gson().toJson(table);
		
		return testJSON;
	}
	
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
		//System.out.println(reader.readLine());//dont ask why this is neccesary, I dont fucking know. It works, leave it. Its useful as debug at least. Maybe we can redirect it to the logging system later.
		ForageTable test = gson.fromJson(reader, ForageTable.class);

		return test;
	}
}

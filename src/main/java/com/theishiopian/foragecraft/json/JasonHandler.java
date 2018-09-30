package com.theishiopian.foragecraft.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//does the dirty work so we don't have to.

import com.google.gson.*;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class JasonHandler
{
	
	
	public static void testMethod(ForageTable test)
	{
		System.out.println(test.getAge());
	}

	public static ForageTable buildTableFromJSON(ResourceLocation location) throws IOException
	{
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		System.out.println(reader.readLine());//dont ask why this is neccesary, I dont fucking know. It works, leave it. Its useful as debug at least. Maybe we can redirect it to the logging system later.
		ForageTable test = gson.fromJson(reader, ForageTable.class);
		

		

		return test;
	}
}

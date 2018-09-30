package com.theishiopian.foragecraft.json;

//does the dirty work so we don't have to. God bless you JasonHandler

import com.google.gson.*;

public class JasonHandler
{
	
	
	public static void testMethod(ForageTable test)
	{
		System.out.println(test);
	}

	public static ForageTable buildTableFromJSON(String jsonString)
	{
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();

		Gson gson = builder.create();
		ForageTable test = gson.fromJson(jsonString, ForageTable.class);
		

		

		return test;
	}
}

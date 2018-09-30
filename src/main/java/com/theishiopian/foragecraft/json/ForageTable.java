package com.theishiopian.foragecraft.json;

import java.util.List;

public class ForageTable
{
	List<ForagePool> items;
	
	public ForageTable(List<ForagePool>items)
	{
		this.items=items;
	}
	
	public static class ForagePool
	{
		String name;
		float price;
		
		public ForagePool(String name, float price)
		{
			this.name = name;
			this.price = price;
		}
		
		public String toString()
		{
			return (name+" "+price+" ");
		}
	}
}

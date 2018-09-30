package com.theishiopian.foragecraft.json;

import java.util.List;

public class ForageTable
{
	private List<ForagePool> items;
	
	public ForageTable(List<ForagePool>items)
	{
		this.items=items;
	}
	
	public int getSize()
	{
		return items.size();
	}
	
	public String getPool(int index)
	{
		return items.get(index).toString();
	}
	
	@Override
	public String toString()
	{
		return this.items.toString();
	}
	
	public static class ForagePool
	{
		String name;
		float chance;
		int amount;
		
		public ForagePool(String name, float chance, int amount)
		{
			this.name = name;
			this.chance = chance;
			this.amount = amount;
		}
		
		public String toString()
		{
			return (name+" "+chance+" "+amount+" ");
		}
	}
}

package com.theishiopian.foragecraft.json;

import java.util.List;

/**
 * This object is just a wrapper for a list.
 * The list is all the forage options for a single block.
 * @author Andrew
 *
 */

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
	
	
	/**
	 * This inner class is the meat of the whole operation. It acts as a storage bpx for a particular forage option eg sticks from leaves.
	 * @author Andrew
	 *
	 */
	public static class ForagePool
	{
		String item;
		float chance;
		int amount;
		
		//will store items as strings, then convert automatically to itemstack notation via magic.
		public ForagePool(String item, float chance, int amount)
		{
			this.item = item;
			this.chance = chance;
			this.amount = amount;
		}
		
		public String toString()
		{
			return (item+" "+chance+" "+amount+" ");
		}
	}
}

package com.theishiopian.foragecraft.world;

import java.util.LinkedHashSet;

import com.theishiopian.foragecraft.ForageLogger;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

/*
/  Logger based from Tinkers' Construct
/  https://github.com/SlimeKnights/TinkersConstruct/blob/08f7180399ca8ad4717493dd0aa5a63b7aa14584/src/main/java/slimeknights/tconstruct/TConstruct.java
*/
//unused?
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Fuckin hate the scarecrow problems
// I feel you. it will get better when I add crows
public class ScarecrowTracking extends net.minecraft.world.storage.WorldSavedData
{
	private final LinkedHashSet<BlockPos>scarecrowLocations = new LinkedHashSet<BlockPos>();
	private final static String NAME = "foragecraft:scarecrows";

	public ScarecrowTracking()
	{
		super(NAME);
	}

	public static ScarecrowTracking get(World world) 
	{
		// The IS_GLOBAL constant is there for clarity, and should be simplified into the right branch.
		MapStorage storage = world.getPerWorldStorage();
		ScarecrowTracking instance = (ScarecrowTracking)storage.getOrLoadData(ScarecrowTracking.class, NAME);

		if (instance == null)
		{
			instance = new ScarecrowTracking();
			storage.setData(NAME, instance);
		}
		return instance;
	}

	// adds scarecrow to list
	public void addScarecrow(BlockPos pos)
	{
		scarecrowLocations.add(pos);
		ForageLogger.printDevelop("Registering Scarecrow");
	}

	// removes scarecrow from list
	public void removeScarecrow(BlockPos pos)
	{
		scarecrowLocations.remove(pos);
		ForageLogger.printDevelop("Unregistering Scarecrow");
	}

	@Override
	public void readFromNBT(NBTTagCompound scTag) 
	{
		scarecrowLocations.clear();

		final NBTTagList scarecrows = scTag.getTagList("Scarecrows", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < scarecrows.tagCount(); i++) 
		{
			final NBTTagCompound locationTag = scarecrows.getCompoundTagAt(i);

			final BlockPos location = new BlockPos(locationTag.getInteger("posX"), locationTag.getInteger("posY"), locationTag.getInteger("posZ"));

			scarecrowLocations.add(location);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound scTag) 
	{
		final NBTTagList locationList = new NBTTagList();

		for (BlockPos pos : scarecrowLocations) 
		{
			final NBTTagCompound locationTag = new NBTTagCompound();
			locationTag.setInteger("posX", pos.getX());
			locationTag.setInteger("posY", pos.getY());
			locationTag.setInteger("posZ", pos.getZ());
			locationList.appendTag(locationTag);
		}
		scTag.setTag("Scarecrows", locationList);

		return scTag;
	}

	public boolean inRange(Entity entity)
	{
		for(BlockPos pos : scarecrowLocations)
		{
			if(entity.getEntityBoundingBox().intersects(new AxisAlignedBB(pos).expand(64,64,64)))
			{
				return true;
			}
		}
		return false;
	}
}
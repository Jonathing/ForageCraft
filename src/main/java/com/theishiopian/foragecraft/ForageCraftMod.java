package com.theishiopian.foragecraft;

import java.io.IOException;

import com.theishiopian.foragecraft.config.ConfigHandler;
import com.theishiopian.foragecraft.handler.BlockForageHandler;
import com.theishiopian.foragecraft.handler.FuelHandler;
import com.theishiopian.foragecraft.handler.PotatoPlanter;
import com.theishiopian.foragecraft.handler.RecipeHandler;
import com.theishiopian.foragecraft.handler.SpawnHandler;
import com.theishiopian.foragecraft.init.ModBlocks;
import com.theishiopian.foragecraft.init.ModEntities;
import com.theishiopian.foragecraft.init.ModItems;
import com.theishiopian.foragecraft.proxy.CommonProxy;
import com.theishiopian.foragecraft.world.generation.FCMasterWorldGenerator;
import com.theishiopian.foragecraft.json.*;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
		modid = ForageCraftMod.MODID,
		name = ForageCraftMod.NAME,
		version = ForageCraftMod.VERSION,
		acceptedMinecraftVersions = ForageCraftMod.MC_VERSIONS,
		updateJSON = ForageCraftMod.UPDATE_JSON
	)

public class ForageCraftMod
{
	public static final String MODID = "foragecraft";
	public static final String MOD_NAME = "ForageCraft";
	public static final String NAME = "ForageCraft";
	public static final String VERSION = "2.0.0-dev";
	public static final String MC_VERSIONS = "[1.12], [1.12.1], [1.12.2]";
	public static final String UPDATE_JSON = "https://raw.githubusercontent.com/theishiopian/ForageCraft/master/update.json";
	public static final String PROXY_CLIENT = "com.theishiopian.foragecraft.proxy.Client";
	public static final String PROXY_SERVER = "com.theishiopian.foragecraft.proxy.Server";

	@SidedProxy(clientSide = ForageCraftMod.PROXY_CLIENT, serverSide = ForageCraftMod.PROXY_SERVER)
	public static CommonProxy proxy;

	@Instance
	public static ForageCraftMod instance;

	public static com.theishiopian.foragecraft.handler.EventHandler forgeEventHandler;

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.loadConfig(event);
		ConfigHandler.autoDeveloperMode("dev"); //If version contains "dev", enable developer mode.
		ConfigHandler.configWarnings();

		// Register EventHandler
		forgeEventHandler = new com.theishiopian.foragecraft.handler.EventHandler();
		forgeEventHandler.register();
		ForageLogger.printInfo("Target acquired...");
		
		// Initialize Blocks
		ModBlocks.init(); ForageLogger.printDevelop("Blocks initialized successfully.");
		
		// Initialize Items
		ModItems.init(); ForageLogger.printDevelop("Items initialized successfully.");

		// Initialize Entities
		ModEntities.init(); ForageLogger.printDevelop("Entities initialized successfully.");
		
		//todo: make this functional
		populateForageDictionaryFromTables();
	}

	@SuppressWarnings("deprecation")
	@EventHandler // Time to Magic School Bus this shit
	public void Init(FMLInitializationEvent event)
	{
		// Let's Do This Thing
		proxy.init(event);

		MinecraftForge.EVENT_BUS.register(new PotatoPlanter());
		MinecraftForge.EVENT_BUS.register(new BlockForageHandler());
		MinecraftForge.EVENT_BUS.register(new SpawnHandler());
		RecipeHandler.Shapless();
		SeedLoader.seed();
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.registerWorldGenerator(new FCMasterWorldGenerator(), 10);

		// Getting fanceh here
		ForageLogger.printInfo("The Magic School Bus is here.");
	}
	
	private static void populateForageDictionaryFromTables()
	{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");//debug shit, please ignore
		try
		{
			System.out.println(JasonHandler.buildTableFromJSON(new ResourceLocation(ForageCraftMod.MODID+":data/dirt.json")).toString());
			System.out.println(JasonHandler.buildTableFromJSON(new ResourceLocation(ForageCraftMod.MODID+":data/grass.json")).toString());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

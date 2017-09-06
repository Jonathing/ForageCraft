package com.theishiopian.foragecraft;

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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/*
/  Logger based from Tinkers' Construct
/  https://github.com/SlimeKnights/TinkersConstruct/blob/08f7180399ca8ad4717493dd0aa5a63b7aa14584/src/main/java/slimeknights/tconstruct/TConstruct.java
*/

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MC_VERSIONS, updateJSON = Reference.UPDATE_JSON)

public class ForageCraftMod
{
	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
	public static CommonProxy proxy;

	@Instance
	public static ForageCraftMod instance;

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		// Call config
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		// Load config
		config.load();

		// Leaves
		ConfigVariables.branchChance = config.getFloat("Branch chance", "Sticks from leaves", 1.0f, 0.0f, 1.0f,
				Reference.descConfigBranchChance);
		ConfigVariables.branchMaxAmount = config.getInt("Maximum branch amount", "Sticks from leaves", 1, 0, 64,
				Reference.descConfigBranchMaxAmount);

		// Grass block
		ConfigVariables.rootChance = config.getFloat("Root chance", "Sticks from grass blocks", 0.09f, 0.0f, 1.0f,
				Reference.descConfigRootChance);
		ConfigVariables.rootMaxAmount = config.getInt("Maximum root amount", "Sticks from grass blocks", 1, 0, 64,
				Reference.descConfigRootMaxAmount);

		ConfigVariables.rockChance = config.getFloat("Rock chance", "Rocks from various blocks", 0.09f, 0.0f, 1.0f,
				Reference.descConfigRockChance);
		ConfigVariables.rockMaxAmount = config.getInt("Maximum rock amount", "Rocks from various blocks", 1, 0, 64,
				Reference.descConfigRockMaxAmount);

		ConfigVariables.wildCarrotChance = config.getFloat("Carrot chance", "Carrots from grass blocks", 0.01f, 0.0f,
				1.0f, Reference.descConfigWildCarrotChance);
		ConfigVariables.wildCarrotMaxAmount = config.getInt("Maximum carrot amount", "Carrots from grass blocks", 1, 0,
				64, Reference.descConfigWildCarrotMaxAmount);

		ConfigVariables.wildPotatoChance = config.getFloat("Potato chance", "Potatoes from grass blocks", 0.01f, 0.0f,
				1.0f, Reference.descConfigWildPotatoChance);
		ConfigVariables.wildPotatoMaxAmount = config.getInt("Maximum potato amount", "Potatoes from grass blocks", 1, 0,
				64, Reference.descConfigWildPotatoMaxAmount);

		ConfigVariables.wildToxicPotatoChance = config.getFloat("Poisonous potato chance", "Potatoes from grass blocks",
				0.01f, 0.0f, 1.0f, Reference.descConfigWildToxicPotatoChance);
		ConfigVariables.wildToxicPotatoMaxAmount = config.getInt("Maximum poisonous potato amount",
				"Potatoes from grass blocks", 1, 0, 64, Reference.descConfigWildToxicPotatoMaxAmount);

		ConfigVariables.wildBeetRootChance = config.getFloat("Beet root chance", "Beet roots from grass blocks", 0.01f,
				0.0f, 1.0f, Reference.descConfigWildBeetRootChance);
		ConfigVariables.wildBeetRootMaxAmount = config.getInt("Maximum beet root amount",
				"Beet roots from grass blocks", 1, 0, 64, Reference.descConfigWildBeetRootMaxAmount);

		// Bones
		ConfigVariables.buriedBonesChance = config.getFloat("Buried bones chance", "Buried Bones", 0.005f, 0.0f, 1.0f,
				Reference.descConfigBuriedBonesChance);
		ConfigVariables.buriedBonesMaxBoneAmount = config.getInt("Maximum buried bones amount", "Buried Bones", 9, 0,
				64, Reference.descConfigBuriedBonesMaxBoneAmount);
		ConfigVariables.buriedBonesMaxSkullAmount = config.getInt("Maximum buried skull amount", "Buried Bones", 1, 0,
				64, Reference.descConfigBuriedBonesMaxSkullAmount);

		// Dirt
		ConfigVariables.deepRootChance = config.getFloat("Deep root chance", "Sticks from dirt blocks", 0.07f, 0.0f,
				1.0f, Reference.descConfigDeepRootChance);
		ConfigVariables.deepRootMaxAmount = config.getInt("Maximum deep root amount", "Sticks from dirt blocks", 1, 0,
				64, Reference.descConfigDeepRootMaxAmount);

		ConfigVariables.buriedFlintChance = config.getFloat("Buried Flint Chance", "Buried Flint", 0.04f, 0.0f, 1.0f,
				Reference.descConfigBuriedFlintChance);
		ConfigVariables.buriedFlintMaxAmount = config.getInt("Maximum buried flint amount", "Buried Flint", 1, 0, 64,
				Reference.descConfigBuriedFlintMaxAmount);

		// Stone
		ConfigVariables.goldChance = config.getFloat("Gold chance", "Gold in stone", 0.005f, 0.0f, 1.0f,
				Reference.descConfigGoldChance);
		ConfigVariables.goldMaxAmount = config.getInt("Maximum gold amount", "Gold in stone", 1, 0, 64,
				Reference.descConfigGoldMaxAmount);

		ConfigVariables.flintChipChance = config.getFloat("Flint chip chance", "Flint chips", 0.05f, 0.0f, 1.0f,
				Reference.descConfigFlintChipChance);
		ConfigVariables.flintChipMaxAmount = config.getInt("Maximum flint chip amount", "Flint chips", 1, 0, 64,
				Reference.descConfigFlintChipMaxAmount);

		// Coal ore
		ConfigVariables.coalDiamondChance = config.getFloat("Coal diamond chance", "Coal gems", 0.001f, 0.0f, 1.0f,
				Reference.descConfigCoalDiamondChance);
		ConfigVariables.coalDiamondMaxAmount = config.getInt("Maximum coal diamond amount", "Coal gems", 1, 0, 64,
				Reference.descConfigCoalDiamondMaxAmount);

		ConfigVariables.coalEmeraldChance = config.getFloat("Coal emerald chance", "Coal gems", 0.001f, 0.0f, 1.0f,
				Reference.descConfigCoalEmeraldChance);
		ConfigVariables.coalEmeraldMaxAmount = config.getInt("Maximum coal emerald amount", "Coal gems", 1, 0, 64,
				Reference.descConfigCoalEmeraldMaxAmount);

		// Nether quartz
		ConfigVariables.netherGoldChance = config.getFloat("Nether gold chance", "Nether gold", 1.0f, 0.0f, 1.0f,
				Reference.descConfigNetherGoldChance);
		ConfigVariables.netherGoldMaxAmount = config.getInt("Maximum nether gold amount", "Nether gold", 9, 0, 64,
				Reference.descConfigNetherGoldMaxAmount);

		// Seeds from grass
		ConfigVariables.pumpkinSeeds = config.getBoolean("Pumpkin seeds from grass", "Pumpkin seeds", true,
				Reference.descConfigPumpkinSeeds);
		ConfigVariables.melonSeeds = config.getBoolean("Melon seeds from grass", "Melon seeds", true,
				Reference.descConfigMelonSeeds);
		ConfigVariables.beetrootSeeds = config.getBoolean("Beetroot seeds from grass", "Beetroot seeds", true,
				Reference.descConfigBeetrootSeeds);

		// Developer
		ConfigVariables.developerMode = config.getBoolean("Developer Mode", "development", false,
				Reference.descConfigDeveloperMode);

		// Mod Integration
		ConfigVariables.jeiVanillaInt = config.getBoolean("JEI Integration", "Mod Integration", true,
				Reference.descConfigJeiVanillaInt);

		// World Generation
		ConfigVariables.enableSticks = config.getBoolean("Sticks generation", "World Generation", true,
				Reference.descConfigEnableSticks);
		ConfigVariables.enableRocks = config.getBoolean("Rocks generation", "World Generation", true,
				Reference.descConfigEnableRocks);

		// Save config
		config.save();
		ForageLogger.printInfo("Configuration file loaded.");

		// Developer Mode logging
		if(!ConfigVariables.developerMode)
			ForageLogger.printWarn("Developer Mode is enabled. Development logging will occur at the [INFO] level.");
		else ForageLogger.printWarn("Developer Mode is disabled. Development logging will occur at the [DEBUG] level.");
		if(!ConfigVariables.jeiVanillaInt)
			ForageLogger.printWarn("JEI Integration for vanilla items has been disabled.");
		if(!ConfigVariables.enableSticks)
			ForageLogger.printWarn("Stick generation has been disabled in the ForageCraft configuration file.");
		if(!ConfigVariables.enableRocks)
			ForageLogger.printWarn("Rock generation has been disabled in the ForageCraft configuration file.");

		// Initialize Items
		ModItems.init();

		ForageLogger.printDevelop("Items initialized successfully.");

		// Initialize Blocks
		ModBlocks.init();
		// ModBlocks.register();
		ForageLogger.printDevelop("Blocks initialized successfully.");

		// Initialize Entities
		ModEntities.init();
		ForageLogger.printDevelop("Entities initialized successfully.");

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
		GameRegistry.registerFuelHandler(new FuelHandler());// deprecated,
															// alternatives?
		GameRegistry.registerWorldGenerator(new FCMasterWorldGenerator(), 10);

		// Getting fanceh here
		ForageLogger.printInfo("Ready to forage.");
	}
}

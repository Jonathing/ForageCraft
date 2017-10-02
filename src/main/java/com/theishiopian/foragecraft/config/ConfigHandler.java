package com.theishiopian.foragecraft.config;

import com.theishiopian.foragecraft.ForageCraftMod;
import com.theishiopian.foragecraft.ForageLogger;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
    public static final String notRecommendedForServer = "This is not recommended for servers";

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        // Call config
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        // Load config
        config.load();

        // Leaves
        ConfigVariables.branchChance = config.getFloat("Branch chance", "Sticks from leaves", 1.0f, 0.0f, 1.0f,
                ConfigDescriptions.BranchChance);
        ConfigVariables.branchMaxAmount = config.getInt("Maximum branch amount", "Sticks from leaves", 1, 0, 64,
                ConfigDescriptions.BranchMaxAmount);

        // Grass block
        ConfigVariables.rootChance = config.getFloat("Root chance", "Sticks from grass blocks", 0.09f, 0.0f, 1.0f,
                ConfigDescriptions.RootChance);
        ConfigVariables.rootMaxAmount = config.getInt("Maximum root amount", "Sticks from grass blocks", 1, 0, 64,
                ConfigDescriptions.RootMaxAmount);

        ConfigVariables.rockChance = config.getFloat("Rock chance", "Rocks from various blocks", 0.09f, 0.0f, 1.0f,
                ConfigDescriptions.RockChance);
        ConfigVariables.rockMaxAmount = config.getInt("Maximum rock amount", "Rocks from various blocks", 1, 0, 64,
                ConfigDescriptions.RockMaxAmount);

        ConfigVariables.wildCarrotChance = config.getFloat("Carrot chance", "Carrots from grass blocks", 0.01f, 0.0f,
                1.0f, ConfigDescriptions.WildCarrotChance);
        ConfigVariables.wildCarrotMaxAmount = config.getInt("Maximum carrot amount", "Carrots from grass blocks", 1, 0,
                64, ConfigDescriptions.WildCarrotMaxAmount);

        ConfigVariables.wildPotatoChance = config.getFloat("Potato chance", "Potatoes from grass blocks", 0.01f, 0.0f,
                1.0f, ConfigDescriptions.WildPotatoChance);
        ConfigVariables.wildPotatoMaxAmount = config.getInt("Maximum potato amount", "Potatoes from grass blocks", 1, 0,
                64, ConfigDescriptions.WildPotatoMaxAmount);

        ConfigVariables.wildToxicPotatoChance = config.getFloat("Poisonous potato chance", "Potatoes from grass blocks",
                0.01f, 0.0f, 1.0f, ConfigDescriptions.WildToxicPotatoChance);
        ConfigVariables.wildToxicPotatoMaxAmount = config.getInt("Maximum poisonous potato amount",
                "Potatoes from grass blocks", 1, 0, 64, ConfigDescriptions.WildToxicPotatoMaxAmount);

        ConfigVariables.wildBeetRootChance = config.getFloat("Beet root chance", "Beet roots from grass blocks", 0.01f,
                0.0f, 1.0f, ConfigDescriptions.WildBeetRootChance);
        ConfigVariables.wildBeetRootMaxAmount = config.getInt("Maximum beet root amount",
                "Beet roots from grass blocks", 1, 0, 64, ConfigDescriptions.WildBeetRootMaxAmount);

        // Bones
        ConfigVariables.buriedBonesChance = config.getFloat("Buried bones chance", "Buried Bones", 0.005f, 0.0f, 1.0f,
                ConfigDescriptions.BuriedBonesChance);
        ConfigVariables.buriedBonesMaxBoneAmount = config.getInt("Maximum buried bones amount", "Buried Bones", 9, 0,
                64, ConfigDescriptions.BuriedBonesMaxBoneAmount);
        ConfigVariables.buriedBonesMaxSkullAmount = config.getInt("Maximum buried skull amount", "Buried Bones", 1, 0,
                64, ConfigDescriptions.BuriedBonesMaxSkullAmount);

        // Dirt
        ConfigVariables.deepRootChance = config.getFloat("Deep root chance", "Sticks from dirt blocks", 0.07f, 0.0f,
                1.0f, ConfigDescriptions.DeepRootChance);
        ConfigVariables.deepRootMaxAmount = config.getInt("Maximum deep root amount", "Sticks from dirt blocks", 1, 0,
                64, ConfigDescriptions.DeepRootMaxAmount);

        ConfigVariables.buriedFlintChance = config.getFloat("Buried Flint Chance", "Buried Flint", 0.04f, 0.0f, 1.0f,
                ConfigDescriptions.BuriedFlintChance);
        ConfigVariables.buriedFlintMaxAmount = config.getInt("Maximum buried flint amount", "Buried Flint", 1, 0, 64,
                ConfigDescriptions.BuriedFlintMaxAmount);

        // Stone
        ConfigVariables.goldChance = config.getFloat("Gold chance", "Gold in stone", 0.005f, 0.0f, 1.0f,
                ConfigDescriptions.GoldChance);
        ConfigVariables.goldMaxAmount = config.getInt("Maximum gold amount", "Gold in stone", 1, 0, 64,
                ConfigDescriptions.GoldMaxAmount);

        ConfigVariables.flintChipChance = config.getFloat("Flint chip chance", "Flint chips", 0.05f, 0.0f, 1.0f,
                ConfigDescriptions.FlintChipChance);
        ConfigVariables.flintChipMaxAmount = config.getInt("Maximum flint chip amount", "Flint chips", 1, 0, 64,
                ConfigDescriptions.FlintChipMaxAmount);

        // Coal ore
        ConfigVariables.coalDiamondChance = config.getFloat("Coal diamond chance", "Coal gems", 0.001f, 0.0f, 1.0f,
                ConfigDescriptions.CoalDiamondChance);
        ConfigVariables.coalDiamondMaxAmount = config.getInt("Maximum coal diamond amount", "Coal gems", 1, 0, 64,
                ConfigDescriptions.CoalDiamondMaxAmount);

        ConfigVariables.coalEmeraldChance = config.getFloat("Coal emerald chance", "Coal gems", 0.001f, 0.0f, 1.0f,
                ConfigDescriptions.CoalEmeraldChance);
        ConfigVariables.coalEmeraldMaxAmount = config.getInt("Maximum coal emerald amount", "Coal gems", 1, 0, 64,
                ConfigDescriptions.CoalEmeraldMaxAmount);

        // Nether quartz
        ConfigVariables.netherGoldChance = config.getFloat("Nether gold chance", "Nether gold", 1.0f, 0.0f, 1.0f,
                ConfigDescriptions.NetherGoldChance);
        ConfigVariables.netherGoldMaxAmount = config.getInt("Maximum nether gold amount", "Nether gold", 9, 0, 64,
                ConfigDescriptions.NetherGoldMaxAmount);

        // Seeds from grass
        ConfigVariables.pumpkinSeeds = config.getBoolean("Pumpkin seeds from grass", "Pumpkin seeds", true,
                ConfigDescriptions.PumpkinSeeds);
        ConfigVariables.melonSeeds = config.getBoolean("Melon seeds from grass", "Melon seeds", true,
                ConfigDescriptions.MelonSeeds);
        ConfigVariables.beetrootSeeds = config.getBoolean("Beetroot seeds from grass", "Beetroot seeds", true,
                ConfigDescriptions.BeetrootSeeds);

        // Developer
        ConfigVariables.developerMode = config.getBoolean("Developer Mode", "development", false,
                ConfigDescriptions.DeveloperMode);

        // Mod Integration
        ConfigVariables.jeiVanillaInt = config.getBoolean("JEI Integration", "Mod Integration", true,
                ConfigDescriptions.JeiVanillaInt);

        // World Generation
        ConfigVariables.enableSticks = config.getBoolean("Sticks generation", "World Generation", true,
                ConfigDescriptions.EnableSticks);
        ConfigVariables.enableRocks = config.getBoolean("Rocks generation", "World Generation", true,
                ConfigDescriptions.EnableRocks);

        // Save config
        config.save();

        // List all the set variables in the debug console (ONLY DEBUG, NOT AFFECTED BY DEVELOPER MODE)
        debugListAllVariables();

        ForageLogger.printInfo("Configuration file loaded.");
    }

    public static void autoDeveloperMode(String versionCode)
    {
        if(ForageCraftMod.VERSION.contains(versionCode))
        {
            ConfigVariables.developerMode = true;
            ForageLogger.printInfo("This is a development version of ForageCraft. Therefore, developer mode has been automatically enabled.");
        }
    }

    public static void configWarnings()
    {
        // Developer Mode logging
        if(ConfigVariables.developerMode)
            ForageLogger.printWarn("Developer Mode is enabled. Development logging will occur at the [INFO] level.");
        else ForageLogger.printWarn("Developer Mode is disabled. Development logging will occur at the [DEBUG] level.");
        if(!ConfigVariables.jeiVanillaInt)
            ForageLogger.printWarn("JEI Integration for vanilla items has been disabled.");
        if(!ConfigVariables.enableSticks)
            ForageLogger.printWarn("Stick generation has been disabled in the ForageCraft configuration file.");
        if(!ConfigVariables.enableRocks)
            ForageLogger.printWarn("Rock generation has been disabled in the ForageCraft configuration file.");
    }

    public static void debugListAllVariables()
    {
        // Spit out all the stuff (DEBUG LOG ONLY, NOT DEVELOPER)
        ForageLogger.printDebug("Chance of digging up sticks from leaves is " + ConfigVariables.branchChance);
        ForageLogger.printDebug("Maximum amount of sticks dropping from leaves is " + ConfigVariables.branchMaxAmount);
        ForageLogger.printDebug("Chance of digging up sticks from grass block is " + ConfigVariables.rootChance);
        ForageLogger.printDebug("Maximum amount of sticks dropping from grass block is " + ConfigVariables.rootMaxAmount);
        ForageLogger.printDebug("Chance of digging up sticks from dirt is " + ConfigVariables.deepRootChance);
        ForageLogger.printDebug("Maximum amount of sticks dropping from dirt is " + ConfigVariables.deepRootMaxAmount);

        ForageLogger.printDebug("Chance of digging up rocks from various blocks is " + ConfigVariables.rockChance);
        ForageLogger.printDebug("Maximum amount of rocks dropping from various blocks is " + ConfigVariables.rockMaxAmount);

        ForageLogger.printDebug("Chance of digging up carrots from a grass block is " + ConfigVariables.wildCarrotChance);
        ForageLogger.printDebug("Maximum amount of carrots dropping from a grass block is " + ConfigVariables.wildCarrotMaxAmount);

        ForageLogger.printDebug("Chance of digging up potatoes from a grass block is " + ConfigVariables.wildPotatoChance);
        ForageLogger.printDebug("Maximum amount of potatoes dropping from a grass block is " + ConfigVariables.wildPotatoMaxAmount);

        ForageLogger.printDebug("Chance of digging up beet root from a grass block is " + ConfigVariables.wildBeetRootChance);
        ForageLogger.printDebug("Maximum amount of beet root dropping from a grass block is " + ConfigVariables.wildBeetRootMaxAmount);
        if(ConfigVariables.beetrootSeeds)
            ForageLogger.printDebug("Digging up beet root seeds from grass is enabled.");
        else
            ForageLogger.printDebug("Digging up beet root seeds from grass is disabled.");

        if(ConfigVariables.pumpkinSeeds)
            ForageLogger.printDebug("Digging up pumpkin seeds from grass is enabled.");
        else
            ForageLogger.printDebug("Digging up pumpkin seeds from grass is disabled.");

        if(ConfigVariables.melonSeeds)
            ForageLogger.printDebug("Digging up melon seeds from grass is enabled.");
        else
            ForageLogger.printDebug("Digging up melon seeds from grass is disabled.");

        ForageLogger.printDebug("Chance of digging up bones is " + ConfigVariables.buriedBonesChance);
        ForageLogger.printDebug("Maximum amount of bones dropping from stone is " + ConfigVariables.buriedBonesMaxBoneAmount);
        ForageLogger.printDebug("Maximum amount of skulls dropping from stone is " + ConfigVariables.buriedBonesMaxSkullAmount);

        ForageLogger.printDebug("Chance of digging up flint from dirt is " + ConfigVariables.buriedFlintChance);
        ForageLogger.printDebug("Maximum amount of flint dropping from dirt is " + ConfigVariables.buriedFlintMaxAmount);
        ForageLogger.printDebug("Chance of digging up flint from stone is " + ConfigVariables.flintChipChance);
        ForageLogger.printDebug("Maximum amount of flint dropping from stone is " + ConfigVariables.flintChipMaxAmount);

        ForageLogger.printDebug("Chance of digging up gold nuggets from stone is " + ConfigVariables.goldChance);
        ForageLogger.printDebug("Maximum amount of gold nuggets dropping from stone is " + ConfigVariables.goldMaxAmount);
        ForageLogger.printDebug("Chance of digging up gold nuggets from nether quartz ore is " + ConfigVariables.netherGoldChance);
        ForageLogger.printDebug("Maximum amount of gold nuggets dropping from nether quartz ore is " + ConfigVariables.netherGoldMaxAmount);

        ForageLogger.printDebug("Chance of digging up diamonds from coal ore is " + ConfigVariables.coalDiamondChance);
        ForageLogger.printDebug("Maximum amount of diamonds dropping from coal ore is " + ConfigVariables.coalDiamondMaxAmount);

        ForageLogger.printDebug("Chance of digging up emeralds from coal ore is " + ConfigVariables.coalEmeraldChance);
        ForageLogger.printDebug("Maximum amount of emeralds dropping from coal ore is " + ConfigVariables.coalEmeraldMaxAmount);
    }
}

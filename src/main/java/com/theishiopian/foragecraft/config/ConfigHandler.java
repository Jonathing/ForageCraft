package com.theishiopian.foragecraft.config;

import com.theishiopian.foragecraft.ForageCraftMod;
import com.theishiopian.foragecraft.ForageLogger;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
    public static void loadConfig(FMLPreInitializationEvent event)
    {
        // Call config
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        // Load config
        config.load();

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
            ForageLogger.printWarn("Developer Mode is enabled. Development logging will occur at the [INFO] level. Generation of rocks and sticks will always occur at [DEBUG] level.");
        if(!ConfigVariables.jeiVanillaInt)
            ForageLogger.printWarn("JEI Integration for vanilla items has been disabled.");
        if(!ConfigVariables.enableSticks)
            ForageLogger.printWarn("Stick generation has been disabled in the ForageCraft configuration file.");
        if(!ConfigVariables.enableRocks)
            ForageLogger.printWarn("Rock generation has been disabled in the ForageCraft configuration file.");
    }

    public static void debugListAllVariables()
    {
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
    }
}

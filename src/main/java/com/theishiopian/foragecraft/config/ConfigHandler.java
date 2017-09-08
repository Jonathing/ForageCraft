package com.theishiopian.foragecraft.config;

import com.theishiopian.foragecraft.ForageLogger;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


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
        ForageLogger.printInfo("Configuration file loaded.");
    }

    public static void configWarnings()
    {
        //how do I use this stuff?
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
    }
}

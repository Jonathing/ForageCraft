package com.theishiopian.foragecraft;

public class Reference
{
    /* Mod Info */
    // Easy changes to the mcmod.info file
    public static final String MODID = "foragecraft";
    public static final String MOD_NAME = "ForageCraft";
    public static final String NAME = "ForageCraft";
    public static final String VERSION = "1.12.3";
    public static final String MC_VERSIONS = "[1.11.2]";
    public static final String UPDATE_JSON = "https://raw.githubusercontent.com/theishiopian/ForageCraft/master/update.json";
    public static final String CLIENTPROXY = "com.theishiopian.foragecraft.proxy.Client";
    public static final String SERVERPROXY = "com.theishiopian.foragecraft.proxy.Server";

    /* ConfigVar */
    // Leaves
    public static final String descConfigBranchChance = "The chance of a stick dropping fom leaves";
    public static final String descConfigBranchMaxAmount = "The maximum number of sticks you can get from leaves";

    // Grass Block
    public static final String descConfigRootChance = "The chance of digging up a root (stick) from a grass block";
    public static final String descConfigRootMaxAmount = "The maximum amount of roots (sticks) you can get from a grass block";

    public static final String descConfigRockChance = "The chance of digging up a rock";
    public static final String descConfigRockMaxAmount = "The maximum amount of rocks you can get from various blocks";

    public static final String descConfigWildCarrotChance = "The chance of digging up a carrot from a grass block";
    public static final String descConfigWildCarrotMaxAmount = "The maximum amount of carrots you can get from a grass block";

    public static final String descConfigWildPotatoChance = "The chance of digging up a potato from a grass block";
    public static final String descConfigWildPotatoMaxAmount = "The maximum amount of potatoes you can get from a grass block";

    public static final String descConfigWildToxicPotatoChance = "The chance of digging up a poisonous potato from a grass block";
    public static final String descConfigWildToxicPotatoMaxAmount = "The maximum amount of poisonous potatoes you can get from a grass block";

    public static final String descConfigWildBeetRootChance = "The chance of digging up a beet root from a grass block";
    public static final String descConfigWildBeetRootMaxAmount = "The maximum amount of beet root you can get from a grass block";

    // Bones
    public static final String descConfigBuriedBonesChance = "The chance of uncovering ancient bones";
    public static final String descConfigBuriedBonesMaxBoneAmount = "The maximum amount of bones you can get from buried skeletons";
    public static final String descConfigBuriedBonesMaxSkullAmount = "The maximum amount of skulls buried skeletons can have";

    // Dirt
    public static final String descConfigDeepRootChance = "The chance of digging up a root (stick) from a dirt block";
    public static final String descConfigDeepRootMaxAmount = "The maximum amount of roots (sticks) you can get from a dirt block";

    public static final String descConfigBuriedFlintChance = "The chance of finding flint in a dirt block";
    public static final String descConfigBuriedFlintMaxAmount = "The maximum amount of flint you can get from a dirt block";

    // Stone
    public static final String descConfigGoldChance = "The chance of finding gold (in nugget form) inside a stone block";
    public static final String descConfigGoldMaxAmount = "The maximum amount of gold nuggets you can find in a stone block";

    public static final String descConfigFlintChipChance = "The chance of finding flint in a stone block";
    public static final String descConfigFlintChipMaxAmount = "The maximum amount of flint you can get from a stone block";

    // Coal Ore
    public static final String descConfigCoalDiamondChance = "The chance of finding a diamond while mining coal";
    public static final String descConfigCoalDiamondMaxAmount = "The maximum amount of diamonds you can get from coal ore";

    public static final String descConfigCoalEmeraldChance = "The chance of finding an emerald while mining coal";
    public static final String descConfigCoalEmeraldMaxAmount = "The maximum amount of emeralds you can get from coal ore";

    // Nether Quartz Ore
    public static final String descConfigNetherGoldChance = "The chance of getting a gold nugget from nether quartz";
    public static final String descConfigNetherGoldMaxAmount = "The maximum amount of gold nuggets you can get from nether quartz";

    // Seeds from Tall Grass
    public static final String descConfigPumpkinSeeds = "This determines whether or not you can find pumpkin seeds in grass";
    public static final String descConfigMelonSeeds = "This determines whether or not you can find melon seeds in grass";
    public static final String descConfigBeetrootSeeds = "This determines whether or not you can find beetroot seeds in grass";

    // Developer
    public static final String descConfigDeveloperMode = "Developer mode logs world generation of sticks and rocks to the console";

    // Mod Integration
    public static final String descConfigJeiVanillaInt = "This determines whether or not JEI Integration should be enabled for vanilla items";

    // World Generation
    public static final String descConfigEnableSticks = "This determines whether or not sticks should be generated";
    public static final String descConfigEnableRocks = "This determines whether or not rocks should be generated";
}

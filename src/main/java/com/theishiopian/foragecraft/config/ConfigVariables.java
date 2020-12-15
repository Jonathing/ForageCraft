package com.theishiopian.foragecraft.config;

public class ConfigVariables
{
    // maximum values for events in foragecraft.BlockForageHandler (used for configuration file)
    // chance values are between 0 and 1 (floating point) example: 0.1=10% 1.0=100%
    // amount values are 0 to 64 (integer)

    // leaves
    public static float branchChance;
    public static int branchMaxAmount;

    // grass block
    public static float rootChance;
    public static int rootMaxAmount;

    public static float rockChance;
    public static int rockMaxAmount;

    public static float wildCarrotChance;
    public static int wildCarrotMaxAmount;
    public static float wildPotatoChance;
    public static int wildPotatoMaxAmount;
    public static float wildToxicPotatoChance;
    public static int wildToxicPotatoMaxAmount;
    public static float wildBeetRootChance;
    public static int wildBeetRootMaxAmount;

    // bones in grass and dirt
    public static float buriedBonesChance;
    public static int buriedBonesMaxBoneAmount;
    public static int buriedBonesMaxSkullAmount;

    // dirt block
    public static float deepRootChance;
    public static int deepRootMaxAmount;
    public static float buriedFlintChance;
    public static int buriedFlintMaxAmount;

    // stone
    public static float goldChance;
    public static int goldMaxAmount;
    public static float flintChipChance;
    public static int flintChipMaxAmount;

    // coal ore. because someone spilled some gems in the coal supply >.<
    public static float coalDiamondChance;
    public static int coalDiamondMaxAmount;
    public static float coalEmeraldChance;
    public static int coalEmeraldMaxAmount;

    // nether quartz. where DO pig men get their gold in vanilla anyways???
    public static float netherGoldChance;
    public static int netherGoldMaxAmount;

    // seeds, cause I forgot them
    public static boolean pumpkinSeeds;
    public static boolean melonSeeds;
    public static boolean beetrootSeeds;
    // sticks from grass being removed

    // Developer mode because I'm smart
    public static boolean developerMode;

    // JEI force stop vanilla
    public static boolean jeiVanillaInt;

    // Disable sticks or rocks
    public static boolean enableSticks;
    public static boolean enableRocks;
}

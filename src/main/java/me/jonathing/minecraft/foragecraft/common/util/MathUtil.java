package me.jonathing.minecraft.foragecraft.common.util;

/**
 * This is a utility class to help me do calculations because I'm incredibly lazy and don't feel like doing the same
 * shit!
 *
 * @author Jonathing
 * @since 2.2.2
 */
public class MathUtil
{
    public static int secondsToWorldTicks(int seconds)
    {
        return seconds * 120;
    }

    public static int secondsToTicks(int seconds)
    {
        return seconds * 20;
    }
}

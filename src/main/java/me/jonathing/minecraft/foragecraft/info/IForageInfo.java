package me.jonathing.minecraft.foragecraft.info;

/**
 * Interface that holds the base variables that carry information about ForageCraft. Special thanks to Shadew for
 * allowing easy usage of this via his ModUtil plugin and the boilerplate we made for the Midnight.
 *
 * @author Jonathing
 * @since 2.0.0
 */
public interface IForageInfo
{
    /**
     * Returns the mod-id/resources namespace of ForageCraft, which is {@code foragecraft}.
     */
    String modId();

    /**
     * Returns the mod name of ForageCraft, which is 'ForageCraft'.
     */
    String name();

    /**
     * Returns the build version number of ForageCraft, in the following format:<br>
     * <code><i>major</i>.<i>minor</i>.<i>patch</i><i>[</i>-<i>modifier]</i></code><br>
     */
    String version();

    /**
     * Returns the build version name of ForageCraft.
     */
    String versionName();

    /**
     * Returns the build date of the available ForageCraft build, in RFC-3339 format.
     */
    String buildDate();

    /**
     * Returns whether ForageCraft is running from the IDE or not. When this returns true, ForageCraft provides
     * additinonal tools and features intended for debugging. To enable these features, set the {@code midnight.iside}
     * system property to {@code true}.
     */
    boolean ide();

    boolean forceDevMixins();

    /**
     * Returns whether ForageCraft is running as a data generator.
     */
    boolean data();

    /**
     * Returns whether ForageCraft is running from GitHub Actions.
     */
    boolean testServer();
}

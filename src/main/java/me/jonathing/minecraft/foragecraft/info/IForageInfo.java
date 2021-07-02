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
     *
     * @return The mod-id/resources namespace.
     */
    String modId();

    /**
     * Returns the mod name of ForageCraft, which is 'ForageCraft'.
     *
     * @return The mod name.
     */
    String name();

    /**
     * Returns the build version number of ForageCraft, in the following format:<br>
     * <code><i>major</i>.<i>minor</i>.<i>patch</i><i>[</i>-<i>modifier]</i></code><br>
     *
     * @return The build version number.
     */
    String version();

    /**
     * Returns the build version name of ForageCraft.
     *
     * @return The build version name.
     */
    String versionName();

    /**
     * Returns the build date of the available ForageCraft build, in RFC-3339 format.
     *
     * @return The build date.
     */
    String buildDate();

    /**
     * Returns whether ForageCraft is running from the IDE or not. When this returns true, ForageCraft provides
     * additinonal tools and features intended for debugging. To enable these features, set the
     * {@code foragecraft.iside} system property to {@code true}.
     *
     * @return The result of the IDE check.
     */
    boolean ide();

    /**
     * Returns whether ForageCraft is running as a data generator.
     *
     * @return The result of the datagen check.
     */
    boolean data();

    /**
     * Returns whether ForageCraft is running from GitHub Actions.
     *
     * @return The result of the test server check.
     */
    boolean testServer();
}

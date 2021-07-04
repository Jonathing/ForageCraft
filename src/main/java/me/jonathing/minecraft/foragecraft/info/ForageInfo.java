package me.jonathing.minecraft.foragecraft.info;

/**
 * Contains various important pieces of information about the instance of ForageCraft. Special thanks to Shadew for
 * allowing easy usage of this via his ModUtil plugin and the boilerplate we made for the Midnight.
 *
 * @author Jonathing
 * @see DynamicConstant
 * @since 2.0.0
 */
public final class ForageInfo
{
    private ForageInfo()
    {
    }

    /**
     * The Mod ID of ForageCraft, which is fixed to {@code foragecraft}.
     */
    public static final String MOD_ID = "foragecraft";

    /**
     * The Mod Name of ForageCraft, which is fixed to 'ForageCraft'.
     */
    public static final String NAME = "ForageCraft";

    /**
     * This constant is true when the system property {@code foragecraft.iside} is {@code "true"}. This property is set
     * in all run configurations gradle.
     *
     * @see #isRunningFromIDE()
     */
    public static final boolean IDE = isRunningFromIDE();

    /**
     * This constant is true when the system property {@code foragecraft.datagen} is {@code "true"}. This property is set
     * in the {@code data} run configration (for the {@code runData} task).
     *
     * @see #isRunningDatagen()
     */
    public static final boolean DATAGEN = isRunningDatagen();

    /**
     * This constant is true when the system property {@code foragecraft.istestserver} is {@code "true"}. This property is
     * set in the {@code testserver} run configration (for the {@code runTestServer} task).
     *
     * @see #isRunningTestServer()
     */
    public static final boolean TESTSERVER = isRunningTestServer();

    /**
     * The version of the ForageCraft (ex. {@code 2.0.0}), which is dynamically injected on build. Defaults to {@code
     * NOT.A.VERSION}.
     */
    @DynamicConstant("version")
    public static final String VERSION = "NOT.A.VERSION";

    /**
     * The version name of ForageCraft (ex. 'ForageCraft: Reborn'), which is dynamically injected on build.
     * Defaults to 'Not A Version'.
     */
    @DynamicConstant("version_name")
    public static final String VERSION_NAME = "Not A Version";

    /**
     * The build time of ForageCraft, which is dynamically injected on build. Defaults to {@code 2038-01-19T03:14:08Z},
     * referring to the <a href="https://en.wikipedia.org/wiki/Year_2038_problem">2038 Problem</a>.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Year_2038_problem">The 2038 Problem</a>
     */
    @DynamicConstant("build_time")
    public static final String BUILD_DATE = "2038-01-19T03:14:08Z";

    /**
     * Contains the logic for the {@link #IDE} variable.
     *
     * @see #IDE
     */
    private static boolean isRunningFromIDE()
    {
        String p = System.getProperty("foragecraft.iside");
        return Boolean.parseBoolean(p);
    }

    /**
     * Contains the logic for the {@link #DATAGEN} variable.
     *
     * @see #DATAGEN
     */
    private static boolean isRunningDatagen()
    {
        String p = System.getProperty("foragecraft.datagen");
        return Boolean.parseBoolean(p);
    }

    /**
     * Contains the logic for the {@link #TESTSERVER} variable.
     *
     * @see #TESTSERVER
     */
    private static boolean isRunningTestServer()
    {
        String p = System.getProperty("foragecraft.istestserver");
        return Boolean.parseBoolean(p);
    }
}

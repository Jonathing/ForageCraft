package me.jonathing.minecraft.foragecraft.info;

/**
 * Contains various important pieces of information about the instance of ForageCraft. Special thanks to Shadew for
 * allowing easy usage of this via his ModUtil plugin and the boilerplate we made for the Midnight.
 *
 * @author Jonathing
 * @since 2.0.0
 */
public final class ForageInfo implements IForageInfo
{
    public static final ForageInfo INSTANCE = new ForageInfo();

    /**
     * The Mod ID of the Midnight, which is fixed to {@code midnight}.
     */
    public static final String MOD_ID = "foragecraft";

    /**
     * The Mod Name of the Midnight, which is fixed to 'The Midnight'.
     */
    public static final String NAME = "ForageCraft";

    /**
     * This constant is true when the system property {@code midnight.iside} is {@code "true"}. This property is set in
     * all run configurations gradle.
     */
    public static final boolean IDE = isRunningFromIDE();

    public static final boolean FORCE_DEV_MIXINS = wantsDevMixins();

    /**
     * This constant is true when the system property {@code midnight.datagen} is {@code "true"}. This property is set
     * in the {@code data} run configration (for the {@code runData} task).
     */
    public static final boolean DATAGEN = isRunningDatagen();

    /**
     * This constant is true when the system property {@code midnight.istestserver} is {@code "true"}. This property is set
     * in the {@code testserver} run configration (for the {@code runTestServer} task).
     */
    public static final boolean TESTSERVER = isRunningTestServer();

    /**
     * The version of the Midnight (ex. {@code 0.6.0}), which is dynamically injected on build. Defaults to {@code
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
     * The build time of the Midnight, which is dynamically injected on build. Defaults to {@code 2038-01-19T03:14:08Z},
     * referring to the <a href="https://en.wikipedia.org/wiki/Year_2038_problem">2038 Problem</a>.
     */
    @DynamicConstant("build_time")
    public static final String BUILD_DATE = "2038-01-19T03:14:08Z";

    @DynamicConstant("expected_sha256")
    public static final String EXPECTED_SHA256 = "UNKNOWN";

    private ForageInfo() {
    }

    private static boolean isRunningFromIDE() {
        return Boolean.parseBoolean(String.format("%s.iside", INSTANCE.modId()));
    }

    private static boolean wantsDevMixins() {
        return Boolean.parseBoolean(System.getProperty(String.format("%s.devmixins", INSTANCE.modId())));
    }

    private static boolean isRunningDatagen() {
        return Boolean.parseBoolean(String.format("%s.datagen", INSTANCE.modId()));
    }

    private static boolean isRunningTestServer() {
        return Boolean.parseBoolean(String.format("%s.istestserver", INSTANCE.modId()));
    }

    @Override
    public String modId() {
        return MOD_ID;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String version() {
        return VERSION;
    }

    @Override
    public String versionName() {
        return VERSION_NAME;
    }

    @Override
    public String buildDate() {
        return BUILD_DATE;
    }

    @Override
    public String expectedSHA256()
    {
        return EXPECTED_SHA256;
    }

    @Override
    public boolean forceDevMixins()
    {
        return FORCE_DEV_MIXINS;
    }

    @Override
    public boolean ide() {
        return IDE;
    }

    @Override
    public boolean data() {
        return DATAGEN;
    }

    @Override
    public boolean testServer() {
        return TESTSERVER;
    }
}

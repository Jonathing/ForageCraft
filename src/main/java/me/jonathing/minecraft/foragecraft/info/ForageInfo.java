package me.jonathing.minecraft.foragecraft.info;

import me.jonathing.minecraft.foragecraft.asm.MixinConnector;

/**
 * Contains various important pieces of information about the instance of ForageCraft. Special thanks to Shadew for
 * allowing easy usage of this via his ModUtil plugin and the boilerplate we made for the Midnight.
 *
 * @author Jonathing
 * @see IForageInfo
 * @see DynamicConstant
 * @since 2.0.0
 */
public final class ForageInfo implements IForageInfo
{
    public static final ForageInfo INSTANCE = new ForageInfo();

    /**
     * The Mod ID of ForageCraft, which is fixed to {@code foragecraft}.
     *
     * @see #modId()
     */
    public static final String MOD_ID = "foragecraft";

    /**
     * The Mod Name of ForageCraft, which is fixed to 'ForageCraft'.
     *
     * @see #name()
     */
    public static final String NAME = "ForageCraft";

    /**
     * This constant is true when the system property {@code foragecraft.iside} is {@code "true"}. This property is set
     * in all run configurations gradle.
     *
     * @see #isRunningFromIDE()
     * @see #ide()
     */
    public static final boolean IDE = isRunningFromIDE();

    /**
     * This constant is true when the system property {@code foragecraft.devmixins} is "true".
     *
     * @see #wantsDevMixins()
     * @see #forceDevMixins()
     * @see MixinConnector#connect()
     */
    public static final boolean FORCE_DEV_MIXINS = wantsDevMixins();

    /**
     * This constant is true when the system property {@code foragecraft.datagen} is {@code "true"}. This property is set
     * in the {@code data} run configration (for the {@code runData} task).
     *
     * @see #isRunningDatagen()
     * @see #data()
     */
    public static final boolean DATAGEN = isRunningDatagen();

    /**
     * This constant is true when the system property {@code foragecraft.istestserver} is {@code "true"}. This property is
     * set in the {@code testserver} run configration (for the {@code runTestServer} task).
     *
     * @see #isRunningTestServer()
     * @see #testServer()
     */
    public static final boolean TESTSERVER = isRunningTestServer();

    /**
     * The version of the ForageCraft (ex. {@code 2.0.0}), which is dynamically injected on build. Defaults to {@code
     * NOT.A.VERSION}.
     *
     * @see #version()
     */
    @DynamicConstant("version")
    public static final String VERSION = "NOT.A.VERSION";

    /**
     * The version name of ForageCraft (ex. 'ForageCraft: Reborn'), which is dynamically injected on build.
     * Defaults to 'Not A Version'.
     *
     * @see #versionName()
     */
    @DynamicConstant("version_name")
    public static final String VERSION_NAME = "Not A Version";

    /**
     * The build time of ForageCraft, which is dynamically injected on build. Defaults to {@code 2038-01-19T03:14:08Z},
     * referring to the <a href="https://en.wikipedia.org/wiki/Year_2038_problem">2038 Problem</a>.
     *
     * @see #buildDate()
     * @see <a href="https://en.wikipedia.org/wiki/Year_2038_problem">The 2038 Problem</a>
     */
    @DynamicConstant("build_time")
    public static final String BUILD_DATE = "2038-01-19T03:14:08Z";

    private ForageInfo()
    {
    }

    /**
     * @see #IDE
     */
    private static boolean isRunningFromIDE()
    {
        String p = System.getProperty("foragecraft.iside");
        return Boolean.parseBoolean(p);
    }

    /**
     * @see #FORCE_DEV_MIXINS
     */
    private static boolean wantsDevMixins()
    {
        String p = System.getProperty("foragecraft.devmixins");
        return Boolean.parseBoolean(p);
    }

    /**
     * @see #DATAGEN
     */
    private static boolean isRunningDatagen()
    {
        String p = System.getProperty("foragecraft.datagen");
        return Boolean.parseBoolean(p);
    }

    /**
     * @see #TESTSERVER
     */
    private static boolean isRunningTestServer()
    {
        String p = System.getProperty("foragecraft.istestserver");
        return Boolean.parseBoolean(p);
    }

    /**
     * @return {@link #MOD_ID}
     * @see IForageInfo#modId()
     */
    @Override
    public String modId()
    {
        return MOD_ID;
    }

    /**
     * @return {@link #NAME}
     * @see IForageInfo#name()
     */
    @Override
    public String name()
    {
        return NAME;
    }

    /**
     * @return {@link #VERSION}
     * @see IForageInfo#version()
     */
    @Override
    public String version()
    {
        return VERSION;
    }

    /**
     * @return {@link #VERSION_NAME}
     * @see IForageInfo#versionName()
     */
    @Override
    public String versionName()
    {
        return VERSION_NAME;
    }

    /**
     * @return {@link #BUILD_DATE}
     * @see IForageInfo#buildDate()
     */
    @Override
    public String buildDate()
    {
        return BUILD_DATE;
    }

    /**
     * @return {@link #FORCE_DEV_MIXINS}
     * @see IForageInfo#forceDevMixins()
     */
    @Override
    public boolean forceDevMixins()
    {
        return FORCE_DEV_MIXINS;
    }

    /**
     * @return {@link #IDE}
     * @see IForageInfo#ide()
     */
    @Override
    public boolean ide()
    {
        return IDE;
    }

    /**
     * @return {@link #DATAGEN}
     * @see IForageInfo#data()
     */
    @Override
    public boolean data()
    {
        return DATAGEN;
    }

    /**
     * @return {@link #TESTSERVER}
     * @see IForageInfo#testServer()
     */
    @Override
    public boolean testServer()
    {
        return TESTSERVER;
    }
}

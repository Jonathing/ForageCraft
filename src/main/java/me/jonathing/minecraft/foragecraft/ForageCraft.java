package me.jonathing.minecraft.foragecraft;

import me.jonathing.minecraft.foragecraft.client.ForageClient;
import me.jonathing.minecraft.foragecraft.common.compat.ForageModCompat;
import me.jonathing.minecraft.foragecraft.common.config.ForageCraftConfig;
import me.jonathing.minecraft.foragecraft.common.handler.GeneralEventHandler;
import me.jonathing.minecraft.foragecraft.common.registry.ForageCapabilities;
import me.jonathing.minecraft.foragecraft.common.registry.ForageFeatures;
import me.jonathing.minecraft.foragecraft.common.registry.ForageRegistry;
import me.jonathing.minecraft.foragecraft.common.registry.ForageTriggers;
import me.jonathing.minecraft.foragecraft.data.ForageCraftData;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * The main class of ForageCraft containing a bunch of initialization methods.
 *
 * @author Jonathing
 * @since 2.0.0
 */
@Mod(ForageInfo.MOD_ID)
public class ForageCraft
{
    public static final Logger LOGGER = LogManager.getLogger("ForageCraft");
    private static final Marker MARKER = MarkerManager.getMarker("Init");

    /**
     * Public constructor for ForageCraft. The initial spark which begins all of the registration processes.
     */
    public ForageCraft() // the Magic School Bus returns, bitch.
    {
        // Print info to debug or IDE console
        printInfo();

        // Register configs
        LOGGER.debug(MARKER, "Registering configs");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ForageCraftConfig.SERVER_SPEC);

        // Register event listeners
        LOGGER.debug(MARKER, "Registering event listeners");
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forge = MinecraftForge.EVENT_BUS;
        ForageCraft.addEventListeners(mod, forge);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ForageClient.addEventListeners(mod, forge));
        ForageRegistry.addEventListeners(mod, forge);
        GeneralEventHandler.addEventListeners(mod, forge);
        ForageCraftData.addEventListeners(mod, forge);
    }

    private static void addEventListeners(IEventBus mod, IEventBus forge)
    {
        mod.addListener(ForageCraft::commonSetup);
    }

    /**
     * Runs methods that are designed to be run on the {@link FMLCommonSetupEvent}.
     *
     * @param event The modloader common setup event to use for common setup.
     */
    private static void commonSetup(final FMLCommonSetupEvent event)
    {
        ForageFeatures.init();
        ForageTriggers.init();
        ForageModCompat.init();
        ForageCapabilities.init();
    }

    /**
     * @param key The key to search the resource for.
     * @return A new {@link ResourceLocation} under ForageCraft's mod id.
     */
    public static ResourceLocation locate(String key)
    {
        return new ResourceLocation(ForageInfo.MOD_ID + ":" + key);
    }

    /**
     * @param key The key to search the resource for.
     * @return A {@link String} containing a potential {@link ResourceLocation}.
     */
    public static String find(String key)
    {
        return ForageInfo.MOD_ID + ":" + key;
    }

    /**
     * Prints information about ForageCraft to the console. Is limited when in a normal environment.
     * <p>
     * Normal:
     * <pre>
     *     Initializing ForageCraft. See the debug log for build information.
     * </pre>
     * <p>
     * Debug:
     * <pre>
     *     ForageCraft Build Information
     *     - Version:     2.0.0 - ForageCraft: Reborn
     *     - Build Date:  2038-01-19T03:14:08Z
     *     - Dist:        CLIENT
     *     - Environment: Normal
     * </pre>
     */
    private void printInfo()
    {
        LOGGER.info(MARKER, String.format("Initializing %s.%s", ForageInfo.NAME, !ForageInfo.IDE ? " See the debug log for build information." : ""));

        if (ForageInfo.IDE)
        {
            LOGGER.info(MARKER, String.format("%s Build Information", ForageInfo.NAME));
            LOGGER.info(MARKER, String.format(" - Version:     %s - %s", ForageInfo.VERSION, ForageInfo.VERSION_NAME));
            LOGGER.info(MARKER, String.format(" - Build Date:  %s", ForageInfo.BUILD_DATE));
            LOGGER.info(MARKER, String.format(" - Dist:        %s", ForageInfo.DATAGEN ? "DATAGEN" : FMLEnvironment.dist.toString()));
            LOGGER.info(MARKER, String.format(" - Environment: %s", ForageInfo.TESTSERVER ? "GitHub Actions Test Server" : "IDE/Gradle"));
        }
        else
        {
            LOGGER.debug(MARKER, String.format("%s Build Information", ForageInfo.NAME));
            LOGGER.debug(MARKER, String.format(" - Version:     %s - %s", ForageInfo.VERSION, ForageInfo.VERSION_NAME));
            LOGGER.debug(MARKER, String.format(" - Build Date:  %s", ForageInfo.BUILD_DATE));
            LOGGER.debug(MARKER, String.format(" - Dist:        %s", FMLEnvironment.dist.toString()));
            LOGGER.debug(MARKER, " - Environment: Normal");
        }
    }
}

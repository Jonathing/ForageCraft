package me.jonathing.minecraft.foragecraft;

import me.jonathing.minecraft.foragecraft.client.ForageCraftClient;
import me.jonathing.minecraft.foragecraft.common.registry.ForageRegistry;
import me.jonathing.minecraft.foragecraft.common.security.VerificationUtil;
import me.jonathing.minecraft.foragecraft.data.ForageCraftDataGen;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForageInfo.MOD_ID)
public class ForageCraft
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger("ForageCraft");

    public ForageCraft()
    {
        printInfo();

        // Get event buses
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forge = MinecraftForge.EVENT_BUS;

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> mod.addListener(ForageCraftClient::clientSetup));

        // Register the events
        mod.register(ForageCraftDataGen.class);
        mod.addListener(ForageCraft::commonSetup);
        mod.register(ForageRegistry.class);
    }

    private void printInfo()
    {
        LOGGER.info(String.format("Initializing %s.%s", ForageInfo.NAME, !ForageInfo.IDE ? " See the debug log for build information." : ""));

        LOGGER.info("Expected SHA256 is " + ForageInfo.EXPECTED_SHA256);

        if (ForageInfo.IDE)
        {
            LOGGER.info(String.format("%s Build Information", ForageInfo.NAME));
            LOGGER.info(String.format(" - Version:     %s - %s", ForageInfo.VERSION, ForageInfo.VERSION_NAME));
            LOGGER.info(String.format(" - Build Date:  %s", ForageInfo.BUILD_DATE));
            LOGGER.info(String.format(" - Dist:        %s", ForageInfo.DATAGEN ? "DATAGEN" : FMLEnvironment.dist.toString()));
            LOGGER.info(String.format(" - Environment: %s", ForageInfo.TESTSERVER ? "GitHub Actions Test Server" : "IDE/Gradle"));
        }
        else
        {
            LOGGER.debug(String.format("%s Build Information", ForageInfo.NAME));
            LOGGER.debug(String.format(" - Version:     %s - %s", ForageInfo.VERSION, ForageInfo.VERSION_NAME));
            LOGGER.debug(String.format(" - Build Date:  %s", ForageInfo.BUILD_DATE));
            LOGGER.debug(String.format(" - Dist:        %s", FMLEnvironment.dist.toString()));
            LOGGER.debug(" - Environment: Normal");
        }
    }

    public static void commonSetup(final FMLCommonSetupEvent event)
    {
        VerificationUtil.validateMod(ForageInfo.MOD_ID, ForageInfo.EXPECTED_SHA256);
    }

    public static ResourceLocation locate(String key)
    {
        return new ResourceLocation(ForageInfo.MOD_ID + ":" + key);
    }

    public static String find(String key)
    {
        return ForageInfo.MOD_ID + ":" + key;
    }
}

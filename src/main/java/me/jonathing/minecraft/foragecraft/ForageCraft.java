package me.jonathing.minecraft.foragecraft;

import me.jonathing.minecraft.foragecraft.client.ForageCraftClient;
import me.jonathing.minecraft.foragecraft.common.registry.ForageRegistry;
import me.jonathing.minecraft.foragecraft.data.ForageCraftDataGen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForageCraft.MOD_ID)
public class ForageCraft
{
    public static final String MOD_ID = "foragecraft";
    public static final String NAME = "ForageCraft";
    public static final String VERSION = "2.0.0-pre1";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger("ForageCraft");

    public ForageCraft()
    {
        // Get event buses
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forge = MinecraftForge.EVENT_BUS;

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
        {
            mod.addListener(ForageCraftClient::clientSetup);
        });

        // Register the events
        mod.register(ForageCraftDataGen.class);
        mod.addListener(ForageCraft::commonSetup);
        mod.register(ForageRegistry.class);
    }

    public static void commonSetup(final FMLCommonSetupEvent event)
    {
        if (isRunningFromIDE())
        {
            LOGGER.warn("ForageCraft is being run from an IDE or via Gradle.");
        }
    }

    public static boolean isRunningFromIDE()
    {
        String property = System.getProperty("foragecraft.iside");
        return Boolean.parseBoolean(property);
    }

    public static ResourceLocation locate(String key)
    {
        return new ResourceLocation(MOD_ID + ":" + key);
    }

    public static String find(String key)
    {
        return MOD_ID + ":" + key;
    }
}
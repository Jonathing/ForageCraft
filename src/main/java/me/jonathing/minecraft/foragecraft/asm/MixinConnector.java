package me.jonathing.minecraft.foragecraft.asm;

import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

/**
 * This class uses an {@link IMixinConnector} to connect our mixins to the environment. If we detect that we are in
 * a development environment ({@link ForageInfo#IDE}) or if development mixins have forcefully been enabled
 * ({@link ForageInfo#FORCE_DEV_MIXINS}), we will ask mixin to use the development mixins as well.
 *
 * @author Jonathing
 * @see IMixinConnector
 * @since 2.0.0
 */
public class MixinConnector implements IMixinConnector
{
    private static final Logger LOGGER = LogManager.getLogger("ForageCraft Mixin Connector");

    /**
     * Connects our mixins to the environment.
     *
     * @see MixinConnector
     * @see IMixinConnector#connect()
     */
    @Override
    public void connect()
    {
        LOGGER.info("Connecting ForageCraft mixins...");
        Mixins.addConfiguration(ForageInfo.MOD_ID + ".mixins.json");
        LOGGER.info("ForageCraft mixins connected successfully!");

        if (ForageInfo.IDE || ForageInfo.FORCE_DEV_MIXINS)
        {
            LOGGER.info("Connecting ForageCraft dev-environment mixins...");
            Mixins.addConfiguration(ForageInfo.MOD_ID + ".dev.mixins.json");
            LOGGER.info("ForageCraft dev-environment mixins connected successfully!");
        }
    }
}

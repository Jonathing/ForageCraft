package me.jonathing.minecraft.foragecraft.asm;

import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraftforge.fml.StartupMessageManager;
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
    public static final Logger LOGGER = LogManager.getLogger("ForageCraft Mixins");

    /**
     * Connects ForageCraft's mixins to the environment. The workspace is configured to use this in the IDE since it is
     * manually specified in the {@code MANIFEST.MF} file in the {@code META-INF} folder.
     *
     * @see MixinConnector
     * @see IMixinConnector#connect()
     */
    @Override
    public void connect()
    {
        Mixins.addConfiguration(ForageInfo.MOD_ID + ".mixins.json");
        StartupMessageManager.addModMessage("Connecting ForageCraft mixins");

        if (ForageInfo.IDE || ForageInfo.FORCE_DEV_MIXINS)
        {
            Mixins.addConfiguration(ForageInfo.MOD_ID + ".dev.mixins.json");
            LOGGER.warn("ForageCraft's dev-environment mixins have been enabled.");
        }
    }
}

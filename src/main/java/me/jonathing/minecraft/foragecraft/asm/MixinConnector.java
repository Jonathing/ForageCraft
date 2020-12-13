package me.jonathing.minecraft.foragecraft.asm;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector
{
    private static final Logger LOGGER = LogManager.getLogger("ForageCraft Mixin Connector");

    @Override
    public void connect()
    {
        LOGGER.info("Connecting ForageCraft mixins...");
        Mixins.addConfiguration(ForageInfo.MOD_ID + ".mixins.json");
        LOGGER.info("ForageCraft mixins connected successfully!");

        if (ForageInfo.IDE || ForageInfo.FORCE_DEV_MIXINS)
        {
            LOGGER.warn("Connecting dev-environment mixins...");
            Mixins.addConfiguration(ForageInfo.MOD_ID + ".dev.mixins.json");
            LOGGER.info("ForageCraft dev-environment mixins connected successfully!");
        }
    }
}

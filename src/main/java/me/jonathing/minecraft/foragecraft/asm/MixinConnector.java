package me.jonathing.minecraft.foragecraft.asm;

import me.jonathing.minecraft.foragecraft.ForageCraft;
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
        LOGGER.info("Connecting ForageCraft to Mixin...");
        Mixins.addConfiguration(ForageCraft.MOD_ID + ".mixins.json");
        LOGGER.info("ForageCraft connected to Mixin successfully!");
    }
}

package me.jonathing.minecraft.foragecraft.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ForageCraftConfig
{
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ServerConfig SERVER;

    static
    {
        {
            Pair<ServerConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
            SERVER = pair.getLeft();
            SERVER_SPEC = pair.getRight();
        }
    }

    public static class ServerConfig
    {
        private final ForgeConfigSpec.ConfigValue<Integer> unsuccessfulForagingCooldown;
        private final ForgeConfigSpec.ConfigValue<Integer> successfulForagingCooldown;
        private final ForgeConfigSpec.ConfigValue<Integer> maxForagesPerChunk;

        public ServerConfig(ForgeConfigSpec.Builder builder)
        {
            builder.comment("ForageCraft world config");
            this.unsuccessfulForagingCooldown = builder
                    .comment("The amount of time (in seconds) a player cannot forage even if it was unsuccessful.")
                    .define("unsuccessful_foraging_cooldown", 1);
            this.successfulForagingCooldown = builder
                    .comment("The amount of time (in seconds) a player cannot forage after a successful attempt.")
                    .define("successful_foraging_cooldown", 7);
            this.maxForagesPerChunk = builder
                    .comment("The maximum amount of forages allowed per chunk.")
                    .define("max_forages_per_chunk", 10);
            builder.pop();
        }

        public int getUnsuccessfulForagingCooldown()
        {
            return unsuccessfulForagingCooldown.get();
        }

        public int getSuccessfulForagingCooldown()
        {
            return successfulForagingCooldown.get();
        }

        public int getMaxForagesPerChunk()
        {
            return this.maxForagesPerChunk.get();
        }
    }
}

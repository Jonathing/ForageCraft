package me.jonathing.minecraft.foragecraft.common.registry;

import com.google.common.collect.Maps;
import me.jonathing.minecraft.foragecraft.common.trigger.ForagingTrigger;
import me.jonathing.minecraft.foragecraft.common.trigger.LeekTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Map;

import static me.jonathing.minecraft.foragecraft.ForageCraft.LOGGER;

/**
 * This class holds all of the custom advancement {@link CriteriaTriggers} in ForageCraft.
 *
 * @author Jonathing
 * @since 2.1.0
 */
public class ForageTriggers
{
    private static final Marker MARKER = MarkerManager.getMarker("Triggers");

    /**
     * This is a map that holds all of the triggers registered by ForageCraft only.
     *
     * @see #all()
     */
    private static final Map<ResourceLocation, ICriterionTrigger<?>> FORAGE_TRIGGERS_MAP = Maps.newHashMap();

    public static final ForagingTrigger FORAGING_TRIGGER = register(new ForagingTrigger());
    public static final LeekTrigger LEEK_TRIGGER = register(new LeekTrigger());

    /**
     * This empty method makes sure that the {@link #FORAGING_TRIGGER} is registered into the game by initializing this
     * class.
     *
     * @see me.jonathing.minecraft.foragecraft.ForageCraft#commonSetup(FMLCommonSetupEvent)
     */
    public static void init()
    {
    }

    private static <T extends ICriterionTrigger<?>> T register(T criterion)
    {
        if (FORAGE_TRIGGERS_MAP.containsKey(criterion.getId()))
            throw new IllegalArgumentException("Duplicate criterion id " + criterion.getId());

        LOGGER.debug(MARKER, "Registering new criteria trigger `{}`", criterion.getId());
        FORAGE_TRIGGERS_MAP.put(criterion.getId(), criterion);
        return CriteriaTriggers.register(criterion);
    }

    /**
     * @return All of the values of {@link #FORAGE_TRIGGERS_MAP} in an {@link Iterable}.
     * @see CriteriaTriggers#all()
     * @see #FORAGE_TRIGGERS_MAP
     */
    public static Iterable<? extends ICriterionTrigger<?>> all()
    {
        return FORAGE_TRIGGERS_MAP.values();
    }
}

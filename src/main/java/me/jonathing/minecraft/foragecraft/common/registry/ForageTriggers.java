package me.jonathing.minecraft.foragecraft.common.registry;

import com.google.common.collect.Maps;
import me.jonathing.minecraft.foragecraft.common.handler.trigger.ForagingTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class ForageTriggers
{
    private static final Map<ResourceLocation, ICriterionTrigger<?>> FORAGE_TRIGGERS_MAP = Maps.newHashMap();
    public static final ForagingTrigger FORAGING_TRIGGER = CriteriaTriggers.register(new ForagingTrigger());

    /**
     * @see CriteriaTriggers#getAll()
     * @return All of the values of {@link #FORAGE_TRIGGERS_MAP} in an {@link Iterable}.
     */
    public static Iterable<? extends ICriterionTrigger<?>> getAll() {
        return FORAGE_TRIGGERS_MAP.values();
    }
}

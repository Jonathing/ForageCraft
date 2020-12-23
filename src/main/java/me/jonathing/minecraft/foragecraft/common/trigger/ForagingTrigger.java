package me.jonathing.minecraft.foragecraft.common.trigger;

import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.util.JsonUtil;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * This is the custom trigger that is exclusive to forage drops.
 *
 * @author Jonathing
 * @see me.jonathing.minecraft.foragecraft.common.handler.ForagingEventHandler#forageDrop(List, BlockEvent.BreakEvent)
 * @since 2.1.0
 */
@MethodsReturnNonnullByDefault
public class ForagingTrigger extends AbstractCriterionTrigger<ForagingTrigger.Instance>
{
    private static final ResourceLocation ID = ForageCraft.locate("foraging_trigger");

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser)
    {
        Block block = JsonUtil.Reader.getBlock(json);
        Item item = JsonUtil.Reader.getItem(json);
        return new Instance(entityPredicate, block, item);
    }

    /**
     * Triggers the {@link ForagingTrigger} with a specific {@link Block} and {@link Item} along with the responsible
     * {@link ServerPlayerEntity}.
     *
     * @param playerEntity The {@link ServerPlayerEntity} that caused the trigger via a forage drop.
     * @param block        The {@link Block} that the player broke.
     * @param item         The {@link Item} that was foraged from the broken block.
     * @see me.jonathing.minecraft.foragecraft.common.handler.ForagingEventHandler#forageDrop(List, BlockEvent.BreakEvent)
     */
    public void trigger(ServerPlayerEntity playerEntity, Block block, Item item)
    {
        LogManager.getLogger().debug("Triggering the foraging trigger with block `" + block + "` and item `" + item + "`.");
        this.triggerListeners(playerEntity, (instance) -> instance.test(block, item));
    }

    /**
     * This static inner class contains information about an instance of a {@link ForagingTrigger}. This is mainly used
     * for advancements. See the {@code accept()} method in {@code ForageAdvancements} class in
     * {@link me.jonathing.minecraft.foragecraft.data.provider.ForageAdvancementProvider}.
     */
    @MethodsReturnNonnullByDefault
    public static class Instance extends CriterionInstance
    {
        private final Block block;
        private final Item item;

        public Instance(EntityPredicate.AndPredicate entityPredicate, @Nonnull Block block, @Nonnull Item item)
        {
            super(ID, entityPredicate);
            this.block = block;
            this.item = item;
        }

        /**
         * Is used by {@link #trigger(ServerPlayerEntity, Block, Item)} to test if the trigger's {@link Block} and
         * {@link Item} match that of this {@link Instance}'s.
         *
         * @param block The trigger's {@link Block}.
         * @param item  The trigger's {@link Item}.
         * @return The result of the test.
         * @see #trigger(ServerPlayerEntity, Block, Item)
         */
        protected boolean test(Block block, Item item)
        {
            if (block == null || item == null) return false;

            return this.block.equals(block) && this.item.equals(item);
        }

        /**
         * Creates a raw {@link Instance} for use in data generation.
         *
         * @param block The {@link Block} to be used for the instance.
         * @param item  The {@link Item} to be used for the instance.
         * @return The created {@link Instance} with the given parameters.
         * @see Instance
         */
        @ParametersAreNonnullByDefault
        public static Instance create(Block block, Item item)
        {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, block, item);
        }

        @Override
        public JsonObject serialize(@Nonnull ConditionArraySerializer conditionSerializer)
        {
            JsonObject jsonObject = super.serialize(conditionSerializer);
            JsonUtil.Writer.writeBlock(jsonObject, block);
            JsonUtil.Writer.writeItem(jsonObject, item);
            return jsonObject;
        }
    }
}

package me.jonathing.minecraft.foragecraft.common.trigger;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;

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
    private static final String BLOCK_KEY = "block";
    private static final String ITEM_KEY = "item";

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected Instance createInstance(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser)
    {
        Block block = JsonUtil.Reader.getBlock(json, BLOCK_KEY).orElseThrow(() -> new JsonSyntaxException("Unknown block type!"));
        IItemProvider item = JsonUtil.Reader.getItem(json, ITEM_KEY).orElseThrow(() -> new JsonSyntaxException("Unknown item type!"));
        return new Instance(entityPredicate, block, item);
    }

    /**
     * Triggers the {@link ForagingTrigger} with a specific {@link Block} and {@link IItemProvider} along with the responsible
     * {@link ServerPlayerEntity}.
     *
     * @param playerEntity The {@link ServerPlayerEntity} that caused the trigger via a forage drop.
     * @param block        The {@link Block} that the player broke.
     * @param item         The {@link Item} that was foraged from the broken block.
     * @see me.jonathing.minecraft.foragecraft.common.handler.ForagingEventHandler#forageDrop(List, BlockEvent.BreakEvent)
     */
    public void trigger(ServerPlayerEntity playerEntity, Block block, IItemProvider item)
    {
        this.trigger(playerEntity, (instance) -> instance.test(block, item));
    }

    /**
     * This static inner class contains information about an instance of a {@link ForagingTrigger}. This is mainly used
     * for advancements. See the {@code accept()} method in {@code ForageAdvancements} class in
     * {@link me.jonathing.minecraft.foragecraft.data.provider.ForageAdvancementProvider}.
     */
    public static class Instance extends CriterionInstance
    {
        private final Block block;
        private final IItemProvider item;

        public Instance(EntityPredicate.AndPredicate entityPredicate, @Nonnull Block block, @Nonnull IItemProvider item)
        {
            super(ID, entityPredicate);
            this.block = block;
            this.item = item;
        }

        /**
         * Is used by {@link #trigger(ServerPlayerEntity, Block, IItemProvider)} to test if the trigger's {@link Block} and
         * {@link Item} match that of this {@link Instance}'s.
         *
         * @param block The trigger's {@link Block}.
         * @param item  The trigger's {@link Item}.
         * @return The result of the test.
         * @see #trigger(ServerPlayerEntity, Block, IItemProvider)
         */
        protected boolean test(Block block, IItemProvider item)
        {
            if (block == null || item == null) return false;

            return this.block.equals(block) && this.item.asItem().equals(item.asItem());
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
        public static Instance create(Block block, IItemProvider item)
        {
            return new Instance(EntityPredicate.AndPredicate.ANY, block, item);
        }

        @Override
        public JsonObject serializeToJson(@Nonnull ConditionArraySerializer conditionSerializer)
        {
            JsonObject jsonObject = super.serializeToJson(conditionSerializer);
            JsonUtil.Writer.writeBlock(jsonObject, block, BLOCK_KEY);
            JsonUtil.Writer.writeItem(jsonObject, item, ITEM_KEY);
            return jsonObject;
        }
    }
}

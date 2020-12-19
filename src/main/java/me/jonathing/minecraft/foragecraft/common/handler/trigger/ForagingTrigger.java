package me.jonathing.minecraft.foragecraft.common.handler.trigger;

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

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

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
    protected Instance conditionsFromJson(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser)
    {
        Block block = JsonUtil.Reader.getBlock(json);
        Item item = JsonUtil.Reader.getItem(json);
        return new Instance(entityPredicate, block, item);
    }

    public void trigger(ServerPlayerEntity playerEntity, Block block, Item item)
    {
        this.test(playerEntity, (instance) -> instance.test(block, item));
    }

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

        protected boolean test(Block block, Item item)
        {
            if (block == null || item == null) return false;

            return this.block.equals(block) && this.item.equals(item);
        }

        @ParametersAreNonnullByDefault
        public static Instance create(Block block, Item item)
        {
            return new Instance(EntityPredicate.AndPredicate.EMPTY, block, item);
        }

        @Override
        public JsonObject toJson(@Nonnull ConditionArraySerializer conditionSerializer)
        {
            JsonObject jsonObject = super.toJson(conditionSerializer);
            JsonUtil.Writer.writeBlock(jsonObject, block);
            JsonUtil.Writer.writeItem(jsonObject, item);
            return jsonObject;
        }
    }
}

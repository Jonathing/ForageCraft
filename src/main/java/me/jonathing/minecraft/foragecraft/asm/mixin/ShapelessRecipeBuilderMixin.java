package me.jonathing.minecraft.foragecraft.asm.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

/**
 * This mixin class is used to make certain modifications to the {@link ShapelessRecipeBuilder} class to ForageCraft's
 * needs. It is only used in datagen and there are checks in place to make sure that they only affect ForageCraft's
 * data generation.
 *
 * @author Jonathing
 * @see ShapelessRecipeBuilder
 * @since 2.1.0
 */
@Mixin(ShapelessRecipeBuilder.class)
public class ShapelessRecipeBuilderMixin
{
    /**
     * This mixin class is used to make certain modifications to the {@link ShapelessRecipeBuilder.Result} class, which
     * is an inner class of {@link ShapelessRecipeBuilder} to ForageCraft's needs. It is only used in datagen and there
     * are checks in place to make sure that they only affect ForageCraft's data generation.
     *
     * @author Jonathing
     * @see ShapelessRecipeBuilderMixin
     * @see ShapelessRecipeBuilder.Result
     * @since 2.1.0
     */
    @Mixin(ShapelessRecipeBuilder.Result.class)
    @SuppressWarnings("unused")
    private static class ResultMixin
    {
        /**
         * @see ShapelessRecipeBuilder.Result#result
         */
        @Shadow
        @Final
        private Item result;

        /**
         * @see ShapelessRecipeBuilder.Result#count
         */
        @Shadow
        @Final
        private int count;

        /**
         * @see ShapelessRecipeBuilder.Result#group
         */
        @Shadow
        @Final
        private String group;

        /**
         * @see ShapelessRecipeBuilder.Result#ingredients
         */
        @Shadow
        @Final
        private List<Ingredient> ingredients;

        /**
         * The way the {@link ShapelessRecipeBuilder} is written does not allow us to add NBT to our items by default.
         * In the case of the Patchouli book, the only way I am able to get my version of the book is with NBT, so it's
         * quite important that we take care of that. This mixin does exactly that. If it checks true that ForageCraft
         * is running datagen and the given {@link Item}'s key is "{@code patchouli:guide_book}", it fires its own
         * of the {@link ShapelessRecipeBuilder.Result#serialize(JsonObject)} method which includes this NBT block in
         * the generated JSON file.
         *
         * @param recipeJson The JSON file that will be modified to add the NBT block.
         * @param callback   Mixin's way of returning the method by cancelling it.
         * @see ShapelessRecipeBuilder.Result#serialize(JsonObject)
         * @see me.jonathing.minecraft.foragecraft.data.provider.ForageRecipeProvider#registerRecipes(Consumer)
         */
        @Inject(at = @At("HEAD"), method = "serialize(Lcom/google/gson/JsonObject;)V", cancellable = true)
        @SuppressWarnings("deprecation")
        private void serialize(JsonObject recipeJson, CallbackInfo callback)
        {
            if (ForageInfo.DATAGEN && Registry.ITEM.getKey(this.result).toString().equals("patchouli:guide_book"))
            {
                if (!this.group.isEmpty())
                {
                    recipeJson.addProperty("group", this.group);
                }

                JsonArray jsonarray = new JsonArray();

                for (Ingredient ingredient : this.ingredients)
                {
                    jsonarray.add(ingredient.serialize());
                }

                recipeJson.add("ingredients", jsonarray);
                JsonObject resultObject = new JsonObject();
                resultObject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
                if (this.count > 1)
                {
                    resultObject.addProperty("count", this.count);
                }

                JsonObject nbtObject = new JsonObject();
                nbtObject.addProperty("patchouli:book", "foragecraft:book");
                resultObject.add("nbt", nbtObject);

                recipeJson.add("result", resultObject);
                callback.cancel();
            }
        }
    }
}

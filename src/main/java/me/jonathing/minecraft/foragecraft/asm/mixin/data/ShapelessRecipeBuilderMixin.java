package me.jonathing.minecraft.foragecraft.asm.mixin.data;

import com.google.gson.JsonObject;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
         * This is used to check if the current recipe being made is the Patchouli book.
         *
         * @see ShapelessRecipeBuilder.Result#result
         * @see #modify$jsonObject(JsonObject)
         */
        @Shadow
        @Final
        private Item result;

        /**
         * The way the {@link ShapelessRecipeBuilder} is written does not allow us to add NBT to our items by default.
         * In the case of the Patchouli book, the only way I am able to get my version of the book is with NBT, so it's
         * quite important that we take care of that. That's what this is mixin for. This method hooks into a local
         * variable on its third call of {@link org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal} and checks
         * if ForageCraft is running datagen and the given {@link Item}'s key is "{@code patchouli:guide_book}". If that
         * condition is met, it modifies the local {@link JsonObject} variable containing the {@code "result"} object
         * which holds the {@code "code"} property to include a new JSON object that contains the NBT information for
         * the Patchouli book.
         *
         * @param jsonObject The {@link JsonObject} containing the {@code "result"} object that we will add our NBT JSON
         *                   object to.
         * @return The new {@link JsonObject} with the NBT object if the given conditions are met, or the original
         * JSON object otherwise.
         * @see ShapelessRecipeBuilder.Result#serializeRecipeData(JsonObject)
         */
        @ModifyVariable(
                method = "serializeRecipeData(Lcom/google/gson/JsonObject;)V",
                at = @At(
                        value = "LOAD",
                        ordinal = 2
                ),
                index = 3
        )
        @SuppressWarnings("deprecation")
        private JsonObject modify$jsonObject(JsonObject jsonObject)
        {
            if (ForageInfo.DATAGEN && Registry.ITEM.getKey(this.result).toString().equals("patchouli:guide_book"))
            {
                JsonObject nbtObject = new JsonObject();
                nbtObject.addProperty("patchouli:book", "foragecraft:book");
                jsonObject.add("nbt", nbtObject);
            }

            return jsonObject;
        }
    }
}

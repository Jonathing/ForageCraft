package me.jonathing.minecraft.foragecraft.asm.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.jonathing.minecraft.foragecraft.ForageCraft;
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
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

@Mixin(ShapelessRecipeBuilder.class)
public class ShapelessRecipeBuilderResultMixin
{
    @Mixin(ShapelessRecipeBuilder.Result.class)
    private static class ResultMixin
    {
        @Shadow @Final private Item result;
        @Shadow @Final private int count;
        @Shadow @Final private String group;
        @Shadow @Final private List<Ingredient> ingredients;

        @Inject(at = @At("HEAD"), method = "serialize(Lcom/google/gson/JsonObject;)V", cancellable = true)
        private void serialize(JsonObject recipeJson, CallbackInfo callback)
        {
            if (ForageInfo.DATAGEN && Registry.ITEM.getKey(this.result).toString().equals("patchouli:guide_book"))
            {
                if (!this.group.isEmpty()) {
                    recipeJson.addProperty("group", this.group);
                }

                JsonArray jsonarray = new JsonArray();

                for(Ingredient ingredient : this.ingredients) {
                    jsonarray.add(ingredient.serialize());
                }

                recipeJson.add("ingredients", jsonarray);
                JsonObject resultObject = new JsonObject();
                resultObject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
                if (this.count > 1) {
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

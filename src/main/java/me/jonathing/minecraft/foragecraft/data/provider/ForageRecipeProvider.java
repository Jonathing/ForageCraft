package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.gson.JsonObject;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;

import java.nio.file.Path;
import java.util.function.Consumer;

public class ForageRecipeProvider extends RecipeProvider
{
    private Consumer<IFinishedRecipe> consumer;
    private String hasItem = "has_item";

    public ForageRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        this.consumer = consumer;

        simpleInOut(Items.SKELETON_SKULL, Items.BONE_MEAL, 9);

        simple3x3withResourceLoc(ForageBlocks.rock, Items.COBBLESTONE);
        simpleInOut(Items.COBBLESTONE, ForageBlocks.rock, 9);

        simple3x3(ForageBlocks.flat_rock, ForageBlocks.paving_stones);

        simpleInOut(ForageBlocks.fascine, ForageItems.stick_bundle, 9);
        simple3x3(ForageItems.stick_bundle, ForageBlocks.fascine);

//        ShapedRecipeBuilder.shapedRecipe(ForageBlocks.scarecrow, 1)
//                .key('l', Items.LEATHER).key('p', Items.PUMPKIN).key('f', ForageItems.stick_bundle).key('b', ForageBlocks.straw_bale).key('s', Items.STICK)
//                .patternLine("lpl").patternLine("fbf").patternLine("lsl").addCriterion(hasItem, hasItem(Items.PUMPKIN)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ForageItems.spaghetti, 1)
                .key('s', Items.COOKED_BEEF).key('w', Items.WHEAT).key('b', Items.BOWL)
                .patternLine(" s ").patternLine("www").patternLine(" b ").addCriterion(hasItem, hasItem(Items.WHEAT)).build(consumer);

        simple3x3(Items.STICK, ForageItems.stick_bundle);
        simpleInOut(ForageItems.stick_bundle, Items.STICK, 9);

        simple3x3(ForageItems.straw, ForageBlocks.straw_bale);
        simpleInOut(ForageBlocks.straw_bale, ForageItems.straw, 9);
    }

    private void simpleInOut(IItemProvider in, IItemProvider out, int amount)
    {
        simpleInOut(in, out, amount, in);
    }

    private void simpleInOut(IItemProvider in, IItemProvider out, int amount, IItemProvider criterion)
    {
        ShapelessRecipeBuilder.shapelessRecipe(out, amount).addIngredient(in).addCriterion(hasItem, hasItem(criterion)).build(consumer, ForageCraft.find(out.asItem().getRegistryName().getPath() + "_from_" + in.asItem().getRegistryName().getPath()));;
    }

    private void simple3x3withResourceLoc(IItemProvider in, IItemProvider out, int amount)
    {
        ShapedRecipeBuilder.shapedRecipe(out, amount).key('#', in).patternLine("###").patternLine("###").patternLine("###").addCriterion(hasItem, hasItem(in)).build(consumer, ForageCraft.find(out.asItem().getRegistryName().getPath() + "_from_" + in.asItem().getRegistryName().getPath()));
    }

    private void simple3x3withResourceLoc(IItemProvider in, IItemProvider out)
    {
        simple3x3withResourceLoc(in, out, 1);
    }

    private void simple3x3(IItemProvider in, IItemProvider out, int amount)
    {
        ShapedRecipeBuilder.shapedRecipe(out, amount).key('#', in).patternLine("###").patternLine("###").patternLine("###").addCriterion(hasItem, hasItem(in)).build(consumer);
    }

    private void simple3x3(IItemProvider in, IItemProvider out)
    {
        simple3x3(in, out, 1);
    }

    @Override
    protected void saveRecipeAdvancement(DirectoryCache p_208310_0_, JsonObject p_208310_1_, Path p_208310_2_)
    {
        super.saveRecipeAdvancement(p_208310_0_, p_208310_1_, p_208310_2_);
    }

    @Override
    public String getName()
    {
        return "ForageCraft Recipes";
    }
}

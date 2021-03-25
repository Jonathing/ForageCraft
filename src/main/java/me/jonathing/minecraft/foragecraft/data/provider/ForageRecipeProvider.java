package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.gson.JsonObject;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * The provider for all of the recipes in ForageCraft.
 *
 * @author Jonathing
 * @author Silver_David
 * @see RecipeProvider
 * @since 2.0.0
 */
public class ForageRecipeProvider extends RecipeProvider
{
    private final String hasItem = "has_item";
    private Consumer<IFinishedRecipe> consumer;

    public ForageRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    /**
     * This method runs through all of the recipes within it and generates data based off of them.
     *
     * @see RecipeProvider#buildShapelessRecipes(Consumer)
     */
    @Override
    @ParametersAreNonnullByDefault
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
    {
        this.consumer = consumer;

        simpleInOut(Items.SKELETON_SKULL, Items.BONE_MEAL, 9);

        simple3x3withResourceLoc(ForageBlocks.rock, Items.COBBLESTONE);
        simpleInOut(Items.COBBLESTONE, ForageBlocks.rock, 9);

        simple3x3(ForageBlocks.flat_rock, ForageBlocks.paving_stones);

        simpleInOut(ForageBlocks.fascine, ForageItems.stick_bundle, 9);
        simple3x3(ForageItems.stick_bundle, ForageBlocks.fascine);

        // TODO: Scarecrow. Kill me.
//        ShapedRecipeBuilder.shapedRecipe(ForageBlocks.scarecrow, 1)
//                .key('l', Items.LEATHER).key('p', Items.PUMPKIN).key('f', ForageItems.stick_bundle).key('b', ForageBlocks.straw_bale).key('s', Items.STICK)
//                .patternLine("lpl").patternLine("fbf").patternLine("lsl").addCriterion(hasItem, hasItem(Items.PUMPKIN)).build(consumer);

        ShapedRecipeBuilder.shaped(ForageItems.spaghetti, 1)
                .define('s', Items.COOKED_BEEF).define('w', Items.WHEAT).define('b', Items.BOWL)
                .pattern(" s ").pattern("www").pattern(" b ").unlockedBy(hasItem, has(Items.WHEAT)).save(consumer);

        simple3x3(Items.STICK, ForageItems.stick_bundle);
        simpleInOut(ForageItems.stick_bundle, Items.STICK, 9);

        simple3x3(ForageItems.straw, ForageBlocks.straw_bale);
        simpleInOut(ForageBlocks.straw_bale, ForageItems.straw, 9);

        simpleInOut(ForageItems.leek, ForageItems.leek_seeds, 1);

        ShapedRecipeBuilder.shaped(ForageItems.gathering_knife, 1)
                .define('s', Items.STICK).define('r', ForageBlocks.flat_rock)
                .pattern(" r").pattern("s ").unlockedBy(hasItem, has(ForageBlocks.flat_rock.asItem())).save(consumer);

        ShapelessRecipeBuilder.shapeless(PatchouliAPI.get().getBookStack(ForageCraft.locate("book")).getItem(), 1)
                .requires(Items.PAPER)
                .requires(Items.DIRT, 3)
                .unlockedBy(hasItem, has(Blocks.DIRT))
                .save(consumer, ForageCraft.find("guide_book"));

        ShapelessRecipeBuilder.shapeless(Items.STICK, 1)
                .requires(ItemTags.SAPLINGS)
                .requires(ForageItems.gathering_knife)
                .unlockedBy(hasItem, has(ForageItems.gathering_knife))
                .save(consumer, ForageCraft.find("stick_from_gathering_knife_and_sapling"));
    }

    private void simpleInOut(IItemProvider in, IItemProvider out, int amount)
    {
        simpleInOut(in, out, amount, in);
    }

    private void simpleInOut(IItemProvider in, IItemProvider out, int amount, IItemProvider criterion)
    {
        ShapelessRecipeBuilder.shapeless(out, amount)
                .requires(in)
                .unlockedBy(hasItem, has(criterion))
                .save(consumer, ForageCraft.find(out.asItem().getRegistryName().getPath() + "_from_" + in.asItem().getRegistryName().getPath()));
    }

    private void simple3x3withResourceLoc(IItemProvider in, IItemProvider out, int amount)
    {
        ShapedRecipeBuilder.shaped(out, amount).define('#', in).pattern("###").pattern("###").pattern("###").unlockedBy(hasItem, has(in)).save(consumer, ForageCraft.find(out.asItem().getRegistryName().getPath() + "_from_" + in.asItem().getRegistryName().getPath()));
    }

    private void simple3x3withResourceLoc(IItemProvider in, IItemProvider out)
    {
        simple3x3withResourceLoc(in, out, 1);
    }

    private void simple3x3(IItemProvider in, IItemProvider out, int amount)
    {
        ShapedRecipeBuilder.shaped(out, amount).define('#', in).pattern("###").pattern("###").pattern("###").unlockedBy(hasItem, has(in)).save(consumer);
    }

    private void simple3x3(IItemProvider in, IItemProvider out)
    {
        simple3x3(in, out, 1);
    }

    @Override
    protected void saveAdvancement(DirectoryCache p_208310_0_, JsonObject p_208310_1_, Path p_208310_2_)
    {
        super.saveAdvancement(p_208310_0_, p_208310_1_, p_208310_2_);
    }

    @Override
    @Nonnull
    public String getName()
    {
        return "ForageCraft Recipes";
    }
}

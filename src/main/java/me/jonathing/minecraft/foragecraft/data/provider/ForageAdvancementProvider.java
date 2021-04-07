package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import me.jonathing.minecraft.foragecraft.common.trigger.ForagingTrigger;
import me.jonathing.minecraft.foragecraft.common.trigger.LeekTrigger;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The provider for all of the advancements in ForageCraft.
 *
 * @author Jonathing
 * @author Silver_David
 * @see IDataProvider
 * @since 2.1.0
 */
public class ForageAdvancementProvider implements IDataProvider
{
    private static final Marker MARKER = MarkerManager.getMarker("AdvancementProvider");
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    private final List<Consumer<Consumer<Advancement>>> advancements = ImmutableList.of(new ForageAdvancements());

    public ForageAdvancementProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@Nonnull DirectoryCache cache) throws IOException
    {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> advancement = (a) ->
        {
            if (!set.add(a.getId()))
            {
                throw new IllegalStateException("Duplicate advancement " + a.getId());
            }
            else
            {
                Path path1 = getPath(path, a);

                try
                {
                    IDataProvider.save(GSON, cache, a.deconstruct().serializeToJson(), path1);
                }
                catch (IOException ioexception)
                {
                    ForageCraft.LOGGER.error(MARKER, String.format("Couldn't save advancement %s", path1), ioexception);
                }

            }
        };

        for (Consumer<Consumer<Advancement>> adv : this.advancements)
        {
            adv.accept(advancement);
        }
    }

    private static Path getPath(Path path, Advancement advancement)
    {
        return path.resolve("data/" + advancement.getId().getNamespace() + "/advancements/" + advancement.getId().getPath() + ".json");
    }

    @Override
    @Nonnull
    public String getName()
    {
        return "ForageCraft Advancements";
    }

    private class ForageAdvancements implements Consumer<Consumer<Advancement>>
    {
        private String section = "";

        @Override
        @SuppressWarnings("unused")
        public void accept(Consumer<Advancement> consumer)
        {
            // ForageCraft advancements
            Advancement root = builder(ForageItems.stick_bundle, "root", ForageCraft.locate("textures/block/fascine_side.png"), FrameType.TASK, false, false, false)
                    .addCriterion("has_" + Blocks.DIRT.asItem().getRegistryName().getPath(), InventoryChangeTrigger.Instance.hasItems(Blocks.DIRT.asItem()))
                    .save(consumer, ForageCraft.find("main/root"));

            Advancement root_vegetable = builder(Items.POTATO, "foraged_root_vegetable", FrameType.TASK, true, false, false)
                    .parent(root)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("foraged_potato_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.POTATO))
                    .addCriterion("foraged_potato_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.POTATO))
                    .addCriterion("foraged_carrot_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.CARROT))
                    .addCriterion("foraged_beetroot_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BEETROOT))
                    .save(consumer, ForageCraft.find("main/foraged_root_vegetable"));

            Advancement poisonous_potato = builder(Items.POISONOUS_POTATO, "foraged_poisonous_potato", FrameType.TASK, true, false, false)
                    .parent(root_vegetable)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("foraged_poisonous_potato_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.POISONOUS_POTATO))
                    .addCriterion("foraged_poisonous_potato_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.POISONOUS_POTATO))
                    .save(consumer, ForageCraft.find("main/foraged_poisonous_potato"));

            Advancement flint = builder(Items.FLINT, "foraged_flint", FrameType.TASK, true, false, false)
                    .parent(root)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("foraged_flint_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.FLINT))
                    .addCriterion("foraged_flint_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.FLINT))
                    .save(consumer, ForageCraft.find("main/foraged_flint"));

            Advancement bone = builder(Items.BONE, "foraged_bone", FrameType.TASK, true, true, false)
                    .parent(flint)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("foraged_bone_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BONE))
                    .addCriterion("foraged_bone_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.BONE))
                    .save(consumer, ForageCraft.find("main/foraged_bone"));

            Advancement skeleton_skull = builder(Items.SKELETON_SKULL, "foraged_skeleton_skull", FrameType.TASK, true, true, false)
                    .parent(bone)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("foraged_skeleton_skull_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.SKELETON_SKULL))
                    .addCriterion("foraged_skeleton_skull_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.SKELETON_SKULL))
                    .save(consumer, ForageCraft.find("main/foraged_skeleton_skull"));

            Advancement gold_nugget = builder(Items.GOLD_NUGGET, "foraged_gold_nugget", FrameType.TASK, true, true, false)
                    .parent(bone)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("foraged_gold_nugget_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.GOLD_NUGGET))
                    .addCriterion("foraged_gold_nugget_from_nether_quartz_ore", ForagingTrigger.Instance.create(Blocks.NETHER_QUARTZ_ORE, Items.GOLD_NUGGET))
                    .save(consumer, ForageCraft.find("main/foraged_gold_nugget"));

            Advancement diamond = builder(Items.DIAMOND, "foraged_diamond", FrameType.CHALLENGE, true, true, false)
                    .parent(gold_nugget)
                    .addCriterion("foraged_diamond_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.DIAMOND))
                    .save(consumer, ForageCraft.find("main/foraged_diamond"));

            Advancement emerald = builder(Items.EMERALD, "foraged_emerald", FrameType.CHALLENGE, true, true, false)
                    .parent(gold_nugget)
                    .addCriterion("foraged_emerald_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.EMERALD))
                    .save(consumer, ForageCraft.find("main/foraged_emerald"));

            Advancement gathering_knife = haveAnyItem(builder(ForageItems.gathering_knife, "has_gathering_knife", FrameType.TASK, true, false, false)
                            .parent(root),
                    ImmutableList.of(ForageItems.gathering_knife))
                    .save(consumer, ForageCraft.find("main/has_gathering_knife"));

            Advancement straw = haveAnyItem(builder(ForageItems.straw, "has_straw", FrameType.TASK, true, false, false)
                            .parent(gathering_knife),
                    ImmutableList.of(ForageItems.straw))
                    .save(consumer, ForageCraft.find("main/has_straw"));

            Advancement seeds_stolen = haveAnyItem(builder(ForageItems.leek_seeds, "has_leek_seeds", FrameType.TASK, true, true, false)
                            .parent(root),
                    ImmutableList.of(ForageItems.leek_seeds))
                    .save(consumer, ForageCraft.find("main/has_leek_seeds"));

            Advancement leek = haveAnyItem(builder(ForageItems.leek, "has_leek", FrameType.TASK, true, true, false)
                            .parent(seeds_stolen),
                    ImmutableList.of(ForageItems.leek))
                    .save(consumer, ForageCraft.find("main/has_leek"));

            Advancement baka_baka_baka = builder(ForageItems.leek, "triple_baka", FrameType.GOAL, true, true, true)
                    .parent(leek)
                    .addCriterion("hit_entity_with_leek", LeekTrigger.Instance.create())
                    .save(consumer, ForageCraft.find("main/triple_baka"));

            Advancement spaghetti = haveAnyItem(builder(ForageItems.spaghetti, "has_spaghetti", FrameType.GOAL, true, true, true)
                            .parent(root),
                    ImmutableList.of(ForageItems.spaghetti))
                    .save(consumer, ForageCraft.find("main/has_spaghetti"));

            // Patchouli book exclusives
            Advancement book_underground = Advancement.Builder.advancement()
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("forage_bone_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BONE))
                    .addCriterion("forage_bone_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.BONE))
                    .addCriterion("forage_skeleton_skull_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Blocks.SKELETON_SKULL))
                    .addCriterion("forage_skeleton_skull_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Blocks.SKELETON_SKULL))
                    .addCriterion("forage_flint_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.FLINT))
                    .addCriterion("forage_flint_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.FLINT))
                    .addCriterion("forage_gold_nugget_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.GOLD_NUGGET))
                    .addCriterion("forage_diamond_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.DIAMOND))
                    .addCriterion("forage_emerald_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.EMERALD))
                    .save(consumer, ForageCraft.find("book_exclusive/underground"));

            Advancement book_root_vegetables = Advancement.Builder.advancement()
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("foraged_potato_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.POTATO))
                    .addCriterion("foraged_potato_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.POTATO))
                    .addCriterion("foraged_carrot_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.CARROT))
                    .addCriterion("foraged_beetroot_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BEETROOT))
                    .addCriterion("foraged_poisonous_potato_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.POISONOUS_POTATO))
                    .addCriterion("foraged_poisonous_potato_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.POISONOUS_POTATO))
                    .save(consumer, ForageCraft.find("book_exclusive/root_vegetables"));

            Advancement book_paving_stones = Advancement.Builder.advancement()
                    .addCriterion("has_paving_stones", InventoryChangeTrigger.Instance.hasItems(ForageBlocks.paving_stones))
                    .save(consumer, ForageCraft.find("book_exclusive/has_paving_stones"));
        }

        private Advancement.Builder builder(IItemProvider displayItem, String name, ResourceLocation background, FrameType frameType, boolean showToast, boolean announceToChat, boolean hidden)
        {
            return Advancement.Builder.advancement().display(displayItem, translate(name), translate(name + ".desc"), background, frameType, showToast, announceToChat, hidden);
        }

        private Advancement.Builder builder(IItemProvider displayItem, String name, FrameType frameType, boolean showToast, boolean announceToChat, boolean hidden)
        {
            return builder(displayItem, name, null, frameType, showToast, announceToChat, hidden);
        }

        private Advancement.Builder haveAnyItem(Advancement.Builder builder, List<IItemProvider> items)
        {
            items.forEach(item -> builder.addCriterion("has_" + item.asItem().getRegistryName().getPath(), InventoryChangeTrigger.Instance.hasItems(item)));
            return builder;
        }

        private TranslationTextComponent translate(String key)
        {
            return new TranslationTextComponent("advancements." + ForageInfo.MOD_ID + (section.equals("") ? "" : "." + section) + "." + key);
        }
    }
}

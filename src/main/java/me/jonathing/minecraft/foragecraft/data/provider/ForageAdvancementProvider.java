package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import me.jonathing.minecraft.foragecraft.common.trigger.ForagingTrigger;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    private final List<Consumer<Consumer<Advancement>>> advancements = ImmutableList.of(new ForageAdvancements());

    public ForageAdvancementProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void act(@Nonnull DirectoryCache cache) throws IOException
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
                    IDataProvider.save(GSON, cache, a.copy().serialize(), path1);
                }
                catch (IOException ioexception)
                {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
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
            Advancement root = builder(ForageItems.stick_bundle, "root", ForageCraft.locate("textures/block/fascine_side.png"), FrameType.TASK, true, false, false)
                    .withCriterion("has_" + Blocks.DIRT.asItem().getRegistryName().getPath(), InventoryChangeTrigger.Instance.forItems(Blocks.DIRT.asItem()))
                    .register(consumer, ForageCraft.find("root"));

            Advancement root_vegetable = builder(Items.POTATO, "foraged_root_vegetable", FrameType.TASK, true, false, false)
                    .withParent(root)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("foraged_potato_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.POTATO))
                    .withCriterion("foraged_potato_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.POTATO))
                    .withCriterion("foraged_carrot_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.CARROT))
                    .withCriterion("foraged_beetroot_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BEETROOT))
                    .register(consumer, ForageCraft.find("foraged_root_vegetable"));

            Advancement poisonous_potato = builder(Items.POISONOUS_POTATO, "foraged_poisonous_potato", FrameType.TASK, true, false, false)
                    .withParent(root_vegetable)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("foraged_poisonous_potato_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.POISONOUS_POTATO))
                    .withCriterion("foraged_poisonous_potato_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.POISONOUS_POTATO))
                    .register(consumer, ForageCraft.find("foraged_poisonous_potato"));

            Advancement flint = builder(Items.FLINT, "foraged_flint", FrameType.TASK, true, false, false)
                    .withParent(root)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("foraged_flint_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.FLINT))
                    .withCriterion("foraged_flint_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.FLINT))
                    .register(consumer, ForageCraft.find("foraged_flint"));

            Advancement bone = builder(Items.BONE, "foraged_bone", FrameType.TASK, true, false, false)
                    .withParent(flint)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("foraged_bone_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BONE))
                    .withCriterion("foraged_bone_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.BONE))
                    .register(consumer, ForageCraft.find("foraged_bone"));

            Advancement skeleton_skull = builder(Items.SKELETON_SKULL, "foraged_skeleton_skull", FrameType.TASK, true, false, false)
                    .withParent(bone)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("foraged_skeleton_skull_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.SKELETON_SKULL))
                    .withCriterion("foraged_skeleton_skull_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.SKELETON_SKULL))
                    .register(consumer, ForageCraft.find("foraged_skeleton_skull"));

            Advancement gold_nugget = builder(Items.GOLD_NUGGET, "foraged_gold_nugget", FrameType.TASK, true, false, false)
                    .withParent(bone)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("foraged_gold_nugget_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.GOLD_NUGGET))
                    .withCriterion("foraged_gold_nugget_from_nether_quartz_ore", ForagingTrigger.Instance.create(Blocks.NETHER_QUARTZ_ORE, Items.GOLD_NUGGET))
                    .register(consumer, ForageCraft.find("foraged_gold_nugget"));

            Advancement diamond = builder(Items.DIAMOND, "foraged_diamond", FrameType.CHALLENGE, true, false, false)
                    .withParent(gold_nugget)
                    .withCriterion("foraged_diamond_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.DIAMOND))
                    .register(consumer, ForageCraft.find("foraged_diamond"));

            Advancement emerald = builder(Items.EMERALD, "foraged_emerald", FrameType.CHALLENGE, true, false, false)
                    .withParent(gold_nugget)
                    .withCriterion("foraged_emerald_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.EMERALD))
                    .register(consumer, ForageCraft.find("foraged_emerald"));

            Advancement cutting_knife = haveAnyItem(builder(ForageItems.cutting_knife, "has_cutting_knife", FrameType.TASK, true, false, false)
                            .withParent(root),
                    ImmutableList.of(ForageItems.cutting_knife))
                    .register(consumer, ForageCraft.find("has_cutting_knife"));

            Advancement straw = haveAnyItem(builder(ForageItems.straw, "has_straw", FrameType.TASK, true, false, false)
                            .withParent(cutting_knife),
                    ImmutableList.of(ForageItems.straw))
                    .register(consumer, ForageCraft.find("has_straw"));

            Advancement seeds_stolen = haveAnyItem(builder(ForageItems.leek_seeds, "has_leek_seeds", FrameType.TASK, true, false, false)
                            .withParent(root),
                    ImmutableList.of(ForageItems.leek_seeds))
                    .register(consumer, ForageCraft.find("has_leek_seeds"));

            Advancement leek = haveAnyItem(builder(ForageItems.leek, "has_leek", FrameType.TASK, true, false, false)
                            .withParent(seeds_stolen),
                    ImmutableList.of(ForageItems.leek))
                    .register(consumer, ForageCraft.find("has_leek"));

            // Patchouli book exclusives
            Advancement book_underground = Advancement.Builder.builder()
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("forage_bone_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BONE))
                    .withCriterion("forage_bone_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.BONE))
                    .withCriterion("forage_skeleton_skull_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.SKELETON_SKULL))
                    .withCriterion("forage_skeleton_skull_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.SKELETON_SKULL))
                    .withCriterion("forage_flint_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.FLINT))
                    .withCriterion("forage_flint_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.FLINT))
                    .withCriterion("forage_gold_nugget_from_stone", ForagingTrigger.Instance.create(Blocks.STONE, Items.GOLD_NUGGET))
                    .withCriterion("forage_diamond_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.DIAMOND))
                    .withCriterion("forage_emerald_from_coal_ore", ForagingTrigger.Instance.create(Blocks.COAL_ORE, Items.EMERALD))
                    .register(consumer, ForageCraft.find("book_exclusive/underground"));
        }

        private Advancement.Builder builder(IItemProvider displayItem, String name, ResourceLocation background, FrameType frameType, boolean showToast, boolean announceToChat, boolean hidden)
        {
            return Advancement.Builder.builder().withDisplay(displayItem, translate(name), translate(name + ".desc"), background, frameType, showToast, announceToChat, hidden);
        }

        private Advancement.Builder builder(IItemProvider displayItem, String name, FrameType frameType, boolean showToast, boolean announceToChat, boolean hidden)
        {
            return builder(displayItem, name, null, frameType, showToast, announceToChat, hidden);
        }

        private Advancement.Builder haveAnyItem(Advancement.Builder builder, List<IItemProvider> items)
        {
            items.forEach(item -> builder.withCriterion("has_" + item.asItem().getRegistryName().getPath(), InventoryChangeTrigger.Instance.forItems(item)));
            return builder;
        }

        private TranslationTextComponent translate(String key)
        {
            return new TranslationTextComponent("advancements." + ForageInfo.MOD_ID + (section.equals("") ? "" : "." + section) + "." + key);
        }
    }
}

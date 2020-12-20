package me.jonathing.minecraft.foragecraft.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.common.trigger.ForagingTrigger;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
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
                    .register(consumer, ForageCraft.find("begin_research"));

            Advancement root_vegetable = builder(Items.POTATO, "root_vegetable", FrameType.TASK, true, false, false)
                    .withParent(root)
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("forage_potato_from_dirt", ForagingTrigger.Instance.create(Blocks.DIRT, Items.POTATO))
                    .withCriterion("forage_potato_from_grass_block", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.POTATO))
                    .withCriterion("forage_carrot", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.CARROT))
                    .withCriterion("forage_beetroot", ForagingTrigger.Instance.create(Blocks.GRASS_BLOCK, Items.BEETROOT))
                    .register(consumer, ForageCraft.find("root_vegetable"));

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
            return builder(displayItem, name, (ResourceLocation) null, frameType, showToast, announceToChat, hidden);
        }

        private TranslationTextComponent translate(String key)
        {
            return new TranslationTextComponent("advancements." + ForageInfo.MOD_ID + (section.equals("") ? "" : "." + section) + "." + key);
        }
    }
}

package me.jonathing.minecraft.foragecraft.data.provider;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.data.tag.ForageItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks.straw_bale;
import static me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks.fascine;

public class ForageTagProvider
{
    public static class BlockTagProvider extends BlockTagsProvider
    {
        public BlockTagProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
        {
            super(generator, ForageCraft.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerTags()
        {
            // Skipping for now...
        }

        @Override
        public String getName()
        {
            return "ForageCraft Block Tags";
        }
    }

    public static class ItemTagProvider extends ItemTagsProvider
    {
        public ItemTagProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper)
        {
            super(generator, blockTagsProvider, ForageCraft.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerTags()
        {
            forageCraft();
            vanilla();
        }

        void forageCraft()
        {
            this.getOrCreateTagBuilder(ForageItemTags.SCARECROW_CRAFTABLE).add(straw_bale.asItem(), fascine.asItem());
        }

        void vanilla()
        {
            this.getOrCreateTagBuilder(ForageItemTags.SCARECROW_CRAFTABLE).add(Blocks.HAY_BLOCK.asItem());
        }

        @Override
        public String getName()
        {
            return "ForageCraft Item Tags";
        }
    }
}

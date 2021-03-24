package me.jonathing.minecraft.foragecraft.data.provider;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.data.objects.ForageItemTags;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

import static me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks.fascine;
import static me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks.straw_bale;

/**
 * The provider for all of the tags in ForageCraft.
 *
 * @author Jonathing
 * @author Silver_David
 * @since Not yet implemented.
 */
public class ForageTagProvider
{
    /**
     * @see BlockTagsProvider
     */
    public static class BlockTagProvider extends BlockTagsProvider
    {
        public BlockTagProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
        {
            super(generator, ForageInfo.MOD_ID, existingFileHelper);
        }

        /**
         * @see BlockTagsProvider#registerTags()
         */
        @Override
        protected void addTags()
        {
            vanilla();
        }

        private void vanilla()
        {
            this.tag(BlockTags.CROPS).add(ForageBlocks.leek_crop);
        }

        @Override
        @Nonnull
        public String getName()
        {
            return "ForageCraft Block Tags";
        }
    }

    public static class ItemTagProvider extends ItemTagsProvider
    {
        public ItemTagProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper)
        {
            super(generator, blockTagsProvider, ForageInfo.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags()
        {
            forageCraft();
            vanilla();
        }

        void forageCraft()
        {
            this.tag(ForageItemTags.SCARECROW_CRAFTABLE).add(straw_bale.asItem(), fascine.asItem());
        }

        void vanilla()
        {
            this.tag(ForageItemTags.SCARECROW_CRAFTABLE).add(Blocks.HAY_BLOCK.asItem());
        }

        @Override
        @Nonnull
        public String getName()
        {
            return "ForageCraft Item Tags";
        }
    }
}

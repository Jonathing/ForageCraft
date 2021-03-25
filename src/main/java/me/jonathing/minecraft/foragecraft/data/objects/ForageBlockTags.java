package me.jonathing.minecraft.foragecraft.data.objects;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

/**
 * The holder of all the item tags in ForageCraft.
 *
 * @author Jonathing
 * @author Silver_David
 * @since 2.2.0
 */
public class ForageBlockTags
{
    public static final IOptionalNamedTag<Block> ROCK_PLACEABLE = tag("rock_placeable");
    public static final IOptionalNamedTag<Block> STICK_PLACEABLE = tag("stick_placeable");
    public static final IOptionalNamedTag<Block> BLACKSTONE_ROCK_PLACEABLE = tag("blackstone_rock_placeable");

    public static void init()
    {
    }

    private static IOptionalNamedTag<Block> tag(String key)
    {
        return BlockTags.createOptional(ForageCraft.locate(key));
    }
}

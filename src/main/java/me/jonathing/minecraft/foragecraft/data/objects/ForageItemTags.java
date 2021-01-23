package me.jonathing.minecraft.foragecraft.data.objects;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

/**
 * The holder of all the item tags in ForageCraft.
 *
 * @author Jonathing
 * @author Silver_David
 * @since Not yet implemented.
 */
public class ForageItemTags
{
    public static final IOptionalNamedTag<Item> SCARECROW_CRAFTABLE = tag("scarecrow_craftable");

    public static void init()
    {
    }

    private static IOptionalNamedTag<Item> tag(String key)
    {
        return ItemTags.createOptional(ForageCraft.locate(key));
    }
}

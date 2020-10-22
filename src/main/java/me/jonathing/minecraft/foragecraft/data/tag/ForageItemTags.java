package me.jonathing.minecraft.foragecraft.data.tag;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class ForageItemTags
{
    public static void init()
    {
    }

    public static final IOptionalNamedTag<Item> SCARECROW_CRAFTABLE = tag("scarecrow_craftable");

    private static IOptionalNamedTag<Item> tag(String key)
    {
        return ItemTags.createOptional(ForageCraft.locate(key));
    }
}

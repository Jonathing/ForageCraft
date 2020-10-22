package me.jonathing.minecraft.foragecraft.common.items;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ForageItemGroups extends ItemGroup
{
    private final String iconName;

    public ForageItemGroups(String label, String iconName)
    {
        super(label);
        this.iconName = iconName;
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(ForageCraft.locate(iconName)));
    }

    public static final ForageItemGroups FORAGECRAFT = new ForageItemGroups("foragecraft", "straw_bale");
}

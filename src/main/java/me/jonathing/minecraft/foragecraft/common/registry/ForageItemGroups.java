package me.jonathing.minecraft.foragecraft.common.registry;

import me.jonathing.minecraft.foragecraft.ForageCraft;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

/**
 * This class holds all of the item groups in ForageCraft.
 * <p>
 * Currently, there is only <strong>one</strong> item group in ForageCraft, and it is <em>only</em> used in a
 * development environment.
 *
 * @author Jonathing
 * @see ItemGroup
 * @since 2.0.0
 */
public class ForageItemGroups extends ItemGroup
{
    /**
     * This is the development environment item group for ForageCraft. This will <em>never</em> be seen in game unless
     * the specific system property has been defined.
     */
    public static final ForageItemGroups FORAGECRAFT = new ForageItemGroups(ForageInfo.MOD_ID, "stick_bundle");
    private final String iconName;

    public ForageItemGroups(String label, String iconName)
    {
        super(label);
        this.iconName = iconName;
    }

    /**
     * This method creates an icon for a specific item group. In this case,  only the dev-environment group is needed
     * here.
     *
     * @see ItemGroup#createIcon()
     * @see #FORAGECRAFT
     */
    @Override
    @Nonnull
    public ItemStack makeIcon()
    {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(ForageCraft.locate(iconName)));
    }
}

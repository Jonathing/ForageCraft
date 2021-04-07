package me.jonathing.minecraft.foragecraft.common.item.util;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * This enum contains all of ForageCraft's specific item tiers.
 *
 * @author Jonathing
 * @see me.jonathing.minecraft.foragecraft.common.registry.ForageItems
 * @see IItemTier
 * @since 2.3.0
 */
public enum ForageItemTier implements IItemTier
{
    /**
     * The primitive item tier is designed to be a sort of middle ground between wood and stone, but not good enough to
     * incentivise using it all the time. Primitive tools are meant for emergencies or when the player is on low
     * resources.
     */
    PRIMITIVE(0, 59, 2.25F, 0.0F, 0, () -> Ingredient.of(Items.FLINT));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    /**
     * Everything that an item tier needs will be served in this constructor.
     *
     * @param harvestLevelIn The harvest level for the item tier.
     * @param maxUsesIn The maximum uses for each tool for the item tier.
     * @param efficiencyIn The effeciency of each tool for the item tier.
     * @param attackDamageIn The base attack damage of each tool for the item tier.
     * @param enchantabilityIn The enchantability of the item tier.
     * @param repairMaterialIn The repair material, served in a supplier to be turned into a {@link Lazy}.
     */
    ForageItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn)
    {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = Lazy.of(repairMaterialIn);
    }

    @Override
    public int getUses()
    {
        return this.maxUses;
    }

    @Override
    public float getSpeed()
    {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus()
    {
        return this.attackDamage;
    }

    @Override
    public int getLevel()
    {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue()
    {
        return this.enchantability;
    }

    @Override
    @Nonnull
    public Ingredient getRepairIngredient()
    {
        return this.repairMaterial.get();
    }
}

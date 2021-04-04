package me.jonathing.minecraft.foragecraft.common.item.util;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum ForageItemTier implements IItemTier
{
    PRIMITIVE(0, 89, 2.25F, 0.0F, 0, () -> Ingredient.of(Items.FLINT));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

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

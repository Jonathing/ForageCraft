package me.jonathing.minecraft.foragecraft.mixin;

import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ForageMixinPlugin implements IMixinConfigPlugin
{
    private static final String[] MAIN_MIXINS = new String[]
            {
                    "EntityMixin",
                    "ItemMixin",
                    "RecipeManagerMixin"
            };

    private static final String[] DATA_MIXINS = new String[]
            {
                    "data.ShapelessRecipeBuilderMixin",
                    "data.ShapelessRecipeBuilderMixin$ResultMixin"
            };

    @Override
    public void onLoad(String mixinPackage)
    {
    }

    @Override
    public String getRefMapperConfig()
    {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets)
    {
    }

    @Override
    public List<String> getMixins()
    {
        List<String> mixins = new ArrayList<>(Arrays.asList(MAIN_MIXINS));

        if (ForageInfo.DATAGEN)
            mixins.addAll(Arrays.asList(DATA_MIXINS));

        return mixins;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
    {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
    {
    }
}

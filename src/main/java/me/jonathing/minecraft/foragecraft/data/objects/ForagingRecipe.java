package me.jonathing.minecraft.foragecraft.data.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.jonathing.minecraft.foragecraft.common.util.JsonUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ForagingRecipe implements IToJson<ForagingRecipe>
{
    public static final String DIRECTORY = "foraging_recipes";

    private static final String BLOCK_KEY = "block";
    private static final String ITEM_KEY = "item";
    private static final String MAX_DROPS_KEY = "max_drops";
    private static final String CHANCE_KEY = "chance";

    private final Block block;
    private final IItemProvider item;
    private final int maxDrops;
    private final float chance;

    public ForagingRecipe(Block block, IItemProvider item, int maxDrops, float chance)
    {
        this.block = block;
        this.item = item;
        this.maxDrops = maxDrops;
        this.chance = chance;
    }

    public Block getInput()
    {
        return this.block;
    }

    public IItemProvider getResult()
    {
        return this.item;
    }

    public int getMaxDrops()
    {
        return this.maxDrops;
    }

    public float getChance()
    {
        return this.chance;
    }

    public static ForagingRecipe fromJson(JsonObject json)
    {
        return new ForagingRecipe(
                JsonUtil.Reader.getBlock(json, BLOCK_KEY).orElseThrow(() -> new JsonSyntaxException("Unable to get block!")),
                JsonUtil.Reader.getItemProvider(json, ITEM_KEY),
                JSONUtils.getAsInt(json, MAX_DROPS_KEY),
                JSONUtils.getAsFloat(json, CHANCE_KEY)
        );
    }

    @Override
    public JsonObject toJson()
    {
        JsonObject json = new JsonObject();
        json.addProperty(BLOCK_KEY, this.block.getRegistryName().toString());
        json.addProperty(ITEM_KEY, this.item.asItem().getRegistryName().toString());
        json.addProperty(MAX_DROPS_KEY, this.maxDrops);
        json.addProperty(CHANCE_KEY, this.chance);
        return json;
    }

    public static ForagingRecipe fromNBT(CompoundNBT nbt)
    {
        return new ForagingRecipe(
                ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString(BLOCK_KEY))),
                getItemProvider(new ResourceLocation(nbt.getString(ITEM_KEY))),
                nbt.getInt(MAX_DROPS_KEY),
                nbt.getFloat(CHANCE_KEY)
        );
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(BLOCK_KEY, this.block.getRegistryName().toString());
        nbt.putString(ITEM_KEY, this.item.asItem().getRegistryName().toString());
        nbt.putInt(MAX_DROPS_KEY, this.maxDrops);
        nbt.putFloat(CHANCE_KEY, this.chance);
        return nbt;
    }

    private static IItemProvider getItemProvider(ResourceLocation resLoc)
    {
        Block block = ForgeRegistries.BLOCKS.getValue(resLoc);
        return block != null ? block : ForgeRegistries.ITEMS.getValue(resLoc);
    }
}

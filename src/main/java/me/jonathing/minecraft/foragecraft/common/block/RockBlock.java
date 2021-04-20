package me.jonathing.minecraft.foragecraft.common.block;

import me.jonathing.minecraft.foragecraft.common.block.template.DecorativeBlock;
import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

/**
 * This class holds the {@link ForageBlocks#rock}. This class is the king of all rocks, as it is also integral to the
 * how {@link ForageBlocks#flat_rock} and {@link ForageBlocks#stick} function.
 *
 * @author Jonathing
 * @see ForageBlocks#rock
 * @see DecorativeBlock
 * @see IWaterLoggable
 * @since 2.1.0
 */
public class RockBlock extends DecorativeBlock
{
    public static final Supplier<VoxelShape> ROCK_SHAPE = () ->
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);
    public static final Supplier<VoxelShape> FLAT_ROCK_SHAPE = () ->
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);

    private static final Supplier<Properties> PROPERTIES =
            () -> AbstractBlock.Properties
                    .of(Material.STONE)
                    .sound(SoundType.STONE)
                    .noCollission()
                    .noOcclusion()
                    .instabreak();

    /**
     * Makes a new {@link DecorativeBlock} with features exclusive to the {@link ForageBlocks#rock} with a gicen
     * {@link VoxelShape} and decorative {@link Item} supplied by a {@link Lazy}.
     *
     * @param shape          The shape to use for this rock block.
     * @param decorativeItem The item to use as the decorative item, provided in a Lazy.
     * @see RockBlock#RockBlock(Properties, VoxelShape, Lazy)
     */
    public RockBlock(VoxelShape shape, Lazy<Item> decorativeItem)
    {
        this(PROPERTIES.get(), shape, decorativeItem);
    }

    public RockBlock(Properties properties, VoxelShape shape, Lazy<Item> decorativeItem)
    {
        super(properties, shape, decorativeItem);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }
}

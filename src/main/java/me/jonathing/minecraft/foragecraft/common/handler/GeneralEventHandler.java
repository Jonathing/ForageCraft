package me.jonathing.minecraft.foragecraft.common.handler;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

/**
 * This class is a general event handler that handles some events that do not necessarily need a class of their own.
 *
 * @author Jonathing
 * @since 2.0.0
 */
@Mod.EventBusSubscriber
public class GeneralEventHandler
{
    private static final Supplier<LootPool> VILLAGE_HOUSE_CHESTS =
            () -> LootPool.builder().addEntry(
                    ItemLootEntry.builder(ForageItems.leek_seeds)
                            .acceptFunction(SetCount.builder(RandomValueRange.of(2, 4))))
                    .build();

    /**
     * This event method sets the fuel burn time for specific items or blocks in ForageCraft. Since they cannot be
     * defined in item properties, they are instead defined here.
     *
     * @see FurnaceFuelBurnTimeEvent
     * @since 2.0.0
     */
    @SubscribeEvent
    public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event)
    {
        if (event.getItemStack().getItem().equals(ForageBlocks.fascine.asItem()))
        {
            event.setBurnTime(20 * 5 * 9 * 9);
        }
        else if (event.getItemStack().getItem().equals(ForageItems.stick_bundle))
        {
            event.setBurnTime(20 * 5 * 9);
        }
    }

    @SubscribeEvent
    public static void lootTableLoadEvent(LootTableLoadEvent event)
    {
        if (event.getName().equals(LootTables.CHESTS_VILLAGE_VILLAGE_DESERT_HOUSE)
                || event.getName().equals(LootTables.CHESTS_VILLAGE_VILLAGE_PLAINS_HOUSE)
                || event.getName().equals(LootTables.CHESTS_VILLAGE_VILLAGE_SAVANNA_HOUSE)
                || event.getName().equals(LootTables.CHESTS_VILLAGE_VILLAGE_SNOWY_HOUSE)
                || event.getName().equals(LootTables.CHESTS_VILLAGE_VILLAGE_TAIGA_HOUSE))
        {
            event.getTable().addPool(VILLAGE_HOUSE_CHESTS.get());
        }
    }
}

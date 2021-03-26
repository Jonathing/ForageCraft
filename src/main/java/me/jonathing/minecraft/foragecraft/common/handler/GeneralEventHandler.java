package me.jonathing.minecraft.foragecraft.common.handler;

import me.jonathing.minecraft.foragecraft.common.registry.ForageBlocks;
import me.jonathing.minecraft.foragecraft.common.registry.ForageItems;
import me.jonathing.minecraft.foragecraft.info.ForageInfo;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.common.util.NonNullLazy;
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
@Mod.EventBusSubscriber(modid = ForageInfo.MOD_ID)
public class GeneralEventHandler
{
    /**
     * This is a {@link LootPool} that is added to all of the village house chests on the {@link LootTableLoadEvent}.
     * The loot pool uses a {@link Supplier} to prevent {@link ExceptionInInitializerError}.
     *
     * @see #onLootTableLoad(LootTableLoadEvent)
     */
    private static final NonNullLazy<LootPool> VILLAGE_HOUSE_CHESTS = NonNullLazy.of(() ->
            LootPool.lootPool().name("village_leek_seeds").add(
                    ItemLootEntry.lootTableItem(ForageItems.leek_seeds)
                            .apply(SetCount.setCount(RandomValueRange.between(0, 2))))
                    .build());

    /**
     * This event method sets the fuel burn time for specific items or blocks in ForageCraft. Since they cannot be
     * defined in item properties, they are instead defined here.
     *
     * @param event The furnace fuel burn time event to use to add the burn times to.
     * @see FurnaceFuelBurnTimeEvent
     */
    @SubscribeEvent
    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event)
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

    /**
     * This event method is used to add some of ForageCraft's own loot pools into existing loot tables.
     *
     * @param event The loot table load event to use to add the {@link #VILLAGE_HOUSE_CHESTS} to.
     * @see LootTableLoadEvent
     * @see #VILLAGE_HOUSE_CHESTS
     * @since 2.1.0
     */
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
        if (event.getName().equals(LootTables.VILLAGE_DESERT_HOUSE)
                || event.getName().equals(LootTables.VILLAGE_PLAINS_HOUSE)
                || event.getName().equals(LootTables.VILLAGE_SAVANNA_HOUSE)
                || event.getName().equals(LootTables.VILLAGE_SNOWY_HOUSE)
                || event.getName().equals(LootTables.VILLAGE_TAIGA_HOUSE))
        {
            event.getTable().addPool(VILLAGE_HOUSE_CHESTS.get());
        }
    }
}

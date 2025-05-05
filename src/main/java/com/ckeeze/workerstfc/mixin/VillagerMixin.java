package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
@Mixin(Villager.class)
public class VillagerMixin {

    private static final Map<Item, Integer> FOOD_POINTS = ImmutableMap.ofEntries(
            Map.entry(Items.BREAD, 4),
            Map.entry(Items.POTATO, 1),
            Map.entry(Items.CARROT, 1),
            Map.entry(Items.BEETROOT, 1),
            Map.entry(TFCItems.FOOD.get(Food.BEET).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.SOYBEAN).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.CARROT).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.CABBAGE).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.GREEN_BEAN).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.GARLIC).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.GREEN_BELL_PEPPER).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.SQUASH).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.POTATO).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.ONION).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.TOMATO).get(), 1),
            Map.entry(TFCItems.FOOD.get(Food.OAT_BREAD).get(), 4),
            Map.entry(TFCItems.FOOD.get(Food.RICE_BREAD).get(), 4),
            Map.entry(TFCItems.FOOD.get(Food.RYE_BREAD).get(), 4),
            Map.entry(TFCItems.FOOD.get(Food.BARLEY_BREAD).get(), 4),
            Map.entry(TFCItems.FOOD.get(Food.MAIZE_BREAD).get(), 4),
            Map.entry(TFCItems.FOOD.get(Food.WHEAT_BREAD).get(), 4)
            );

    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD,
            TFCItems.FOOD.get(Food.BEET).get(),TFCItems.FOOD.get(Food.SOYBEAN).get(),TFCItems.FOOD.get(Food.CARROT).get(),
            TFCItems.FOOD.get(Food.CABBAGE).get(),TFCItems.FOOD.get(Food.GREEN_BEAN).get(),TFCItems.FOOD.get(Food.GARLIC).get(),
            TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(), TFCItems.FOOD.get(Food.GREEN_BELL_PEPPER).get(),
            TFCItems.FOOD.get(Food.SQUASH).get(),TFCItems.FOOD.get(Food.POTATO).get(),TFCItems.FOOD.get(Food.ONION).get(),TFCItems.FOOD.get(Food.TOMATO).get(),
            TFCItems.FOOD.get(Food.OAT_BREAD).get(), TFCItems.FOOD.get(Food.RICE_BREAD).get(),TFCItems.FOOD.get(Food.RYE_BREAD).get(),
            TFCItems.FOOD.get(Food.BARLEY_BREAD).get(), TFCItems.FOOD.get(Food.MAIZE_BREAD).get(), TFCItems.FOOD.get(Food.WHEAT_BREAD).get());

}

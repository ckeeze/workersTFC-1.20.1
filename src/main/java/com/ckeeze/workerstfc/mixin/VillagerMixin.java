package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    @Shadow
    private int foodLevel;

    @Final
    private static final Map<Item, Integer> FOOD_POINTS2 = ImmutableMap.ofEntries(
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

    private static final Set<Item> WANTED_ITEMS2 = ImmutableSet.of(
            TFCItems.FOOD.get(Food.BEET).get(),TFCItems.FOOD.get(Food.SOYBEAN).get(),TFCItems.FOOD.get(Food.CARROT).get(),
            TFCItems.FOOD.get(Food.CABBAGE).get(),TFCItems.FOOD.get(Food.GREEN_BEAN).get(),TFCItems.FOOD.get(Food.GARLIC).get(),
            TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(), TFCItems.FOOD.get(Food.GREEN_BELL_PEPPER).get(),
            TFCItems.FOOD.get(Food.SQUASH).get(),TFCItems.FOOD.get(Food.POTATO).get(),TFCItems.FOOD.get(Food.ONION).get(),TFCItems.FOOD.get(Food.TOMATO).get(),
            TFCItems.FOOD.get(Food.OAT_BREAD).get(), TFCItems.FOOD.get(Food.RICE_BREAD).get(),TFCItems.FOOD.get(Food.RYE_BREAD).get(),
            TFCItems.FOOD.get(Food.BARLEY_BREAD).get(), TFCItems.FOOD.get(Food.MAIZE_BREAD).get(), TFCItems.FOOD.get(Food.WHEAT_BREAD).get());

    public VillagerMixin(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Overwrite (remap = false)
    public boolean wantsToPickUp(ItemStack pStack) {
        Item item = pStack.getItem();
        return WANTED_ITEMS2.contains(item) && this.getInventory().canAddItem(pStack);
    }

    @Overwrite
    private void eatUntilFull() {
        if (this.hungry() && this.countFoodPointsInInventory() != 0) {
            for(int i = 0; i < this.getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = this.getInventory().getItem(i);
                if (!itemstack.isEmpty()) {
                    Integer integer = FOOD_POINTS2.get(itemstack.getItem());
                    if (integer != null) {
                        int j = itemstack.getCount();

                        for(int k = j; k > 0; --k) {
                            this.foodLevel += integer;
                            this.getInventory().removeItem(i, 1);
                            if (!this.hungry()) {
                                return;
                            }
                        }
                    }
                }
            }

        }
    }

    @Overwrite
    private int countFoodPointsInInventory() {
        SimpleContainer simplecontainer = this.getInventory();
        return FOOD_POINTS2.entrySet().stream().mapToInt((p_186300_) -> {
            return simplecontainer.countItem(p_186300_.getKey()) * p_186300_.getValue();
        }).sum();
    }

    @Shadow
    private boolean hungry() {
        return this.foodLevel < 12;
    }


}

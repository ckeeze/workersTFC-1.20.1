package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ai.EatGoal;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

@SuppressWarnings("unused")
@Mixin(EatGoal.class)
public class EatGoalMixin {

    @Shadow
    public AbstractWorkerEntity worker;
    @Shadow
    public int slotID;

    public EatGoalMixin(AbstractWorkerEntity worker) {
        this.worker = worker;
    }

    @Unique
    private static final Set<Item> RAW_FOOD = ImmutableSet.of(
            TFCItems.FOOD.get(Food.HORSE_MEAT).get(),TFCItems.FOOD.get(Food.BEAR).get(),TFCItems.FOOD.get(Food.FOX).get(),TFCItems.FOOD.get(Food.PORK).get(),TFCItems.FOOD.get(Food.VENISON).get(),
            TFCItems.FOOD.get(Food.BEEF).get(),TFCItems.FOOD.get(Food.CHICKEN).get(),TFCItems.FOOD.get(Food.QUAIL).get(),TFCItems.FOOD.get(Food.MUTTON).get(),TFCItems.FOOD.get(Food.GRAN_FELINE).get(),
            TFCItems.FOOD.get(Food.TURTLE).get(),TFCItems.FOOD.get(Food.CHEVON).get(),TFCItems.FOOD.get(Food.PEAFOWL).get(),TFCItems.FOOD.get(Food.TURKEY).get(),TFCItems.FOOD.get(Food.PHEASANT).get(),
            TFCItems.FOOD.get(Food.GROUSE).get(),TFCItems.FOOD.get(Food.WOLF).get(),TFCItems.FOOD.get(Food.RABBIT).get(),TFCItems.FOOD.get(Food.CAMELIDAE).get(),TFCItems.FOOD.get(Food.HYENA).get(),
            TFCItems.FOOD.get(Food.DUCK).get(),TFCItems.FOOD.get(Food.FROG_LEGS).get(),TFCItems.FOOD.get(Food.COD).get(),TFCItems.FOOD.get(Food.TROPICAL_FISH).get(),TFCItems.FOOD.get(Food.CALAMARI).get(),
            TFCItems.FOOD.get(Food.SHELLFISH).get(),TFCItems.FOOD.get(Food.BLUEGILL).get(),TFCItems.FOOD.get(Food.SMALLMOUTH_BASS).get(),TFCItems.FOOD.get(Food.SALMON).get(),TFCItems.FOOD.get(Food.TARO_ROOT).get(),
            TFCItems.FOOD.get(Food.LARGEMOUTH_BASS).get(),TFCItems.FOOD.get(Food.LAKE_TROUT).get(),TFCItems.FOOD.get(Food.CRAPPIE).get(),

            TFCItems.FOOD.get(Food.WHEAT).get(),TFCItems.FOOD.get(Food.OAT).get(),
            TFCItems.FOOD.get(Food.RICE).get(),TFCItems.FOOD.get(Food.MAIZE).get(),TFCItems.FOOD.get(Food.RYE).get(),TFCItems.FOOD.get(Food.BARLEY).get(),TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(),
            TFCItems.FOOD.get(Food.OAT_GRAIN).get(),TFCItems.FOOD.get(Food.RICE_GRAIN).get(),TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(),TFCItems.FOOD.get(Food.RYE_GRAIN).get(),TFCItems.FOOD.get(Food.BARLEY_GRAIN).get(),

            TFCItems.FOOD.get(Food.SNOWBERRY).get(),TFCItems.FOOD.get(Food.BLUEBERRY).get(),TFCItems.FOOD.get(Food.BLACKBERRY).get(),TFCItems.FOOD.get(Food.RASPBERRY).get(),TFCItems.FOOD.get(Food.GOOSEBERRY).get(),
            TFCItems.FOOD.get(Food.ELDERBERRY).get(),TFCItems.FOOD.get(Food.WINTERGREEN_BERRY).get(),TFCItems.FOOD.get(Food.BANANA).get(),TFCItems.FOOD.get(Food.CHERRY).get(),TFCItems.FOOD.get(Food.GREEN_APPLE).get(),
            TFCItems.FOOD.get(Food.LEMON).get(),TFCItems.FOOD.get(Food.OLIVE).get(),TFCItems.FOOD.get(Food.PLUM).get(),TFCItems.FOOD.get(Food.ORANGE).get(),TFCItems.FOOD.get(Food.PEACH).get(),
            TFCItems.FOOD.get(Food.RED_APPLE).get(),TFCItems.FOOD.get(Food.MELON_SLICE).get(),TFCItems.FOOD.get(Food.BEET).get(),TFCItems.FOOD.get(Food.SOYBEAN).get(),TFCItems.FOOD.get(Food.CARROT).get(),
            TFCItems.FOOD.get(Food.CABBAGE).get(),TFCItems.FOOD.get(Food.GREEN_BEAN).get(),TFCItems.FOOD.get(Food.GARLIC).get(),TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(),
            TFCItems.FOOD.get(Food.GREEN_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.SQUASH).get(),TFCItems.FOOD.get(Food.POTATO).get(),TFCItems.FOOD.get(Food.ONION).get(),TFCItems.FOOD.get(Food.TOMATO).get(),
            TFCItems.FOOD.get(Food.SUGARCANE).get(),TFCItems.FOOD.get(Food.CRANBERRY).get(),TFCItems.FOOD.get(Food.CLOUDBERRY).get(),TFCItems.FOOD.get(Food.BUNCHBERRY).get(),TFCItems.FOOD.get(Food.STRAWBERRY ).get()
    );

    /**
     * @author Ckeeze
     * @reason Preventing workers from eating their own produce
     */
    @Overwrite(remap = false)
    private boolean hasFoodInInv(){
        boolean hasfood = false;
        SimpleContainer inventory = worker.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.isEdible() && !FoodCapability.isRotten(itemStack) && !RAW_FOOD.contains(itemStack.getItem())) hasfood = true;
        }
        return hasfood;
    }

    /**
     * @author Ckeeze
     * @reason Preventing workers from eating their own produce
     */
    @Overwrite(remap = false)
    private ItemStack getAndRemoveFoodInInv(){
        ItemStack itemStack = null;
        for(int i = 0; i < worker.getInventory().getContainerSize(); i++){
            ItemStack stackInSlot = worker.getInventory().getItem(i).copy();
            if(stackInSlot.isEdible() && !FoodCapability.isRotten(stackInSlot) && !RAW_FOOD.contains(stackInSlot.getItem())){
                itemStack = stackInSlot.copy();
                this.slotID = i;
                worker.getInventory().removeItemNoUpdate(i); //removing item in slot
                break;
            }
        }
        return itemStack;
    }
}

package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ai.EatGoal;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Set;

@Mixin(EatGoal.class)
public class EatGoalMixin {

    public AbstractWorkerEntity worker;
    public int slotID;

    public EatGoalMixin(AbstractWorkerEntity worker) {
        this.worker = worker;
    }

    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    private static final Set<Item> RAW_FOOD = ImmutableSet.of(
            IFS("tfc:food/horse_meat"),IFS("tfc:food/bear"),IFS("tfc:food/fox"),IFS("tfc:food/pork"),IFS("tfc:food/venison"),
            IFS("tfc:food/beef"),IFS("tfc:food/chicken"),IFS("tfc:food/quail"),IFS("tfc:food/mutton"),IFS("tfc:food/gran_feline"),
            IFS("tfc:food/turtle"),IFS("tfc:food/chevon"),IFS("tfc:food/peafowl"),IFS("tfc:food/turkey"),IFS("tfc:food/pheasant"),
            IFS("tfc:food/grouse"),IFS("tfc:food/wolf"),IFS("tfc:food/rabbit"),IFS("tfc:food/camelidae"),IFS("tfc:food/hyena"),
            IFS("tfc:food/duck"),IFS("tfc:food/frog_legs"),IFS("tfc:food/cod"),IFS("tfc:food/tropical_fish"),IFS("tfc:food/calamari"),
            IFS("tfc:food/shellfish"),IFS("tfc:food/bluegill"),IFS("tfc:food/smallmouth_bass"),IFS("tfc:food/salmon"),IFS("tfc:food/trout"),
            IFS("tfc:food/largemouth_bass"),IFS("tfc:food/lake_trout"),IFS("tfc:food/crappie"),IFS("tfc:food/wheat"),IFS("tfc:food/oat"),
            IFS("tfc:food/rice"),IFS("tfc:food/maize"),IFS("tfc:food/rye"),IFS("tfc:food/barley"),IFS("tfc:food/wheat_grain"),
            IFS("tfc:food/oat_grain"),IFS("tfc:food/rice_grain"),IFS("tfc:food/maize_grain"),IFS("tfc:food/rye_grain"),IFS("tfc:food/barley_grain"),

            IFS("tfc:food/snowberry"),IFS("tfc:food/blueberry"),IFS("tfc:food/blackberry"),IFS("tfc:food/raspberry"),IFS("tfc:food/gooseberry"),
            IFS("tfc:food/elderberry"),IFS("tfc:food/wintergreen_berry"),IFS("tfc:food/banana"),IFS("tfc:food/cherry"),IFS("tfc:food/green_apple"),
            IFS("tfc:food/lemon"),IFS("tfc:food/olive"),IFS("tfc:food/plum"),IFS("tfc:food/orange"),IFS("tfc:food/peach"),
            IFS("tfc:food/red_apple"),IFS("tfc:food/melon_slice"),IFS("tfc:food/beet"),IFS("tfc:food/soybean"),IFS("tfc:food/carrot"),
            IFS("tfc:food/cabbage"),IFS("tfc:food/green_bean"),IFS("tfc:food/garlic"),IFS("tfc:food/yellow_bell_pepper"),IFS("tfc:food/red_bell_pepper"),
            IFS("tfc:food/green_bell_pepper"),IFS("tfc:food/squash"),IFS("tfc:food/potato"),IFS("tfc:food/onion"),IFS("tfc:food/tomato"),
            IFS("tfc:food/sugarcane"),IFS("tfc:food/cranberry"),IFS("tfc:food/cloudberry"),IFS("tfc:food/bunchberry"),IFS("tfc:food/strawberry")
    );

    private boolean hasFoodInInv(){
        boolean hasfood = false;
        SimpleContainer inventory = worker.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.isEdible() && !FoodCapability.isRotten(itemStack) && !RAW_FOOD.contains(itemStack.getItem())) hasfood = true;
        }
        return hasfood;
    }

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

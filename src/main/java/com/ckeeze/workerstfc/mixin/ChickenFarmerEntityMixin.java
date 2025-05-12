package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ChickenFarmerEntity;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Mixin(ChickenFarmerEntity.class)
public abstract class ChickenFarmerEntityMixin extends AbstractWorkerEntity {


    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(
            Items.FEATHER,
            Items.CHICKEN,
            Items.EGG,
            Items.WHEAT_SEEDS,
            Items.BEETROOT_SEEDS,
            Items.MELON_SEEDS,
            Items.PUMPKIN_SEEDS,
            TFCItems.FOOD.get(Food.CHICKEN).get(),
            TFCItems.FOOD.get(Food.DUCK).get(),
            TFCItems.FOOD.get(Food.QUAIL).get(),
            Items.BONE);

    public ChickenFarmerEntityMixin(EntityType<? extends AbstractWorkerEntity> entityType, Level world) {
        super(entityType, world);
    }

    public boolean wantsToPickUp(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (WANTED_ITEMS.contains(item) || itemStack.is(TFCTags.Items.CHICKEN_FOOD));
    }

    public boolean wantsToKeep(ItemStack itemStack) {
        return super.wantsToKeep(itemStack) || itemStack.is(TFCTags.Items.CHICKEN_FOOD);
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.ROCK_TOOLS.get(RockCategory.IGNEOUS_EXTRUSIVE).get(RockCategory.ItemType.AXE).get());
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.AXE).get(), TFCItems.FOOD.get(Food.WHEAT_GRAIN).get());
    }
}

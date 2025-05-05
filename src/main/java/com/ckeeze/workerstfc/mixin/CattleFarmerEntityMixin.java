package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.CattleFarmerEntity;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.HideItemType;
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
@Mixin(CattleFarmerEntity.class)
public abstract class CattleFarmerEntityMixin extends AbstractWorkerEntity {


    public final ItemStack MAIN_TOOL = new ItemStack(TFCItems.WOODEN_BUCKET.get());

    public CattleFarmerEntityMixin(EntityType<? extends AbstractWorkerEntity> entityType, Level world) {
        super(entityType, world);
    }

    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(
            Items.LEATHER,
            Items.BEEF,
            Items.MILK_BUCKET,
            Items.BUCKET,
            Items.WHEAT,
            Items.BONE,
            TFCItems.FOOD.get(Food.BEEF).get(),
            TFCItems.FOOD.get(Food.CHEVON).get(),
            TFCItems.WOODEN_BUCKET.get(),
            TFCItems.HIDES.get(HideItemType.RAW).get(HideItemType.Size.MEDIUM).get(),
            TFCItems.HIDES.get(HideItemType.RAW).get(HideItemType.Size.LARGE).get());

    public boolean wantsToKeep(ItemStack itemStack) {
        return super.wantsToKeep(itemStack) || itemStack.is(TFCTags.Items.COW_FOOD) || itemStack.is(TFCItems.WOODEN_BUCKET.get());
    }

    public boolean wantsToPickUp(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (WANTED_ITEMS.contains(item) || itemStack.is(TFCTags.Items.COW_FOOD));
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.ROCK_TOOLS.get(RockCategory.IGNEOUS_EXTRUSIVE).get(RockCategory.ItemType.AXE).get());
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
        ItemStack initialTool2 = new ItemStack(TFCItems.WOODEN_BUCKET.get());
        this.updateInventory(1, initialTool2);
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.AXE).get(),
                TFCItems.WOODEN_BUCKET.get(), TFCItems.FOOD.get(Food.WHEAT_GRAIN).get());
    }
}

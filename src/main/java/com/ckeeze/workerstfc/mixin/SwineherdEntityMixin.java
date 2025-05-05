package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.SwineherdEntity;
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

@Mixin(SwineherdEntity.class)
public abstract class SwineherdEntityMixin extends AbstractWorkerEntity {

    public SwineherdEntityMixin(EntityType<? extends AbstractWorkerEntity> entityType, Level world) {
        super(entityType, world);
    }

    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(
            Items.PORKCHOP,
            Items.CARROT,
            Items.BONE,
            TFCItems.FOOD.get(Food.PORK).get(),
            TFCItems.HIDES.get(HideItemType.RAW).get(HideItemType.Size.MEDIUM).get());
    public boolean wantsToPickUp(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (WANTED_ITEMS.contains(item) || itemStack.is(TFCTags.Items.PIG_FOOD));
    }

    public boolean wantsToKeep(ItemStack itemStack) {
        return super.wantsToKeep(itemStack) || itemStack.is(TFCTags.Items.PIG_FOOD) && !itemStack.is(TFCItems.FOOD.get(Food.PORK).get());
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.ROCK_TOOLS.get(RockCategory.IGNEOUS_EXTRUSIVE).get(RockCategory.ItemType.AXE).get());
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.AXE).get(), TFCItems.FOOD.get(Food.CARROT).get(), Items.ROTTEN_FLESH);
    }
}

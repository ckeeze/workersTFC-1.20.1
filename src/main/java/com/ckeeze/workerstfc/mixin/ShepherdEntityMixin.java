package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ShepherdEntity;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;
import java.util.List;

@Mixin(ShepherdEntity.class)
public abstract class ShepherdEntityMixin extends AbstractWorkerEntity {

    public ShepherdEntityMixin(EntityType<? extends AbstractWorkerEntity> entityType, Level world) {
        super(entityType, world);
    }

    public boolean wantsToPickUp(ItemStack itemStack) {
        return (itemStack.is(TFCItems.WOOL.get()) || itemStack.is(TFCItems.FOOD.get(Food.MUTTON).get()) || itemStack.is(TFCItems.FOOD.get(Food.CAMELIDAE).get()));
    }

    public boolean wantsToKeep(ItemStack itemStack) {
        return super.wantsToKeep(itemStack) || itemStack.is(TFCTags.Items.SHEEP_FOOD);
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.METAL_ITEMS.get(Metal.Default.COPPER).get(Metal.ItemType.SHEARS).get());
        this.updateInventory(0, initialTool);
        ItemStack initialTool2 = new ItemStack(TFCItems.ROCK_TOOLS.get(RockCategory.IGNEOUS_EXTRUSIVE).get(RockCategory.ItemType.AXE).get());
        this.updateInventory(1, initialTool2);

        this.equipTool(initialTool);
        this.equipTool(initialTool2);
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.AXE).get(),
                TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.SHEARS).get(), TFCItems.FOOD.get(Food.WHEAT_GRAIN).get());
    }
}

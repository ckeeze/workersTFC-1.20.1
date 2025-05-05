package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.FishermanEntity;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@Mixin(FishermanEntity.class)
public abstract class FishermanEntityMixin extends AbstractInventoryEntity {

    public FishermanEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.METAL_ITEMS.get(Metal.Default.COPPER).get(Metal.ItemType.FISHING_ROD).get());
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

    public int getFarmedItemsDepositAmount(){
        return 24;
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.FISHING_ROD).get());
    }
}

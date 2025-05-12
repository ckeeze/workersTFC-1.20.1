package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.LumberjackEntity;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.blocks.wood.Wood;
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
@Mixin(LumberjackEntity.class)
public abstract class LumberjackEntityMixin extends AbstractInventoryEntity {

    public LumberjackEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.ROCK_TOOLS.get(RockCategory.IGNEOUS_EXTRUSIVE).get(RockCategory.ItemType.AXE).get());
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

    public int getFarmedItemsDepositAmount(){
        return 64;
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.AXE).get(), TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.SAPLING).get().asItem());
    }
}

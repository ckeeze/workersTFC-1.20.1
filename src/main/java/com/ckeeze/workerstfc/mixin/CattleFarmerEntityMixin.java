package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.CattleFarmerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CattleFarmerEntity.class)
public abstract class CattleFarmerEntityMixin extends AbstractInventoryEntity {

    public CattleFarmerEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:stone/axe/igneous_extrusive"));
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
        ItemStack initialTool2 = new ItemStack(IFS("tfc:wooden_bucket"));
        this.updateInventory(1, initialTool2);
    }
}

package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ShepherdEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShepherdEntity.class)
public abstract class ShepherdEntityMixin extends AbstractWorkerEntity {

    public ShepherdEntityMixin(EntityType<? extends AbstractWorkerEntity> entityType, Level world) {
        super(entityType, world);
    }

    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:metal/shears/copper"));
        this.updateInventory(0, initialTool);
        ItemStack initialTool2 = new ItemStack(IFS("tfc:stone/axe/igneous_extrusive"));
        this.updateInventory(1, initialTool2);

        this.equipTool(initialTool);
        this.equipTool(initialTool2);
    }

}

package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.FishermanEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FishermanEntity.class)
public abstract class FishermanEntityMixin extends AbstractInventoryEntity {

    public FishermanEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    //itemfromstring
    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:metal/fishing_rod/copper"));
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

    public int getFarmedItemsDepositAmount(){
        return 24;
    }
}

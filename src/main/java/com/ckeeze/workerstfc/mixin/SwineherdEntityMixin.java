package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.SwineherdEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Set;

@Mixin(SwineherdEntity.class)
public abstract class SwineherdEntityMixin extends AbstractInventoryEntity {

    public SwineherdEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    //ITEM FROM STRING
    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(
            Items.PORKCHOP,
            Items.CARROT);

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:stone/axe/igneous_extrusive"));
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

}

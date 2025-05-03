package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.SwineherdEntity;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Mixin(SwineherdEntity.class)
public abstract class SwineherdEntityMixin extends AbstractWorkerEntity {

    public SwineherdEntityMixin(EntityType<? extends AbstractWorkerEntity> entityType, Level world) {
        super(entityType, world);
    }

    //ITEM FROM STRING
    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(
            Items.PORKCHOP,
            Items.CARROT,
            Items.BONE,
            IFS("tfc:food/pork"),
            IFS("tfc:medium_raw_hide"));

    public boolean wantsToPickUp(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (WANTED_ITEMS.contains(item) || itemStack.is(TFCTags.Items.PIG_FOOD));
    }

    public boolean wantsToKeep(ItemStack itemStack) {
        return super.wantsToKeep(itemStack) || itemStack.is(TFCTags.Items.PIG_FOOD) && !itemStack.is(IFS("tfc:food/pork"));
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:stone/axe/igneous_extrusive"));
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(IFS("tfc:metal/axe/wrought_iron"), IFS("tfc:food/carrot"), Items.ROTTEN_FLESH);
    }
}

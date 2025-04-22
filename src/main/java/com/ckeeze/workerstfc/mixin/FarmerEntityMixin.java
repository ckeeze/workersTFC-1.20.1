package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.FarmerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Set;

@Mixin(FarmerEntity.class)
public abstract class FarmerEntityMixin extends AbstractInventoryEntity {

    public FarmerEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    private static final Set<Item> WANTED_SEEDS = ImmutableSet.of(
            Items.WHEAT_SEEDS,
            Items.MELON_SEEDS,
            Items.POTATO,
            Items.BEETROOT_SEEDS,
            Items.CARROT);

    private final Set<Item> WANTED_ITEMS = ImmutableSet.of(
            Items.WHEAT,
            Items.MELON_SLICE,
            Items.POTATO,
            Items.BEETROOT,
            Items.CARROT);

    private static final Set<Block> CROP_BLOCKS = ImmutableSet.of(
            Blocks.WHEAT,
            Blocks.POTATOES,
            Blocks.CARROTS,
            Blocks.BEETROOTS,
            Blocks.MELON,
            Blocks.PUMPKIN);

    //HoeItem
    private static final Set<Block> TILLABLES = ImmutableSet.of(
            Blocks.DIRT,
            Blocks.ROOTED_DIRT,
            Blocks.COARSE_DIRT,
            Blocks.GRASS_BLOCK);



    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:stone/hoe/igneous_extrusive"));
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }
}

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

    //itemfromstring
    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }
    //Blockfromstring
    private static Block BFS(String S) { return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(S));}

    private static final Set<Item> WANTED_SEEDS = ImmutableSet.of(
            Items.WHEAT_SEEDS,
            Items.MELON_SEEDS,
            Items.POTATO,
            Items.BEETROOT_SEEDS,
            Items.CARROT,
            IFS("tfc:seeds/yellow_bell_pepper"),
            IFS("tfc:seeds/red_bell_pepper"),
            //IFS("tfc:seeds/melon"), spreading
            //IFS("tfc:seeds/pumpkin"), spreading
            IFS("tfc:seeds/papyrus"),
            IFS("tfc:seeds/jute"),
            //IFS("tfc:seeds/tomato"), sticks
            IFS("tfc:seeds/sugarcane"),
            IFS("tfc:seeds/squash"),
            IFS("tfc:seeds/beet"),
            //IFS("tfc:seeds/rice"), waterlogged
            IFS("tfc:seeds/wheat"),
            IFS("tfc:seeds/barley"),
            IFS("tfc:seeds/oat"),
            IFS("tfc:seeds/rye"),
            IFS("tfc:seeds/maize"),
            IFS("tfc:seeds/cabbage"),
            IFS("tfc:seeds/soybean"),
            IFS("tfc:seeds/onion"),
            IFS("tfc:seeds/potato"),
            IFS("tfc:seeds/carrot"),
            IFS("tfc:seeds/garlic"),
            IFS("tfc:seeds/green_bean") //sticks
            );

    private static final Set<Item> FARMED_ITEMS = ImmutableSet.of(
            Items.WHEAT,
            Items.MELON_SLICE,
            Items.POTATO,
            Items.BEETROOT,
            Items.CARROT,
            IFS("tfc:food/crappie"),IFS("tfc:food/wheat"),IFS("tfc:food/oat"),
            IFS("tfc:food/rice"),IFS("tfc:food/maize"),IFS("tfc:food/rye"),IFS("tfc:food/barley"),
            IFS("tfc:food/snowberry"),IFS("tfc:food/blueberry"),IFS("tfc:food/blackberry"),IFS("tfc:food/raspberry"),IFS("tfc:food/gooseberry"),
            IFS("tfc:food/elderberry"),IFS("tfc:food/wintergreen_berry"),IFS("tfc:food/banana"),IFS("tfc:food/cherry"),IFS("tfc:food/green_apple"),
            IFS("tfc:food/lemon"),IFS("tfc:food/olive"),IFS("tfc:food/plum"),IFS("tfc:food/orange"),IFS("tfc:food/peach"),
            IFS("tfc:food/red_apple"),IFS("tfc:food/melon_slice"),IFS("tfc:food/beet"),IFS("tfc:food/soybean"),IFS("tfc:food/carrot"),
            IFS("tfc:food/cabbage"),IFS("tfc:food/green_bean"),IFS("tfc:food/garlic"),IFS("tfc:food/yellow_bell_pepper"),IFS("tfc:food/red_bell_pepper"),
            IFS("tfc:food/green_bell_pepper"),IFS("tfc:food/squash"),IFS("tfc:food/potato"),IFS("tfc:food/onion"),IFS("tfc:food/tomato"),
            IFS("tfc:food/sugarcane"),IFS("tfc:food/cranberry"),IFS("tfc:food/cloudberry"),IFS("tfc:food/bunchberry"),IFS("tfc:food/strawberry")
    );

    private static final Set<Block> CROP_BLOCKS = ImmutableSet.of(
            Blocks.WHEAT,
            Blocks.POTATOES,
            Blocks.CARROTS,
            Blocks.BEETROOTS,
            Blocks.MELON,
            Blocks.PUMPKIN,
            BFS("tfc:crop/yellow_bell_pepper"),
            BFS("tfc:crop/red_bell_pepper"),
            BFS("tfc:crop/papyrus"),
            BFS("tfc:crop/jute"),
            BFS("tfc:crop/sugarcane"),
            BFS("tfc:crop/squash"),
            BFS("tfc:crop/beet"),
            BFS("tfc:crop/wheat"),
            BFS("tfc:crop/barley"),
            BFS("tfc:crop/oat"),
            BFS("tfc:crop/rye"),
            BFS("tfc:crop/maize"),
            BFS("tfc:crop/cabbage"),
            BFS("tfc:crop/soybean"),
            BFS("tfc:crop/onion"),
            BFS("tfc:crop/potato"),
            BFS("tfc:crop/carrot"),
            BFS("tfc:crop/garlic"),
            BFS("tfc:crop/green_bean")
    );

    //HoeItem
    private static final Set<Block> TILLABLES = ImmutableSet.of(
            Blocks.DIRT,
            Blocks.ROOTED_DIRT,
            Blocks.COARSE_DIRT,
            Blocks.GRASS_BLOCK,
            BFS("tfc:dirt/silt"),BFS("tfc:dirt/loam"),BFS("tfc:dirt/sandy_loam"),BFS("tfc:dirt/silty_loam"),
            BFS("tfc:grass/silt"),BFS("tfc:grass/loam"),BFS("tfc:grass/sandy_loam"),BFS("tfc:grass/silty_loam"),
            BFS("tfc:rooted_dirt/silt"),BFS("tfc:rooted_dirt/loam"),BFS("tfc:rooted_dirt/sandy_loam"),BFS("tfc:rooted_dirt/silty_loam")
    );


    public boolean wantsToPickUp(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (FARMED_ITEMS.contains(item) || WANTED_SEEDS.contains(item));
    }

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:stone/hoe/igneous_extrusive"));
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }
}

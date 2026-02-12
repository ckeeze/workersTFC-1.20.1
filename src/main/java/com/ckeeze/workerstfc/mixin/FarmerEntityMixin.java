package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.FarmerEntity;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.crop.Crop;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


@SuppressWarnings("unused")
@Mixin(FarmerEntity.class)
public abstract class FarmerEntityMixin extends AbstractInventoryEntity {

    public FarmerEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private static final Set<Item> WANTED_SEEDS = ImmutableSet.of(
            Items.WHEAT_SEEDS,
            Items.MELON_SEEDS,
            Items.POTATO,
            Items.BEETROOT_SEEDS,
            Items.CARROT,
            TFCItems.CROP_SEEDS.get(Crop.RED_BELL_PEPPER).get(),
            //IFS("tfc:seeds/melon"), spreading
            //IFS("tfc:seeds/pumpkin"), spreading
            TFCItems.CROP_SEEDS.get(Crop.PAPYRUS).get(),
            TFCItems.CROP_SEEDS.get(Crop.JUTE).get(),
            TFCItems.CROP_SEEDS.get(Crop.TOMATO).get(), // sticks
            TFCItems.CROP_SEEDS.get(Crop.SUGARCANE).get(),
            TFCItems.CROP_SEEDS.get(Crop.SQUASH).get(),
            TFCItems.CROP_SEEDS.get(Crop.BEET).get(),
            //IFS("tfc:seeds/rice"), waterlogged
            TFCItems.CROP_SEEDS.get(Crop.WHEAT).get(),
            TFCItems.CROP_SEEDS.get(Crop.BARLEY).get(),
            TFCItems.CROP_SEEDS.get(Crop.OAT).get(),
            TFCItems.CROP_SEEDS.get(Crop.RYE).get(),
            TFCItems.CROP_SEEDS.get(Crop.MAIZE).get(),
            TFCItems.CROP_SEEDS.get(Crop.CABBAGE).get(),
            TFCItems.CROP_SEEDS.get(Crop.SOYBEAN).get(),
            TFCItems.CROP_SEEDS.get(Crop.ONION).get(),
            TFCItems.CROP_SEEDS.get(Crop.POTATO).get(),
            TFCItems.CROP_SEEDS.get(Crop.CARROT).get(),
            TFCItems.CROP_SEEDS.get(Crop.GARLIC).get(),
            TFCItems.CROP_SEEDS.get(Crop.GREEN_BEAN).get() //sticks
            );

    @Unique
    private static final Set<Item> FARMED_ITEMS = ImmutableSet.of(
            Items.WHEAT,
            Items.MELON_SLICE,
            Items.POTATO,
            Items.BEETROOT,
            Items.CARROT,
            TFCItems.FOOD.get(Food.RICE).get(),
            TFCItems.FOOD.get(Food.OAT).get(),
            TFCItems.FOOD.get(Food.BARLEY).get(),
            TFCItems.FOOD.get(Food.RYE).get(),
            TFCItems.FOOD.get(Food.MAIZE).get(),
            TFCItems.FOOD.get(Food.WHEAT).get());

    @Unique
    private static final Set<Block> CROP_BLOCKS = ImmutableSet.of(
            Blocks.WHEAT,
            Blocks.POTATOES,
            Blocks.CARROTS,
            Blocks.BEETROOTS,
            Blocks.MELON,
            Blocks.PUMPKIN,
            TFCBlocks.CROPS.get(Crop.YELLOW_BELL_PEPPER).get(),
            TFCBlocks.CROPS.get(Crop.RED_BELL_PEPPER).get(),
            TFCBlocks.CROPS.get(Crop.PAPYRUS).get(),
            TFCBlocks.CROPS.get(Crop.JUTE).get(),
            TFCBlocks.CROPS.get(Crop.SUGARCANE).get(),
            TFCBlocks.CROPS.get(Crop.SQUASH).get(),
            TFCBlocks.CROPS.get(Crop.BEET).get(),
            TFCBlocks.CROPS.get(Crop.WHEAT).get(),
            TFCBlocks.CROPS.get(Crop.BARLEY).get(),
            TFCBlocks.CROPS.get(Crop.OAT).get(),
            TFCBlocks.CROPS.get(Crop.RYE).get(),
            TFCBlocks.CROPS.get(Crop.MAIZE).get(),
            TFCBlocks.CROPS.get(Crop.CABBAGE).get(),
            TFCBlocks.CROPS.get(Crop.SOYBEAN).get(),
            TFCBlocks.CROPS.get(Crop.ONION).get(),
            TFCBlocks.CROPS.get(Crop.POTATO).get(),
            TFCBlocks.CROPS.get(Crop.CARROT).get(),
            TFCBlocks.CROPS.get(Crop.GARLIC).get(),
            TFCBlocks.CROPS.get(Crop.GREEN_BEAN).get(),
            TFCBlocks.CROPS.get(Crop.TOMATO).get()
    );

    //HoeItem
    @Unique
    private static final Set<Block> workersTFC_1_20_X$TILLABLES = ImmutableSet.of(
            Blocks.DIRT,
            Blocks.ROOTED_DIRT,
            Blocks.COARSE_DIRT,
            Blocks.GRASS_BLOCK,
            TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.SILT).get(),TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.LOAM).get(),
            TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.SANDY_LOAM).get(),TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.SILTY_LOAM).get(),
            TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.SILT).get(),TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.LOAM).get(),
            TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.SANDY_LOAM).get(),TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.SILTY_LOAM).get(),
            TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.SILT).get(),TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.LOAM).get(),
            TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.SILTY_LOAM).get(),TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.SILTY_LOAM).get()
    );


    public boolean wantsToPickUp(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (FARMED_ITEMS.contains(item) || WANTED_SEEDS.contains(item) || itemStack.is(TFCTags.Items.GOAT_FOOD) || itemStack.is(Items.STICK));
    }

    /**
     * @author Ckeeze
     * @reason Changing starter equipment to TFC items
     */
    @Overwrite(remap = false)
    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.ROCK_TOOLS.get(RockCategory.IGNEOUS_EXTRUSIVE).get(RockCategory.ItemType.HOE).get());
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);
    }

    /**
     * @author Ckeeze
     * @reason Changing displayed items to TFC items
     */
    @Overwrite(remap = false)
    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.HOE).get(),
                TFCItems.CROP_SEEDS.get(Crop.BARLEY).get(),
                TFCItems.CROP_SEEDS.get(Crop.OAT).get(),
                TFCItems.CROP_SEEDS.get(Crop.RYE).get(),
                TFCItems.CROP_SEEDS.get(Crop.MAIZE).get());
    }
}

package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.Translatable;
import com.talhanation.workers.entities.FarmerEntity;
import com.talhanation.workers.entities.ai.FarmerAI;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blockentities.IFarmland;
import net.dries007.tfc.common.blocks.GroundcoverBlockType;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.crop.Crop;
import net.dries007.tfc.common.blocks.crop.CropBlock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.Powder;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.Climate;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;
import java.util.Set;

import static com.talhanation.workers.entities.FarmerEntity.CROP_BLOCKS;
import static net.dries007.tfc.common.blockentities.FarmlandBlockEntity.NutrientType.*;
import static net.dries007.tfc.common.blocks.soil.FarmlandBlock.getHydration;

@SuppressWarnings("unused")
@Mixin(FarmerAI.class)
public abstract class FarmerAIMixin extends Goal {

    private final FarmerEntity farmer;
    private BlockPos waterPos;

    public FarmerAIMixin(FarmerEntity farmer) {
        this.farmer = farmer;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    //MEHTODS AND SETS NEEDED FOR TFC
    private static final Set<Item> FERTILIZER = ImmutableSet.of(
            TFCItems.POWDERS.get(Powder.WOOD_ASH).get(), TFCItems.POWDERS.get(Powder.SYLVITE).get(), TFCItems.POWDERS.get(Powder.SALTPETER).get(),
            TFCItems.COMPOST.get(), TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem(),
            Items.BONE_MEAL
    );
    private static final Set<Item> NITROGENF = ImmutableSet.of(
            TFCItems.POWDERS.get(Powder.SALTPETER).get(), TFCItems.COMPOST.get(),TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem()
    );
    private static final Set<Item> POTASSIUMF = ImmutableSet.of(
            TFCItems.POWDERS.get(Powder.WOOD_ASH).get(), TFCItems.POWDERS.get(Powder.SYLVITE).get(), TFCItems.POWDERS.get(Powder.SALTPETER).get(),
            TFCItems.COMPOST.get(),TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem()
    );
    private static final Set<Item> PHOSPHORF = ImmutableSet.of(
            TFCItems.POWDERS.get(Powder.WOOD_ASH).get(), TFCItems.COMPOST.get(),TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem(),
            Items.BONE_MEAL
    );

    private static final Set<Item> FARMED_ITEMS = ImmutableSet.of(
            Items.WHEAT,
            Items.MELON_SLICE,
            Items.POTATO,
            Items.BEETROOT,
            Items.CARROT,
            TFCItems.FOOD.get(Food.WHEAT).get(),TFCItems.FOOD.get(Food.OAT).get(),
            TFCItems.FOOD.get(Food.RICE).get(),TFCItems.FOOD.get(Food.MAIZE).get(),TFCItems.FOOD.get(Food.RYE).get(),TFCItems.FOOD.get(Food.BARLEY).get(),
            TFCItems.FOOD.get(Food.SNOWBERRY).get(),TFCItems.FOOD.get(Food.BLUEBERRY).get(),TFCItems.FOOD.get(Food.BLACKBERRY).get(),TFCItems.FOOD.get(Food.RASPBERRY).get(),TFCItems.FOOD.get(Food.GOOSEBERRY).get(),
            TFCItems.FOOD.get(Food.ELDERBERRY).get(),TFCItems.FOOD.get(Food.WINTERGREEN_BERRY).get(),TFCItems.FOOD.get(Food.BANANA).get(),TFCItems.FOOD.get(Food.CHERRY).get(),TFCItems.FOOD.get(Food.GREEN_APPLE).get(),
            TFCItems.FOOD.get(Food.LEMON).get(),TFCItems.FOOD.get(Food.OLIVE).get(),TFCItems.FOOD.get(Food.PLUM).get(),TFCItems.FOOD.get(Food.ORANGE).get(),TFCItems.FOOD.get(Food.PEACH).get(),
            TFCItems.FOOD.get(Food.RED_APPLE).get(),TFCItems.FOOD.get(Food.MELON_SLICE).get(),TFCItems.FOOD.get(Food.BEET).get(),TFCItems.FOOD.get(Food.SOYBEAN).get(),TFCItems.FOOD.get(Food.CARROT).get(),
            TFCItems.FOOD.get(Food.CABBAGE).get(),TFCItems.FOOD.get(Food.GREEN_BEAN).get(),TFCItems.FOOD.get(Food.GARLIC).get(),TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(),
            TFCItems.FOOD.get(Food.GREEN_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.SQUASH).get(),TFCItems.FOOD.get(Food.POTATO).get(),TFCItems.FOOD.get(Food.ONION).get(),TFCItems.FOOD.get(Food.TOMATO).get(),
            TFCItems.FOOD.get(Food.SUGARCANE).get(),TFCItems.FOOD.get(Food.CRANBERRY).get(),TFCItems.FOOD.get(Food.CLOUDBERRY).get(),TFCItems.FOOD.get(Food.BUNCHBERRY).get(),TFCItems.FOOD.get(Food.STRAWBERRY ).get()
    );

    //TILLING
    private void prepareFarmLand(BlockPos blockPos) {
        // Make sure the center block remains waterlogged.
        BlockState blockState = this.farmer.getCommandSenderWorld().getBlockState(blockPos);
        Block block = blockState.getBlock();
        if(blockPos != waterPos && FarmerEntity.TILLABLES.contains(block)) {
            if (block == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.SILT).get() || block == TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.SILT).get()|| block == TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.SILT).get()){
                farmer.getCommandSenderWorld().setBlock(blockPos, TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILT).get().defaultBlockState(), 3);
            }
            else if (block == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.LOAM).get() || block == TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.LOAM).get()|| block == TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.LOAM).get()){
                farmer.getCommandSenderWorld().setBlock(blockPos, TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.LOAM).get().defaultBlockState(), 3);
            }
            else if (block == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.SANDY_LOAM).get() || block == TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.SANDY_LOAM).get()|| block == TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.SANDY_LOAM).get()){
                farmer.getCommandSenderWorld().setBlock(blockPos, TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SANDY_LOAM).get().defaultBlockState(), 3);
            }
            else if (block == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(SoilBlockType.Variant.SILTY_LOAM).get() || block == TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(SoilBlockType.Variant.SILTY_LOAM).get()|| block == TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(SoilBlockType.Variant.SILTY_LOAM).get()){
                farmer.getCommandSenderWorld().setBlock(blockPos, TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILTY_LOAM).get().defaultBlockState(), 3);
            }
            else{
                farmer.getCommandSenderWorld().setBlock(blockPos, Blocks.FARMLAND.defaultBlockState(), 3);
            }
            farmer.getCommandSenderWorld().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.HOE_TILL,
                    SoundSource.BLOCKS, 1.0F, 1.0F);
            BlockState blockStateAbove = this.farmer.getCommandSenderWorld().getBlockState(blockPos.above());
            Block blockAbove = blockStateAbove.getBlock();
            farmer.workerSwingArm();
            farmer.consumeToolDurability();
            if (blockAbove instanceof BushBlock || blockAbove instanceof GrowingPlantBlock) {
                farmer.getCommandSenderWorld().destroyBlock(blockPos.above(), false);
                farmer.getCommandSenderWorld().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.GRASS_BREAK,
                        SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

    }

    //disable water creation for TFC ballance
    private boolean startPosIsWater() {
        return true;
    }

    //GETTING SEEDS
    private boolean hasSpaceInInv() {
        SimpleContainer inventory = farmer.getInventory();
        return inventory.canAddItem(FARMED_ITEMS.stream().findAny().get().getDefaultInstance());
    }

    //PLANTING
    private void plantSeedsFromInv(BlockPos blockPos) {
        SimpleContainer inventory = farmer.getInventory();
        int Hyd = getHydration(this.farmer.level(), blockPos.below());
        float Temp = Climate.getTemperature(this.farmer.level(), blockPos.below());

        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            boolean flag = false;
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Items.CARROT) {
                    farmer.level().setBlock(blockPos, Blocks.CARROTS.defaultBlockState(), 3);
                    flag = true;

                } else if (itemstack.getItem() == Items.POTATO) {
                    this.farmer.level().setBlock(blockPos, Blocks.POTATOES.defaultBlockState(), 3);
                    flag = true;

                } else if (itemstack.getItem() == Items.WHEAT_SEEDS) {
                    this.farmer.level().setBlock(blockPos, Blocks.WHEAT.defaultBlockState(), 3);
                    flag = true;

                } else if (itemstack.getItem() == Items.BEETROOT_SEEDS) {
                    this.farmer.level().setBlock(blockPos, Blocks.BEETROOTS.defaultBlockState(), 3);
                    flag = true;
                }
                //TFC plants
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.YELLOW_BELL_PEPPER).get()){
                    if(Hyd >= 25 && Hyd <= 60 && Temp >= 19.0 && Temp <= 27.0){
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.YELLOW_BELL_PEPPER).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.RED_BELL_PEPPER).get()){
                    if(Hyd >= 25 && Hyd <= 60 && Temp >= 19.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.RED_BELL_PEPPER).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.PAPYRUS).get()){
                    if(Hyd >= 70 && Temp >= 22.0 && Temp <= 34.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.PAPYRUS).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.JUTE).get()){
                    if(Hyd >= 25 && Temp >= 8.0 && Temp <= 34.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.JUTE).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.SUGARCANE).get()){
                    if(Hyd >= 40 && Temp >= 15.0 && Temp <= 35.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.SUGARCANE).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.SQUASH).get()){
                    if(Hyd >= 23 && Hyd <= 95 && Temp >= 8.0 && Temp <= 30.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.SQUASH).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.BEET).get()){
                    if(Hyd >= 15 && Hyd <= 85 && Temp >= -2.0 && Temp <= 17.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.BEET).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.WHEAT).get()){
                    if(Hyd >= 25 && Temp >= -1.0 && Temp <= 32.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.WHEAT).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.BARLEY).get()){
                    if(Hyd >= 18 && Hyd <= 75 && Temp >= -5.0 && Temp <= 23.0){
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.BARLEY).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.OAT).get()){
                    if(Hyd >= 35 && Temp >= 6.0 && Temp <= 37.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.OAT).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.RYE).get()){
                    if(Hyd >= 25 && Hyd <= 85 && Temp >= -8.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.RYE).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.MAIZE).get()){
                    if(Hyd >= 75 && Temp >= 16.0 && Temp <= 37.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.MAIZE).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.CABBAGE).get()){
                    if(Hyd >= 15 && Hyd <= 65 && Temp >= -7.0 && Temp <= 24.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.CABBAGE).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.SOYBEAN).get()){
                    if(Hyd >= 40 && Temp >= 11.0 && Temp <= 27.0){
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.SOYBEAN).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.ONION).get()){
                    if(Hyd >= 25 && Hyd <= 90 && Temp >= 3.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.ONION).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.POTATO).get()){
                    if(Hyd >= 50 && Temp >= 2.0 && Temp <= 34.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.POTATO).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.CARROT).get()){
                    if(Hyd >= 25 && Temp >= 6.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.CARROT).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == TFCItems.CROP_SEEDS.get(Crop.GARLIC).get()){
                    if(Hyd >= 15 && Hyd <= 75 && Temp >= -17.0 && Temp <= 15.0) {
                        this.farmer.level().setBlock(blockPos, TFCBlocks.CROPS.get(Crop.GARLIC).get().defaultBlockState(), 3);
                        flag = true;
                    }
                }
                //more plants
                /*else if (itemstack.getItem() == IFS("tfc:seeds/rice")){
                    {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/rice").defaultBlockState(), 3);
                        flag = true;
                    }
                }*/
            }

            if (flag) {
                farmer.level().playSound(null, (double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ(), SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                itemstack.shrink(1);
                if (itemstack.isEmpty()) {
                    inventory.setItem(i, ItemStack.EMPTY);
                }
                break;
            }
        }
    }

    public BlockPos getPlantPos() {
        // int range = 8;
        for (int j = 0; j <= 8; j++) {
            for (int i = 0; i <= 8; i++) {
                BlockPos blockPos = this.waterPos.offset(j - 4, 0, i - 4);
                BlockPos aboveBlockPos = blockPos.above();
                BlockState blockState = this.farmer.level().getBlockState(blockPos);
                BlockState aboveBlockState = this.farmer.level().getBlockState(aboveBlockPos);

                Block block = blockState.getBlock();
                Block aboveBlock = aboveBlockState.getBlock();
                if ((block == Blocks.FARMLAND ||block == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILTY_LOAM).get() ||
                        block == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILT).get() ||
                        block == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SANDY_LOAM).get() ||
                        block == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.LOAM).get() ) && aboveBlock == Blocks.AIR) {
                    return aboveBlockPos;
                }
            }
        }
        return null;
    }

    //HARVESTING
    public BlockPos getHarvestPos() {
        // int range = 8;
        for (int j = 0; j <= 8; j++) {
            for (int i = 0; i <= 8; i++) {
                BlockPos blockPos = waterPos.offset(j - 4, 1, i - 4);
                BlockState blockState = this.farmer.level().getBlockState(blockPos);
                Block block = blockState.getBlock();

                if (CROP_BLOCKS.contains(block)) {
                    BlockPos belowBlockPos = blockPos.below();
                    BlockState belowBlockState = this.farmer.level().getBlockState(belowBlockPos);

                    if (block instanceof CropBlock crop && (belowBlockState.is(Blocks.FARMLAND) ||
                            belowBlockState.is(TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILT).get()) ||
                            belowBlockState.is(TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.LOAM).get()) ||
                            belowBlockState.is(TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILTY_LOAM).get()) ||
                            belowBlockState.is(TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SANDY_LOAM).get()))) {

                        if (crop.isMaxAge(blockState)) {
                            return blockPos;
                        }
                    }
                }
            }
        }
        return null;
    }


    //NUTRIENT MANAGEMENT
    private boolean hasBone() {
        SimpleContainer inventory = farmer.getInventory();
        return inventory.hasAnyOf(FERTILIZER);
    }

    public BlockPos getFertilizePos() {
        SimpleContainer inventory = farmer.getInventory();
        // int range = 8;
        for (int j = 0; j <= 8; j++) {
            for (int i = 0; i <= 8; i++) {
                BlockPos blockPos = waterPos.offset(j - 4, 1, i - 4);
                BlockState blockState = this.farmer.getCommandSenderWorld().getBlockState(blockPos);
                Block block = blockState.getBlock();

                if (CROP_BLOCKS.contains(block)) {
                    BlockPos belowBlockPos = blockPos.below();
                    BlockState belowBlockState = this.farmer.getCommandSenderWorld().getBlockState(belowBlockPos);
                    Block belowBlock = belowBlockState.getBlock();
                    BlockEntity Farmland = this.farmer.getCommandSenderWorld().getBlockEntity(belowBlockPos);

                    if (block instanceof CropBlock crop && (belowBlock == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILTY_LOAM).get() ||
                            belowBlock == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SILT).get() ||
                            belowBlock == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.SANDY_LOAM).get() ||
                            belowBlock == TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(SoilBlockType.Variant.LOAM).get() )) {
                        if (!crop.isMaxAge(blockState)) {
                            if(inventory.hasAnyOf(PHOSPHORF) && crop.getPrimaryNutrient() == PHOSPHOROUS)
                            {
                                if(Farmland instanceof IFarmland farmland && farmland.getNutrient(PHOSPHOROUS) < 0.2)
                                {
                                    return blockPos;
                                }
                            }
                            if(inventory.hasAnyOf(NITROGENF) && crop.getPrimaryNutrient() == NITROGEN)
                            {
                                if(Farmland instanceof IFarmland farmland && farmland.getNutrient(NITROGEN) < 0.2)
                                {
                                    return blockPos;
                                }
                            }
                            if(inventory.hasAnyOf(POTASSIUMF) && crop.getPrimaryNutrient() == POTASSIUM)
                            {
                                if(Farmland instanceof IFarmland farmland && farmland.getNutrient(POTASSIUM) < 0.2)
                                {
                                    return blockPos;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private void fertilizeSeeds(BlockPos workPos) {
        SimpleContainer inventory = farmer.getInventory();
        BlockState state = farmer.getCommandSenderWorld().getBlockState(workPos);
        Block block = state.getBlock();
        BlockEntity Farmland = this.farmer.getCommandSenderWorld().getBlockEntity(workPos.below());

        if(block instanceof CropBlock crop && !this.farmer.getCommandSenderWorld().isClientSide() && Farmland instanceof IFarmland farmland) {
            for (int i = 0; i < inventory.getContainerSize(); ++i) {
                ItemStack itemstack = inventory.getItem(i);
                boolean flag = false;
                if (!itemstack.isEmpty()) {
                    if (crop.getPrimaryNutrient() == PHOSPHOROUS) {
                        if (itemstack.getItem() == Items.BONE_MEAL) {
                            farmland.addNutrient(PHOSPHOROUS, 0.1F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCItems.POWDERS.get(Powder.WOOD_ASH).get()) {
                            farmland.addNutrient(PHOSPHOROUS, 0.1F);
                            farmland.addNutrient(POTASSIUM, 0.3F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCItems.COMPOST.get()) {
                            farmland.addNutrient(NITROGEN, 0.4F);
                            farmland.addNutrient(PHOSPHOROUS, 0.2F);
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem()) {
                            farmland.addNutrient(NITROGEN, 0.8F);
                            farmland.addNutrient(PHOSPHOROUS, 0.5F);
                            farmland.addNutrient(POTASSIUM, 0.1F);
                            flag = true;
                        }

                    }
                    if (crop.getPrimaryNutrient() == POTASSIUM) {
                        if (itemstack.getItem() == TFCItems.POWDERS.get(Powder.SYLVITE).get()) {
                            farmland.addNutrient(POTASSIUM, 0.5F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCItems.POWDERS.get(Powder.WOOD_ASH).get()) {
                            farmland.addNutrient(PHOSPHOROUS, 0.1F);
                            farmland.addNutrient(POTASSIUM, 0.3F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCItems.POWDERS.get(Powder.SALTPETER).get()) {
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            farmland.addNutrient(NITROGEN, 0.1F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCItems.COMPOST.get()) {
                            farmland.addNutrient(NITROGEN, 0.4F);
                            farmland.addNutrient(PHOSPHOROUS, 0.2F);
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem()) {
                            farmland.addNutrient(NITROGEN, 0.8F);
                            farmland.addNutrient(PHOSPHOROUS, 0.5F);
                            farmland.addNutrient(POTASSIUM, 0.1F);
                            flag = true;
                        }
                    }
                    if (crop.getPrimaryNutrient() == NITROGEN) {
                        if (itemstack.getItem() == TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem()) {
                            farmland.addNutrient(NITROGEN, 0.8F);
                            farmland.addNutrient(PHOSPHOROUS, 0.5F);
                            farmland.addNutrient(POTASSIUM, 0.1F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCItems.POWDERS.get(Powder.SALTPETER).get()) {
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            farmland.addNutrient(NITROGEN, 0.1F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == TFCItems.COMPOST.get()) {
                            farmer.tellPlayer(farmer.getOwner(), Translatable.TEXT_CHEST_ERROR); //
                            farmland.addNutrient(NITROGEN, 0.4F);
                            farmland.addNutrient(PHOSPHOROUS, 0.2F);
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            flag = true;
                        }
                    }
                    if (flag) {
                        itemstack.shrink(1);
                        Helpers.playSound(farmer.level(), workPos, TFCSounds.FERTILIZER_USE.get());
                        if (itemstack.isEmpty()) {
                            inventory.setItem(i, ItemStack.EMPTY);
                        }
                        break;
                    }
                }
            }
        }
    }
}

package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.Translatable;
import com.talhanation.workers.entities.FarmerEntity;
import com.talhanation.workers.entities.ai.FarmerAI;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blockentities.IFarmland;
import net.dries007.tfc.common.blocks.crop.CropBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.Climate;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;
import java.util.Set;

import static com.talhanation.workers.entities.FarmerEntity.CROP_BLOCKS;
import static net.dries007.tfc.common.blockentities.FarmlandBlockEntity.NutrientType.*;
import static net.dries007.tfc.common.blocks.soil.FarmlandBlock.getHydration;

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
            IFS("tfc:powder/wood_ash"), IFS("tfc:powder/sylvite"), IFS("tfc:powder/saltpeter"),
            IFS("tfc:compost"),IFS("tfc:groundcover/guano"),
            Items.BONE_MEAL
    );
    private static final Set<Item> NITROGENF = ImmutableSet.of(
            IFS("tfc:powder/saltpeter"), IFS("tfc:compost"),IFS("tfc:guano")
    );
    private static final Set<Item> POTASSIUMF = ImmutableSet.of(
            IFS("tfc:powder/wood_ash"), IFS("tfc:powder/sylvite"), IFS("tfc:powder/saltpeter"),
            IFS("tfc:compost"),IFS("tfc:groundcover/guano")
    );
    private static final Set<Item> PHOSPHORF = ImmutableSet.of(
            IFS("tfc:powder/wood_ash"), IFS("tfc:compost"),IFS("tfc:groundcover/guano"),
            Items.BONE_MEAL
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

    private static Item IFS(String S) { return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));}
    private static Block BFS(String S) { return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(S));}

    //TILLING
    private void prepareFarmLand(BlockPos blockPos) {
        // Make sure the center block remains waterlogged.
        BlockState blockState = this.farmer.getCommandSenderWorld().getBlockState(blockPos);
        Block block = blockState.getBlock();
        if(blockPos != waterPos && FarmerEntity.TILLABLES.contains(block)) {
            if (block == BFS("tfc:dirt/silt") || block == BFS("tfc:grass/silt" )|| block == BFS("tfc:rooted_dirt/silt")){
                farmer.getCommandSenderWorld().setBlock(blockPos, BFS("tfc:farmland/silt").defaultBlockState(), 3);
            }
            else if (block == BFS("tfc:dirt/loam") || block == BFS("tfc:grass/loam" )|| block == BFS("tfc:rooted_dirt/loam")){
                farmer.getCommandSenderWorld().setBlock(blockPos, BFS("tfc:farmland/loam").defaultBlockState(), 3);
            }
            else if (block == BFS("tfc:dirt/sandy_loam") || block == BFS("tfc:grass/sandy_loam" )|| block == BFS("tfc:rooted_dirt/sandy_loam")){
                farmer.getCommandSenderWorld().setBlock(blockPos, BFS("tfc:farmland/sandy_loam").defaultBlockState(), 3);
            }
            else if (block == BFS("tfc:dirt/silty_loam") || block == BFS("tfc:grass/silty_loam" )|| block == BFS("tfc:rooted_dirt/silty_loam")){
                farmer.getCommandSenderWorld().setBlock(blockPos, BFS("tfc:farmland/silty_loam").defaultBlockState(), 3);
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
                else if (itemstack.getItem() == IFS("tfc:seeds/yellow_bell_pepper")){
                    if(Hyd >= 25 && Hyd <= 60 && Temp >= 19.0 && Temp <= 27.0){
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/yellow_bell_pepper").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/red_bell_pepper")){
                    if(Hyd >= 25 && Hyd <= 60 && Temp >= 19.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/red_bell_pepper").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/papyrus")){
                    if(Hyd >= 70 && Temp >= 22.0 && Temp <= 34.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/papyrus").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/jute")){
                    if(Hyd >= 25 && Temp >= 8.0 && Temp <= 34.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/jute").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/sugarcane")){
                    if(Hyd >= 40 && Temp >= 15.0 && Temp <= 35.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/sugarcane").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/squash")){
                    if(Hyd >= 23 && Hyd <= 95 && Temp >= 8.0 && Temp <= 30.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/squash").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/beet")){
                    if(Hyd >= 15 && Hyd <= 85 && Temp >= -2.0 && Temp <= 17.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/beet").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/wheat")){
                    if(Hyd >= 25 && Temp >= -1.0 && Temp <= 32.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/wheat").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/barley")){
                    if(Hyd >= 18 && Hyd <= 75 && Temp >= -5.0 && Temp <= 23.0){
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/barley").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/oat")){
                    if(Hyd >= 35 && Temp >= 6.0 && Temp <= 37.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/oat").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/rye")){
                    if(Hyd >= 25 && Hyd <= 85 && Temp >= -8.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/rye").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/maize")){
                    if(Hyd >= 75 && Temp >= 16.0 && Temp <= 37.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/maize").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/cabbage")){
                    if(Hyd >= 15 && Hyd <= 65 && Temp >= -7.0 && Temp <= 24.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/cabbage").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/soybean")){
                    if(Hyd >= 40 && Temp >= 11.0 && Temp <= 27.0){
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/soybean").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/onion")){
                    if(Hyd >= 25 && Hyd <= 90 && Temp >= 3.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/onion").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/potato")){
                    if(Hyd >= 50 && Temp >= 2.0 && Temp <= 34.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/potato").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/carrot")){
                    if(Hyd >= 25 && Temp >= 6.0 && Temp <= 27.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/carrot").defaultBlockState(), 3);
                        flag = true;
                    }
                }
                else if (itemstack.getItem() == IFS("tfc:seeds/garlic")){
                    if(Hyd >= 15 && Hyd <= 75 && Temp >= -17.0 && Temp <= 15.0) {
                        this.farmer.level().setBlock(blockPos, BFS("tfc:crop/garlic").defaultBlockState(), 3);
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
                if ((block == Blocks.FARMLAND || block == BFS("tfc:farmland/silt") || block == BFS("tfc:farmland/loam") ||
                        block == BFS("tfc:farmland/sandy_loam") || block == BFS("tfc:farmland/silty_loam") ) && aboveBlock == Blocks.AIR) {
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

                    if (block instanceof CropBlock crop && (belowBlockState.is(Blocks.FARMLAND) || belowBlockState.is(BFS("tfc:farmland/silt")) ||belowBlockState.is(BFS("tfc:farmland/loam")) || belowBlockState.is(BFS("tfc:farmland/sandy_loam")) || belowBlockState.is(BFS("tfc:farmland/silty_loam")))) {

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

                    if (block instanceof CropBlock crop && (belowBlock == BFS("tfc:farmland/silt") || belowBlock == BFS("tfc:farmland/loam") ||
                            belowBlock == BFS("tfc:farmland/sandy_loam") || belowBlock == BFS("tfc:farmland/silty_loam") )) {
                        if (!crop.isMaxAge(blockState)) {
                            if(inventory.hasAnyOf(PHOSPHORF) && (crop == BFS("tfc:crop/oat") ||
                                    crop == BFS("tfc:crop/rye") ||crop == BFS("tfc:crop/maize") ||
                                    crop == BFS("tfc:crop/wheat") ||crop == BFS("tfc:crop/rice") ||
                                    crop == BFS("tfc:crop/pumpkin") ||crop == BFS("tfc:crop/melon")))
                            {
                                if(Farmland instanceof IFarmland farmland && farmland.getNutrient(PHOSPHOROUS) < 0.2)
                                {
                                    return blockPos;
                                }
                            }
                            if(inventory.hasAnyOf(NITROGENF) && (crop == BFS("tfc:crop/barley") ||
                                    crop == BFS("tfc:crop/cabbage" ) || crop == BFS("tfc:crop/garlic" ) ||
                                    crop == BFS("tfc:crop/green_beans" ) || crop == BFS("tfc:crop/onions" ) ||
                                    crop == BFS("tfc:crop/soybean" )))
                            {
                                if(Farmland instanceof IFarmland farmland && farmland.getNutrient(NITROGEN) < 0.2)
                                {
                                    return blockPos;
                                }
                            }
                            if(inventory.hasAnyOf(POTASSIUMF) && (crop == BFS("tfc:crop/beet") ||
                                    crop == BFS("tfc:crop/carrot") ||crop == BFS("tfc:crop/potatoes") ||
                                    crop == BFS("tfc:crop/red_bell_peppers") ||crop == BFS("tfc:crop/yellow_bell_peppers") ||
                                    crop == BFS("tfc:crop/squash") ||crop == BFS("tfc:crop/sugarcane") ||
                                    crop == BFS("tfc:crop/tomato") ||crop == BFS("tfc:crop/jute") || crop == BFS("tfc:crop/papyrus")))
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
                        else if (itemstack.getItem() == IFS("tfc:powder/wood_ash")) {
                            farmland.addNutrient(PHOSPHOROUS, 0.1F);
                            farmland.addNutrient(POTASSIUM, 0.3F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:compost")) {
                            farmland.addNutrient(NITROGEN, 0.4F);
                            farmland.addNutrient(PHOSPHOROUS, 0.2F);
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:groundcoverguano")) {
                            farmland.addNutrient(NITROGEN, 0.8F);
                            farmland.addNutrient(PHOSPHOROUS, 0.5F);
                            farmland.addNutrient(POTASSIUM, 0.1F);
                            flag = true;
                        }

                    }
                    if (crop.getPrimaryNutrient() == POTASSIUM) {
                        if (itemstack.getItem() == IFS("tfc:powder/sylvite")) {
                            farmland.addNutrient(POTASSIUM, 0.5F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:powder/wood_ash")) {
                            farmland.addNutrient(PHOSPHOROUS, 0.1F);
                            farmland.addNutrient(POTASSIUM, 0.3F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:powder/saltpeter")) {
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            farmland.addNutrient(NITROGEN, 0.1F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:compost")) {
                            farmland.addNutrient(NITROGEN, 0.4F);
                            farmland.addNutrient(PHOSPHOROUS, 0.2F);
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:guano")) {
                            farmland.addNutrient(NITROGEN, 0.8F);
                            farmland.addNutrient(PHOSPHOROUS, 0.5F);
                            farmland.addNutrient(POTASSIUM, 0.1F);
                            flag = true;
                        }
                    }
                    if (crop.getPrimaryNutrient() == NITROGEN) {
                        if (itemstack.getItem() == IFS("tfc:groundcover/guano")) {
                            farmland.addNutrient(NITROGEN, 0.8F);
                            farmland.addNutrient(PHOSPHOROUS, 0.5F);
                            farmland.addNutrient(POTASSIUM, 0.1F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:powder/saltpeter")) {
                            farmland.addNutrient(POTASSIUM, 0.4F);
                            farmland.addNutrient(NITROGEN, 0.1F);
                            flag = true;
                        }
                        else if (itemstack.getItem() == IFS("tfc:compost")) {
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

package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.Translatable;
import com.talhanation.workers.entities.FishermanEntity;
import com.talhanation.workers.entities.ai.FishermanAI;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;

@SuppressWarnings("unused")
@Mixin(FishermanAI.class)
public abstract class FishermanAIMixin extends Goal {
    private final FishermanEntity fisherman;
    private int fishingTimer = 1000;
    private int fishingRange;
    private BlockPos coastPos;
    private boolean DEBUG = false;
    private BlockPos fishingPos = null;
    public FishermanAIMixin(FishermanEntity fishermanEntity) {
        this.fisherman = fishermanEntity;
    }

    //Changed for modded water fishing
    @Overwrite(remap = false)
    private BlockPos findWaterBlocks() {
        List<BlockPos> waterBlocks = new ArrayList<>();
        Direction direction = this.fisherman.getFishingDirection();
        Direction directionR = direction.getClockWise();
        Direction directionL = direction.getCounterClockWise();

        if (coastPos != null) {
            int length = getDistanceWithWater(coastPos, direction);
            int lengthR = getDistanceWithWater(coastPos, directionR);
            int lengthL = getDistanceWithWater(coastPos, directionL);
            int lengthB = getDistanceWithWater(coastPos, direction.getOpposite());

            for (int x = 0; x <= length; ++x) {
                BlockPos pos = this.coastPos.relative(direction, x);
                BlockState targetBlock = this.fisherman.getCommandSenderWorld().getBlockState(pos);
                if(DEBUG) fisherman.getCommandSenderWorld().setBlock(pos.above(4), Blocks.ICE.defaultBlockState(), 3);

                double distance = fisherman.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                if(targetBlock.getFluidState().is(TFCTags.Fluids.WATER_LIKE) && distance > fishingRange) {
                    for (int i = 0; i < 4; i++) {
                        if (this.fisherman.getCommandSenderWorld().getBlockState(pos.above(i)).isAir() && i == 3) {
                            waterBlocks.add(pos);
                            break;
                        }
                    }
                }
            }

            for (int x = 0; x <= lengthR; ++x) {
                BlockPos pos = this.coastPos.relative(directionR, x);
                BlockState targetBlock = this.fisherman.getCommandSenderWorld().getBlockState(pos);
                if(DEBUG) fisherman.getCommandSenderWorld().setBlock(pos.above(4), Blocks.ICE.defaultBlockState(), 3);

                double distance = fisherman.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                if (targetBlock.getFluidState().is(TFCTags.Fluids.WATER_LIKE) && distance > fishingRange) {
                    for (int i = 0; i < 4; i++) {
                        if (this.fisherman.getCommandSenderWorld().getBlockState(pos.above(i)).isAir() && i == 3) {
                            waterBlocks.add(pos);
                            break;
                        }
                    }
                }
            }

            for (int x = 0; x <= lengthL; ++x) {
                BlockPos pos = this.coastPos.relative(directionL, x);
                BlockState targetBlock = this.fisherman.getCommandSenderWorld().getBlockState(pos);
                if(DEBUG) fisherman.getCommandSenderWorld().setBlock(pos.above(4), Blocks.ICE.defaultBlockState(), 3);

                double distance = fisherman.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                if (targetBlock.getFluidState().is(TFCTags.Fluids.WATER_LIKE) && distance > fishingRange) {
                    for (int i = 0; i < 4; i++) {
                        if (this.fisherman.getCommandSenderWorld().getBlockState(pos.above(i)).isAir() && i == 3) {
                            waterBlocks.add(pos);
                            break;
                        }
                    }
                }
            }

            for (int x = 0; x <= lengthB; ++x) {
                BlockPos pos = this.coastPos.relative(direction.getOpposite(), x);
                BlockState targetBlock = this.fisherman.getCommandSenderWorld().getBlockState(pos);
                if(DEBUG) fisherman.getCommandSenderWorld().setBlock(pos.above(4), Blocks.ICE.defaultBlockState(), 3);

                double distance = fisherman.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                if (targetBlock.getFluidState().is(TFCTags.Fluids.WATER_LIKE) && distance > fishingRange) {
                    for (int i = 0; i < 4; i++) {
                        if (this.fisherman.getCommandSenderWorld().getBlockState(pos.above(i)).isAir() && i == 3) {
                            waterBlocks.add(pos);
                            break;
                        }
                    }
                }
            }
        }

        waterBlocks.sort(Comparator.comparing(this::getWaterDepth).reversed());
        if(fishingRange < 10){
            if(!waterBlocks.isEmpty()){
                List<BlockPos> validWaterSpots = new ArrayList<>();
                for(BlockPos pos : waterBlocks){
                    if(isValidFishingSpot(pos, true)){
                        validWaterSpots.add(pos);
                    }
                }

                BlockPos fishingSpot;
                if(validWaterSpots.isEmpty() && !waterBlocks.isEmpty()){//do not simplify
                    fishingSpot = waterBlocks.get((waterBlocks.size() / 2));
                }
                else fishingSpot = validWaterSpots.get(0);

                if(DEBUG) fisherman.getCommandSenderWorld().setBlock(new BlockPos(fishingSpot.getX(), fishingSpot.getY() + 5, fishingSpot.getZ()), Blocks.PACKED_ICE.defaultBlockState(), 3);

                return fishingSpot;
            }
            else
                return null;
        }
        else{
            if(!waterBlocks.isEmpty()){
                List<BlockPos> validWaterSpots = new ArrayList<>();
                for(BlockPos pos : waterBlocks){
                    if(isValidFishingSpot(pos, false)){
                        validWaterSpots.add(pos);
                    }
                }

                BlockPos fishingSpot;
                if(validWaterSpots.isEmpty() && !waterBlocks.isEmpty()){//do not simplify
                    fishingSpot = waterBlocks.get((waterBlocks.size() / 2));
                }
                else {
                    validWaterSpots.sort(Comparator.comparing(this::getDistanceToFisherStartPos).reversed());
                    fishingSpot = validWaterSpots.get(validWaterSpots.size() / 2);
                }

                if(DEBUG) fisherman.getCommandSenderWorld().setBlock(new BlockPos(fishingSpot.getX(), fishingSpot.getY() + 5, fishingSpot.getZ()), Blocks.PACKED_ICE.defaultBlockState(), 3);

                return fishingSpot;
            }
            else
                return null;
        }
    }
    //needed for findwater method
    @Overwrite(remap = false)
    private double getDistanceToFisherStartPos(BlockPos pos){
        return fisherman.getStartPos().distToCenterSqr(pos.getX(), fisherman.getStartPos().getY(), pos.getZ());//Horizontal distance
    }

    //Changed for modded water fishing
    @Overwrite(remap = false)
    private boolean isValidFishingSpot(BlockPos pos1, boolean coastFishing){
        int range = coastFishing ? 2 : 4;

        for(int i = -range; i <= range; i++){
            for(int k = -range; k <= range; k++) {
                BlockPos pos = pos1.offset(i, 0, k);
                BlockState state = this.fisherman.getCommandSenderWorld().getBlockState(pos);

                if (state.getFluidState().is(TFCTags.Fluids.WATER_LIKE)){
                    return true;
                }
                //debug
                else{
                    if (fisherman.getOwner() != null) fisherman.tellPlayer(fisherman.getOwner(), Translatable.TEXT_FISHER_NO_WATER);
                }
            }
        }
        return false;
    }

    //Fisherman nerf
    @Overwrite(remap = false)
    public void spawnFishingLoot() {
        int depth;
        if (fishingPos != null) {
            depth = 1 + ((this.getWaterDepth(fishingPos) + fishingRange) / 10);
        }
        else
            depth = 1;
        double time = EnchantmentHelper.getFishingSpeedBonus(this.fisherman.getItemInHand(InteractionHand.MAIN_HAND));
        this.fishingTimer = (int) (1500 - 100*time + fisherman.getRandom().nextInt(1000) / depth);
        double luck = 0.1D;
        double luckFromTool = EnchantmentHelper.getFishingLuckBonus(this.fisherman.getItemInHand(InteractionHand.MAIN_HAND));

        LootParams lootparams = (new LootParams.Builder((ServerLevel)this.fisherman.getCommandSenderWorld()))
                .withParameter(LootContextParams.ORIGIN, this.fisherman.position())
                .withParameter(LootContextParams.TOOL, fisherman.getMainHandItem())
                .withParameter(LootContextParams.KILLER_ENTITY, this.fisherman)
                .withLuck((float)(luck + luckFromTool))
                .create(LootContextParamSets.FISHING);
        LootTable loottable = this.fisherman.getCommandSenderWorld().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
        List<ItemStack> list = loottable.getRandomItems(lootparams);

        MinecraftServer server = fisherman.getServer();
        if (server == null) return;


        for (ItemStack itemstack : list) {
            fisherman.getInventory().addItem(itemstack);
        }
    }

    //Changed for modded water fishing
    @Overwrite(remap = false)
    private int getWaterDepth(BlockPos pos){
        int depth = 0;
        for(int i = 0; i < 10; i++){
            BlockState state = fisherman.getCommandSenderWorld().getBlockState(pos.below(i));
            if(state.getFluidState().is(TFCTags.Fluids.WATER_LIKE)){
                depth++;
            }
            else break;
        }
        return depth;
    }

    //Changed for modded water fishing
    @Overwrite(remap = false)
    private BlockPos getCoastPos() {
        List<BlockPos> list = new ArrayList<>();
        for(int i = -10; i <= 10; i++){
            for(int k = -10; k <= 10; k++) {
                BlockPos pos = fisherman.getStartPos().offset(i, 0, k);
                BlockState state = this.fisherman.getCommandSenderWorld().getBlockState(pos);
                BlockState targetBlockN = this.fisherman.getCommandSenderWorld().getBlockState(pos.north());
                BlockState targetBlockE = this.fisherman.getCommandSenderWorld().getBlockState(pos.east());
                BlockState targetBlockS = this.fisherman.getCommandSenderWorld().getBlockState(pos.south());
                BlockState targetBlockW = this.fisherman.getCommandSenderWorld().getBlockState(pos.west());

                if ((state.getFluidState().is(TFCTags.Fluids.WATER_LIKE)) && targetBlockN.getFluidState().is(TFCTags.Fluids.WATER_LIKE) || targetBlockE.getFluidState().is(TFCTags.Fluids.WATER_LIKE) || targetBlockS.getFluidState().is(TFCTags.Fluids.WATER_LIKE) || targetBlockW.getFluidState().is(TFCTags.Fluids.WATER_LIKE)){
                    list.add(pos);
                }
            }
        }

        if(list.isEmpty()){
            if (fisherman.getOwner() != null) fisherman.tellPlayer(fisherman.getOwner(), Translatable.TEXT_FISHER_NO_WATER);

            //this.fisherman.setIsWorking(false, true);
            this.fisherman.clearStartPos();
            this.fisherman.stopRiding();
            this.stop();
            return null;
        }
        else {
            list.sort(Comparator.comparing(blockPos -> blockPos.distSqr(fisherman.getStartPos())));
            return list.get(0);
        }
    }

    //Changed for modded water fishing
    @Overwrite(remap = false)
    private int getDistanceWithWater(BlockPos pos, Direction direction){
        int distance = 0;
        for(int i = 0; i < fishingRange; i++){
            BlockState targetBlockN = this.fisherman.getCommandSenderWorld().getBlockState(pos.relative(direction, i));
            if (targetBlockN.getFluidState().is(TFCTags.Fluids.WATER_LIKE)){
                distance++;
            }
            else break;
        }
        return distance;
    }

    //Changed for modded water fishing
    @Nullable
    @Overwrite(remap = false)
    private BlockPos getWaterField() {
        List<BlockPos> list = new ArrayList<>();

        int distanceNorth = Math.min(fishingRange, getDistanceWithWater(coastPos, Direction.NORTH));
        int distanceEast = Math.min(fishingRange, getDistanceWithWater(coastPos, Direction.EAST));
        int distanceSouth = Math.min(fishingRange,getDistanceWithWater(coastPos, Direction.SOUTH));
        int distanceWest = Math.min(fishingRange, getDistanceWithWater(coastPos, Direction.WEST));

        boolean isNorth = distanceNorth > distanceSouth;
        boolean isEast = distanceEast > distanceWest;

        int maxX = Math.max(distanceNorth, distanceSouth);
        int maxZ = Math.max(distanceEast, distanceWest);

        for(int x = 0; x < maxX; x++){
            for(int z = 0; z < maxZ; z++) {
                int x1 = isNorth || !isEast ? x * -1 : x;
                int z1 = isNorth || isEast ? z * -1 : z;

                BlockPos pos = this.coastPos.offset(x1, 0, z1);

                BlockState targetBlock = this.fisherman.level().getBlockState(pos);

                if (targetBlock.getFluidState().is(TFCTags.Fluids.WATER_LIKE)) {
                    list.add(pos);
                }
            }
        }

        list.sort(Comparator.comparing(blockPos -> blockPos.distSqr(fisherman.getStartPos())));

        BlockPos pos = null;
        if(!list.isEmpty()){
            int rdm = fisherman.getRandom().nextInt(list.size()/2);
            int index = list.size()/2 + rdm;
            if(index >= list.size()) index = list.size() - 1;

            pos = list.get(index);

            //fisherman.level.setBlock(new BlockPos(pos.getX(), pos.getY() + 3, pos.getZ()), Blocks.ICE.defaultBlockState(), 3);
        }
        return pos;
    }
}
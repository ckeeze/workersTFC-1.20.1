package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.LumberjackEntity;
import com.talhanation.workers.entities.ai.LumberjackAI;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.EnumSet;

import static com.talhanation.workers.entities.LumberjackEntity.State.*;

@Mixin(LumberjackAI.class)
public abstract class LumberjackAIMixin extends Goal {
    private final LumberjackEntity lumber;
    private BlockPos workPos;
    private LumberjackEntity.State state;

    public LumberjackAIMixin(LumberjackEntity lumber) {
        this.lumber = lumber;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        // Start AI if there are trees near the work place.
        return lumber.getStatus() == AbstractWorkerEntity.Status.WORK && lumber.getStartPos() != null;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    //@Shadow
    //public void start() {}

    public void tick() {
        this.breakLeaves();
        //Main.LOGGER.info("LumberState: " +state);
        // Go back to assigned work position.
        switch (state){
            case IDLE -> {
                if(lumber.getStartPos() != null){
                    setWorkState(CALC_WORK_POS);
                }
            }

            case CALC_WORK_POS ->  {
                if(lumber.getVehicle() != null) lumber.stopRiding();

                workPos = lumber.getStartPos();
                if (workPos != null){
                    setWorkState(WORKING);
                }
                else setWorkState(STOP);

            }

            case WORKING -> {
                if(lumber.getVehicle() != null) lumber.stopRiding();

                if(isInSapling()) {
                    lumber.walkTowards(lumber.getStartPos(), 1F);
                }

                if (workPos != null){
                    //Move to minePos -> normal movement
                    if(!workPos.closerThan(lumber.getOnPos(), 12)){
                        this.lumber.walkTowards(workPos, 1F);
                    }
                    //Near Mine Pos -> presice movement
                    if (!workPos.closerThan(lumber.getOnPos(), 3.5F)) {
                        this.lumber.getMoveControl().setWantedPosition(workPos.getX(), lumber.getStartPos().getY(), workPos.getZ(), 1);
                    }
                    else
                        lumber.getNavigation().stop();
                }
                else setWorkState(CALC_WORK_POS);
                // If near wood position, start chopping.
                BlockPos chopPos = getWoodPos();
                if (chopPos == null) return;

                BlockPos lumberPos = lumber.blockPosition();
                //this.lumber.walkTowards(chopPos, 1);
                this.lumber.getMoveControl().setWantedPosition(chopPos.getX(), chopPos.getY(), chopPos.getZ(), 1);
                boolean standingBelowChopPos = (
                        lumberPos.getX() == chopPos.getX() &&
                                lumberPos.getZ() == chopPos.getZ() &&
                                lumberPos.getY() < chopPos.getY());

                if (chopPos.closerThan(lumber.blockPosition(), 9) || standingBelowChopPos) {
                    if (this.mineBlock(chopPos))
                        this.lumber.increaseFarmedItems();

                    if(lumber.getReplantSaplings() && lumber.getCommandSenderWorld().getBlockState(chopPos.below()).is(TFCTags.Blocks.GRASS_PLANTABLE_ON) && this.lumber.getCommandSenderWorld().isEmptyBlock(chopPos)) {
                        plantSaplingFromInv(chopPos);
                    }
                }
            }

            case STOP -> {
                lumber.stopRiding();
                if(lumber.needsToSleep()){
                    setWorkState(SLEEP);
                }

                else if(lumber.needsToDeposit()){
                    setWorkState(DEPOSIT);
                }

                else if(lumber.needsToGetFood()){
                    setWorkState(UPKEEP);
                }
                else{
                    this.lumber.walkTowards(workPos, 1);

                    double distance = lumber.distanceToSqr(lumber.getX(), lumber.getY(), lumber.getZ());
                    if(distance < 5.5F) { //valid value example: distance = 3.2
                        stop();
                    }
                }
            }

            case DEPOSIT -> {
                //Separate AI doing stuff
                lumber.stopRiding();
                if(!lumber.needsToDeposit()){
                    setWorkState(IDLE);
                }
            }

            case UPKEEP -> {
                //Separate AI doing stuff
                lumber.stopRiding();
                if(!lumber.needsToGetFood()){
                    setWorkState(IDLE);
                }
            }

            case SLEEP -> {
                //Separate AI doing stuff
                lumber.stopRiding();
                if(!lumber.needsToSleep()){
                    setWorkState(IDLE);
                }
            }
        }
    }

    public boolean isInSapling() {
        for(int i = -1; i <= 1; i++){
            BlockPos blockPos = lumber.getOnPos();

            if(this.lumber.level().getBlockState(blockPos.above()).is(BlockTags.SAPLINGS)){
                return true;
            }
        }

        return false;
    }

    //@Shadow
    //public void stop() {}

    @Shadow
    private void setWorkState(LumberjackEntity.@NotNull State state) {}

    @Shadow
    private void breakLeaves() {}

    @Shadow
    private BlockPos getWoodPos() {
        return null;
    }

    private void plantSaplingFromInv(BlockPos blockPos) {
        SimpleContainer inventory = lumber.getInventory();

        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if (!itemstack.isEmpty() && itemstack.is(ItemTags.SAPLINGS)) {
                BlockState placedSaplingBlock = Blocks.DEAD_BUSH.defaultBlockState();

                if (itemstack.is(TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }
                if (itemstack.is(TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.SAPLING).get().asItem())){
                    placedSaplingBlock = TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.SAPLING).get().defaultBlockState();
                }

                this.lumber.level().setBlock(blockPos, placedSaplingBlock, 3);
                lumber.level().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                itemstack.shrink(1);
                if (itemstack.isEmpty()) {
                    inventory.setItem(i, ItemStack.EMPTY);
                }
                break;
            }
        }
    }

    private boolean mineBlock(BlockPos blockPos){
        if (this.lumber.isAlive() && ForgeEventFactory.getMobGriefingEvent(this.lumber.level(), this.lumber)) {

            BlockState blockstate = this.lumber.level().getBlockState(blockPos);
            Block block = blockstate.getBlock();
            ItemStack heldItem = this.lumber.getItemInHand(InteractionHand.MAIN_HAND);

            if (lumber.wantsToBreak(block)){
                if (lumber.getCurrentTimeBreak() % 5 == 4) {
                    lumber.level().playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockstate.getSoundType().getHitSound(), SoundSource.BLOCKS, 1F, 0.75F, false);
                }

                //set max destroy speed
                int bp = (int) (blockstate.getDestroySpeed(this.lumber.level(), blockPos) * 30);
                this.lumber.setBreakingTime(bp);

                //increase current
                this.lumber.setCurrentTimeBreak(this.lumber.getCurrentTimeBreak() + (int) (1 * (heldItem.getDestroySpeed(blockstate))));
                float f = (float) this.lumber.getCurrentTimeBreak() / (float) this.lumber.getBreakingTime();

                int i = (int) (f * 10);

                if (i != this.lumber.getPreviousTimeBreak()) {
                    this.lumber.level().destroyBlockProgress(1, blockPos, i);
                    this.lumber.setPreviousTimeBreak(i);
                }

                if (this.lumber.getCurrentTimeBreak() >= this.lumber.getBreakingTime()) {
                    // Break the target block
                    this.lumber.level().destroyBlock(blockPos, true, this.lumber);
                    this.lumber.setCurrentTimeBreak(-1);
                    this.lumber.setBreakingTime(0);
                    this.lumber.consumeToolDurability();
                    return true;
                }
                this.lumber.workerSwingArm();
            }
        }
        return false;
    }
}
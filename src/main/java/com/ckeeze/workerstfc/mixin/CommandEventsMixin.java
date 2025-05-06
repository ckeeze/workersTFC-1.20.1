package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.CommandEvents;
import com.talhanation.workers.entities.*;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;

import static com.talhanation.workers.Translatable.TEXT_CHEST;
import static com.talhanation.workers.Translatable.TEXT_CHEST_ERROR;

@SuppressWarnings({"removal", "unused"})
@Mixin(CommandEvents.class)
public abstract class CommandEventsMixin{

    private static final Set<Block> VALID_CHESTS = ImmutableSet.of(
            Blocks.CHEST, Blocks.BARREL
            ,TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.CHEST).get()
            ,TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.CHEST).get()
            ,TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.CHEST).get()
            ,TFCBlocks.WOODS.get(Wood.MANGROVE).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.CHEST).get()
            ,TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.CHEST).get()
            ,TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.CHEST).get()
            ,TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.CHEST).get(),TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.CHEST).get());

    @Overwrite(remap = false)
    public static void setChestPosWorker(UUID player_uuid, AbstractWorkerEntity worker, BlockPos blockpos) {
        LivingEntity owner = worker.getOwner();
        UUID expectedOwnerUuid = worker.getOwnerUUID();
        if (!worker.isTame() || expectedOwnerUuid == null || owner == null) {
            return;
        }
        if (expectedOwnerUuid.equals(player_uuid)) {
              BlockState selectedBlock = worker.level().getBlockState(blockpos);
            if (VALID_CHESTS.contains(selectedBlock.getBlock())) {
                worker.setChestPos(blockpos);
                worker.tellPlayer(owner, TEXT_CHEST);
                worker.setStatus(AbstractWorkerEntity.Status.DEPOSIT);
            } else {
                worker.tellPlayer(owner, TEXT_CHEST_ERROR);
            }
        }
    }
}

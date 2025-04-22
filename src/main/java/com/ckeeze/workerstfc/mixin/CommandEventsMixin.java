package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.CommandEvents;
import com.talhanation.workers.entities.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;

import static com.talhanation.workers.Translatable.TEXT_CHEST;
import static com.talhanation.workers.Translatable.TEXT_CHEST_ERROR;

@SuppressWarnings("removal")
@Mixin(CommandEvents.class)
public abstract class CommandEventsMixin{

    //Blockfromstring
    private static Block BFS(String S){
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(S));
    }

    private static final Set<Block> VALID_CHESTS = ImmutableSet.of(
            Blocks.CHEST, Blocks.BARREL
            ,BFS("tfc:wood/chest/acacia"),BFS("tfc:wood/chest/ash"),BFS("tfc:wood/chest/aspen"),BFS("tfc:wood/chest/birch")
            ,BFS("tfc:wood/chest/blackwood"),BFS("tfc:wood/chest/chestnut"),BFS("tfc:wood/chest/douglas_fir"),BFS("tfc:wood/chest/hickory")
            ,BFS("tfc:wood/chest/kapok"),BFS("tfc:wood/chest/mangrove"),BFS("tfc:wood/chest/maple"),BFS("tfc:wood/chest/oak")
            ,BFS("tfc:wood/chest/palm"),BFS("tfc:wood/chest/pine"),BFS("tfc:wood/chest/rosewood"),BFS("tfc:wood/chest/sequoia")
            ,BFS("tfc:wood/chest/spruce"),BFS("tfc:wood/chest/sycamore"),BFS("tfc:wood/chest/white_cedar"),BFS("tfc:wood/chest/willow"));

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

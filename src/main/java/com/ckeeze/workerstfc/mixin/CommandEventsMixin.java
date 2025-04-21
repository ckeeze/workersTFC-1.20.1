package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.CommandEvents;
import com.talhanation.workers.config.WorkersModConfig;
import com.talhanation.workers.entities.*;
import com.talhanation.workers.inventory.CommandMenu;
import com.talhanation.workers.network.MessageOpenCommandScreen;
import com.talhanation.workers.network.MessageToClientUpdateCommandScreen;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

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

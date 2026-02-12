package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.MinerEntity;
import com.talhanation.workers.entities.ai.MinerAI;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;

import java.util.EnumSet;

@SuppressWarnings("unused")
@Mixin(MinerAI.class)
public abstract class MinerAIMixin extends Goal {
    @Mutable
    @Final
    @Shadow
    private final MinerEntity miner;
    @Shadow
    private BlockPos minePos;

    public MinerAIMixin(MinerEntity miner) {
        this.miner = miner;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * @author Ckeeze
     * @reason Changing torch items to TFC torch
     */
    @Overwrite(remap = false)
    public void placeTorch(){
        if (hasTorchInInv()){
            miner.level().setBlock(miner.getOnPos().above(), TFCBlocks.TORCH.get().defaultBlockState(), 3);
            miner.level().playSound(null, this.minePos.getX(), this.minePos.getY(), this.minePos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            for (int i = 0; i < miner.getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = miner.getInventory().getItem(i);
                if(itemstack.is(TFCItems.TORCH.get())) itemstack.shrink(1);
            }
        }
    }

    /**
     * @author Ckeeze
     * @reason Changing torch items to TFC torch
     */
    @Overwrite(remap = false)
    public boolean hasTorchInInv() {
        return miner.getInventory().hasAnyOf(ImmutableSet.of(TFCItems.TORCH.get()));
    }
}
package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.MinerEntity;
import com.talhanation.workers.entities.ai.MinerAI;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;

@Mixin(MinerAI.class)
public abstract class MinerAIMixin extends Goal {
    private final MinerEntity miner;
    private BlockPos minePos;

    public MinerAIMixin(MinerEntity miner) {
        this.miner = miner;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    //itemfromstring
    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }
    //Blockfromstring
    private static Block BFS(String S){
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(S));
    }

    public void placeTorch(){
        if (hasTorchInInv()){
            miner.level().setBlock(miner.getOnPos().above(), BFS("tfc:torch").defaultBlockState(), 3);
            miner.level().playSound(null, this.minePos.getX(), this.minePos.getY(), this.minePos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            for (int i = 0; i < miner.getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = miner.getInventory().getItem(i);
                if(itemstack.is(IFS("tfc:torch"))) itemstack.shrink(1);
            }
        }
    }

    public boolean hasTorchInInv() {
        return miner.getInventory().hasAnyOf(ImmutableSet.of(IFS("tfc:torch")));
    }
}

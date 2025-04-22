package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ai.WorkerFindWaterAI;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;
import java.util.Random;

@Mixin(WorkerFindWaterAI.class)
public abstract class WorkerFindWaterAIMixin extends Goal{
    private final AbstractWorkerEntity worker;

    public WorkerFindWaterAIMixin(AbstractWorkerEntity worker) {
        this.worker = worker;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public BlockPos findBlockWater() {
        BlockPos blockpos = null;
        Random random = new Random();
        int range = 14;
        for(int i = 0; i < 15; i++){
            BlockPos blockpos1 = this.worker.getWorkerOnPos().offset(random.nextInt(range) - range/2, 3, random.nextInt(range) - range/2);
            while(this.worker.level().isEmptyBlock(blockpos1) && blockpos1.getY() > 1){
                blockpos1 = blockpos1.below();
            }
            if(this.worker.getCommandSenderWorld().getFluidState(blockpos1).is(TFCTags.Fluids.WATER_LIKE)){
                blockpos = blockpos1;
            }
        }
        return blockpos;
    }
}
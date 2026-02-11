package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractWorkerEntity;

import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@SuppressWarnings("unused")
@Mixin(AbstractWorkerEntity.class)
public abstract class AbstractWorkerEntityMixin {

    @Shadow
    public BlockPos getBedPos() {
        return null;
    }
    @Shadow
    public AbstractWorkerEntity.Status getStatus() {
        return null;
    };
    @Shadow
    public int getHunger(){return 0;};
    @Shadow
    public void setHunger(int hunger){}

    /**
     * @author Ckeeze
     * @reason Modded farmed items only stack till 32
     */
    @Overwrite(remap = false)
    protected int getFarmedItemsDepositAmount() {
        return 32;
    }

    @Overwrite(remap =false)
    public void updateHunger() {
        AbstractWorkerEntity.Status status = this.getStatus();
        int hunger = this.getHunger();
        if (status != AbstractWorkerEntity.Status.WORK && this.getBedPos() != null) {
            hunger -= 1;
        } else {
            hunger -= 2;
        }

        if (hunger < 0) {
            hunger = 0;
        }

        this.setHunger(hunger);
    }
}
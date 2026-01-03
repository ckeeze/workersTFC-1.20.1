package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractWorkerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@SuppressWarnings("unused")
@Mixin(AbstractWorkerEntity.class)
public abstract class AbstractWorkerEntityMixin {

    /**
     * @author Ckeeze
     * @reason Modded farmed items only stack till 32
     */
    @Overwrite(remap = false)
    protected int getFarmedItemsDepositAmount() {
        return 32;
    }
}
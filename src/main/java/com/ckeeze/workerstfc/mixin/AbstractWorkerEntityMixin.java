package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.AbstractWorkerEntity;

import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("unused")
@Mixin(AbstractWorkerEntity.class)
public abstract class AbstractWorkerEntityMixin {

    protected int getFarmedItemsDepositAmount() {
        return 32;
    }
}
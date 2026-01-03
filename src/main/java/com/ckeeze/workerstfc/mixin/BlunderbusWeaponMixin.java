package com.ckeeze.workerstfc.mixin;

import com.talhanation.recruits.compat.BlunderbussWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlunderbussWeapon.class)
public class BlunderbusWeaponMixin {

    /**
     * @author Ckeeze
     * @reason Nerf attack speed
     */
    @Overwrite(remap = false)
    public int getAttackCooldown() {
        return 80;
    }

    /**
     * @author Ckeeze
     * @reason Nerf attack speed
     */
    @Overwrite(remap = false)
    public int getWeaponLoadTime() {
        return 120;
    }
}

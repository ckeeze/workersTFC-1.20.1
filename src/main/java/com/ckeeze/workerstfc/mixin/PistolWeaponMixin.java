package com.ckeeze.workerstfc.mixin;

import com.talhanation.recruits.compat.PistolWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PistolWeapon.class)
public class PistolWeaponMixin {

    /**
     * @author Ckeeze
     * @reason Nerf attack speed
     */
    @Overwrite(remap = false)
    public int getAttackCooldown() {
        return 50;
    }

    /**
     * @author Ckeeze
     * @reason Nerf attack speed
     */
    @Overwrite(remap = false)
    public int getWeaponLoadTime() {
        return 90;
    }
}

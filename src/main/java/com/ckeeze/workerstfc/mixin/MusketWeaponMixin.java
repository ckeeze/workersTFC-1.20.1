package com.ckeeze.workerstfc.mixin;

import com.talhanation.recruits.compat.MusketWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MusketWeapon.class)
public class MusketWeaponMixin {

    /**
     * @author Ckeeze
     * @reason Nerf attack speed
     */
    @Overwrite(remap = false)
    public int getAttackCooldown() {
        return 70;
    }

    /**
     * @author Ckeeze
     * @reason Nerf attack speed
     */
    @Overwrite(remap = false)
    public int getWeaponLoadTime() {
        return 110;
    }
}

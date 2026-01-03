package com.ckeeze.workerstfc.mixin;

import com.talhanation.recruits.compat.MusketBayonetWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MusketBayonetWeapon.class)
public class MusketBayonetWeaponMixin {

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

package com.ckeeze.workerstfc.entity.custom;

import com.ckeeze.workerstfc.entity.ModEntities;
import com.ckeeze.workerstfc.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class WeaknessProjectileEntity extends ThrowableItemProjectile {

    public WeaknessProjectileEntity(EntityType<? extends ThrowableItemProjectile> pentityType, Level plevel) {
        super(pentityType, plevel);
    }

    public WeaknessProjectileEntity(Level plevel) {
        super(ModEntities.WEAKNESS_PROJECTILY.get(), plevel);
    }

    public WeaknessProjectileEntity(Level plevel, LivingEntity livingEntity) {
        super(ModEntities.WEAKNESS_PROJECTILY.get(), livingEntity, plevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.WEAKNESSBOMB.get();
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult){
        if(!this.level().isClientSide()){
            this.level().broadcastEntityEvent( this, ((byte) 3));
            this.makeAreaOfEffectWeaknessCloud(Potions.WEAKNESS);
        }
        this.playSound(SoundEvents.SPLASH_POTION_BREAK,0.6F, 0.4F);
        this.discard();
        super.onHitBlock(pResult);
    }

    private void makeAreaOfEffectWeaknessCloud(Potion p_37539_) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            areaeffectcloud.setOwner((LivingEntity)entity);
        }

        areaeffectcloud.setRadius(2.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(14);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.setPotion(p_37539_);

        this.level().addFreshEntity(areaeffectcloud);
    }
}

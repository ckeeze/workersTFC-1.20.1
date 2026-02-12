package com.ckeeze.workerstfc.entity;

import com.ckeeze.workerstfc.Workerstfc;
import com.ckeeze.workerstfc.entity.custom.WeaknessProjectileEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>>ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Workerstfc.MODID);

    public static final RegistryObject<EntityType<WeaknessProjectileEntity>> WEAKNESS_PROJECTILY =
            ENTITY_TYPES.register("weakness_projectile", () -> EntityType.Builder.<WeaknessProjectileEntity>of(WeaknessProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build("weakness_projectile"));

    public static void register(IEventBus eventBus) { ENTITY_TYPES.register(eventBus); }
}

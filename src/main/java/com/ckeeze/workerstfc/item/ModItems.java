package com.ckeeze.workerstfc.item;

import com.ckeeze.workerstfc.Workerstfc;
import com.ckeeze.workerstfc.item.custom.WeaknessBomb;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Workerstfc.MODID);

    public static final RegistryObject<Item> COIN = ITEMS.register("coin",
            () ->  new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLAYROUNDJAR = ITEMS.register("clayroundjar",
            () ->  new Item(new Item.Properties()));
    public static final RegistryObject<Item> CERAMICROUNDJAR = ITEMS.register("ceramicroundjar",
            () ->  new Item(new Item.Properties()));
    public static final RegistryObject<Item> GILDEDFRUIT = ITEMS.register("gildedfruit",
            () ->  new Item(new Item.Properties()));

    public static final RegistryObject<Item> WEAKNESSBOMB = ITEMS.register("weaknessbomb",
            () ->  new WeaknessBomb(new Item.Properties()));



    public static void refister(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}

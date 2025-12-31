package com.ckeeze.workerstfc.item;

import com.ckeeze.workerstfc.Workerstfc;
import com.ckeeze.workerstfc.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Workerstfc.MODID);

    public static final RegistryObject<CreativeModeTab> WORKERSTFC_ITEMS_TAB = CREATIVE_MODE_TABS.register("workerstfc_items_tab",
            ()-> CreativeModeTab.builder()
                    .icon(()-> new ItemStack(ModItems.COIN.get()))
                    .title(Component.translatable("creativetab.workerstfc.items"))
                    .displayItems((itemDisplayParameters, output)->{
                        output.accept(ModItems.COIN.get());
                        output.accept(ModItems.CLAYROUNDJAR.get());
                        output.accept(ModItems.CERAMICROUNDJAR.get());
                        output.accept(ModItems.WEAKNESSBOMB.get());
                        output.accept(ModItems.GILDEDFRUIT.get());

                        output.accept(ModBlocks.KEG.get());
                        output.accept(ModBlocks.POTTERY_WHEEL.get());
                        output.accept(ModBlocks.SPICE_TABLE.get());
                        output.accept(ModBlocks.DYE_TABLE.get());
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

package com.ckeeze.workerstfc.block;

import com.ckeeze.workerstfc.Workerstfc;
import com.ckeeze.workerstfc.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Workerstfc.MODID);

    public static final RegistryObject<Block> POTTERY_WHEEL = registerBlock("pottery_wheel",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.CARTOGRAPHY_TABLE).noOcclusion()));
    public static final RegistryObject<Block> KEG = registerBlock("keg",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.CARTOGRAPHY_TABLE)));
    public static final RegistryObject<Block> SPICE_TABLE = registerBlock("spice_table",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.CARTOGRAPHY_TABLE)));
    public static final RegistryObject<Block> DYE_TABLE = registerBlock("dye_table",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.CARTOGRAPHY_TABLE).noOcclusion()));


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> ToReturn = BLOCKS.register(name, block);
        registerBlockItem(name, ToReturn);
        return ToReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}

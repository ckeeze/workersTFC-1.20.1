package com.ckeeze.workerstfc.villager;

import com.ckeeze.workerstfc.Workerstfc;
import com.ckeeze.workerstfc.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
        DeferredRegister.create(ForgeRegistries.POI_TYPES, Workerstfc.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Workerstfc.MODID);

    public static final RegistryObject<PoiType> POTTER_POI = POI_TYPES.register("potter_poi",
            ()-> new PoiType(ImmutableSet.copyOf(ModBlocks.POTTERY_WHEEL.get().getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> HUNTER_POI = POI_TYPES.register("hunter_poi",
            ()-> new PoiType(ImmutableSet.copyOf(TFCBlocks.FIREPIT.get().getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> TFCFARMER_POI = POI_TYPES.register("tfcfarmer_poi",
            ()-> new PoiType(ImmutableSet.copyOf(TFCBlocks.COMPOSTER.get().getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> WEAVER_POI = POI_TYPES.register("weaver_poi",
            ()-> new PoiType(ImmutableSet.copyOf(Blocks.LOOM.getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> JEWELER_POI = POI_TYPES.register("jeweler_poi",
            ()-> new PoiType(ImmutableSet.copyOf(Blocks.SMITHING_TABLE.getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> TAVERNKEEP_POI = POI_TYPES.register("tavernkeep_poi",
            ()-> new PoiType(ImmutableSet.copyOf(ModBlocks.KEG.get().getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> TFCMASON_POI = POI_TYPES.register("tfcmason_poi",
            ()-> new PoiType(ImmutableSet.copyOf(TFCBlocks.PLAIN_ALABASTER.get().getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> DRUID_POI = POI_TYPES.register("druid_poi",
            ()-> new PoiType(ImmutableSet.copyOf(Blocks.CAULDRON.getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> DYETRADER_POI = POI_TYPES.register("dyetrader_poi",
            ()-> new PoiType(ImmutableSet.copyOf(ModBlocks.DYE_TABLE.get().getStateDefinition().getPossibleStates()), 1 , 1));
    public static final RegistryObject<PoiType> SPICETRADER_POI = POI_TYPES.register("spicetrader_poi",
            ()-> new PoiType(ImmutableSet.copyOf(ModBlocks.SPICE_TABLE.get().getStateDefinition().getPossibleStates()), 1 , 1));

    public static final RegistryObject<VillagerProfession> POTTER =
            VILLAGER_PROFESSIONS.register("potter", ()-> new VillagerProfession("potter",
                    holder -> holder.get() == POTTER_POI.get(), holder -> holder.get() == POTTER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_MASON));
    public static final RegistryObject<VillagerProfession> HUNTER =
            VILLAGER_PROFESSIONS.register("hunter", ()-> new VillagerProfession("hunter",
                    holder -> holder.get() == HUNTER_POI.get(), holder -> holder.get() == HUNTER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_FLETCHER));
    public static final RegistryObject<VillagerProfession> TFCFARMER =
            VILLAGER_PROFESSIONS.register("tfcfarmer", ()-> new VillagerProfession("tfcfarmer",
                    holder -> holder.get() == TFCFARMER_POI.get(), holder -> holder.get() == TFCFARMER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_FARMER));
    public static final RegistryObject<VillagerProfession> WEAVER =
            VILLAGER_PROFESSIONS.register("weaver", ()-> new VillagerProfession("weaver",
                    holder -> holder.get() == WEAVER_POI.get(), holder -> holder.get() == WEAVER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_SHEPHERD));
    public static final RegistryObject<VillagerProfession> JEWELER =
            VILLAGER_PROFESSIONS.register("jeweler", ()-> new VillagerProfession("jeweler",
                    holder -> holder.get() == JEWELER_POI.get(), holder -> holder.get() == JEWELER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_TOOLSMITH));
    public static final RegistryObject<VillagerProfession> TAVERNKEEP =
            VILLAGER_PROFESSIONS.register("tavernkeep", ()-> new VillagerProfession("tavernkeep",
                    holder -> holder.get() == TAVERNKEEP_POI.get(), holder -> holder.get() == TAVERNKEEP_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));
    public static final RegistryObject<VillagerProfession> TFCMASON =
            VILLAGER_PROFESSIONS.register("tfcmason", ()-> new VillagerProfession("tfcmason",
                    holder -> holder.get() == TFCMASON_POI.get(), holder -> holder.get() == TFCMASON_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_MASON));
    public static final RegistryObject<VillagerProfession> DRUID =
            VILLAGER_PROFESSIONS.register("druid", ()-> new VillagerProfession("druid",
                    holder -> holder.get() == DRUID_POI.get(), holder -> holder.get() == DRUID_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LEATHERWORKER));
    public static final RegistryObject<VillagerProfession> DYETRADER =
            VILLAGER_PROFESSIONS.register("dyetrader", ()-> new VillagerProfession("dyetrader",
                    holder -> holder.get() == DYETRADER_POI.get(), holder -> holder.get() == DYETRADER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN));
    public static final RegistryObject<VillagerProfession> SPICETRADER =
            VILLAGER_PROFESSIONS.register("spicetrader", ()-> new VillagerProfession("spicetrader",
                    holder -> holder.get() == SPICETRADER_POI.get(), holder -> holder.get() == SPICETRADER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CARTOGRAPHER));

    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }

}

package com.ckeeze.workerstfc.mixin;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(PoiTypes.class)
public class PoiTypesMixin {
    @Shadow
    private static final ResourceKey<PoiType> ARMORER = createKey("armorer");
    @Shadow
    private static final ResourceKey<PoiType> BUTCHER = createKey("butcher");
    @Shadow
    private static final ResourceKey<PoiType> FARMER = createKey("farmer");
    @Shadow
    private static final ResourceKey<PoiType> FISHERMAN = createKey("fisherman");
    @Shadow
    private static final ResourceKey<PoiType> FLETCHER = createKey("fletcher");
    @Shadow
    private static final ResourceKey<PoiType> LIBRARIAN = createKey("librarian");
    @Shadow
    private static final ResourceKey<PoiType> MASON = createKey("mason");
    @Shadow
    private static final ResourceKey<PoiType> WEAPONSMITH = createKey("weaponsmith");
    @Shadow
    private static final ResourceKey<PoiType> HOME = createKey("home");
    @Shadow
    private static final ResourceKey<PoiType> MEETING = createKey("meeting");
    @Shadow
    private static final ResourceKey<PoiType> BEEHIVE = createKey("beehive");
    @Shadow
    private static final ResourceKey<PoiType> BEE_NEST = createKey("bee_nest");
    @Shadow
    private static final ResourceKey<PoiType> NETHER_PORTAL = createKey("nether_portal");
    @Shadow
    private static final ResourceKey<PoiType> LODESTONE = createKey("lodestone");
    @Shadow
    private static final ResourceKey<PoiType> LIGHTNING_ROD = createKey("lightning_rod");

    @Shadow
    private static final ResourceKey<PoiType> CARTOGRAPHER = createKey("cartographer");
    @Shadow
    private static final ResourceKey<PoiType> CLERIC = createKey("cleric");
    @Shadow
    private static final ResourceKey<PoiType> LEATHERWORKER = createKey("leatherworker");
    @Shadow
    private static final ResourceKey<PoiType> SHEPHERD = createKey("shepherd");
    @Shadow
    private static final ResourceKey<PoiType> TOOLSMITH = createKey("toolsmith");

    @Overwrite(remap = true)
    public static PoiType bootstrap(Registry<PoiType> pRegistry) {
        register(pRegistry, ARMORER, getBlockStates(Blocks.BLAST_FURNACE), 1, 1);
        register(pRegistry, BUTCHER, getBlockStates(Blocks.SMOKER), 1, 1);
        register(pRegistry, CARTOGRAPHER, getBlockStates(Blocks.DEEPSLATE_LAPIS_ORE), 1, 1); //make unobtainable in tfc
        register(pRegistry, CLERIC, getBlockStates(Blocks.DEEPSLATE_COAL_ORE), 1, 1); //make unobtainable in tfc
        register(pRegistry, FARMER, getBlockStates(Blocks.COMPOSTER), 1, 1);
        register(pRegistry, FISHERMAN, getBlockStates(Blocks.BARREL), 1, 1);
        register(pRegistry, FLETCHER, getBlockStates(Blocks.FLETCHING_TABLE), 1, 1);
        register(pRegistry, LEATHERWORKER, getBlockStates(Blocks.DEEPSLATE_DIAMOND_ORE), 1, 1); //make unobtainable in tfc
        register(pRegistry, LIBRARIAN, getBlockStates(Blocks.GOLD_ORE), 1, 1); //make unobtainable in tfc
        register(pRegistry, MASON, getBlockStates(Blocks.STONECUTTER), 1, 1);
        register(pRegistry, SHEPHERD, getBlockStates(Blocks.DEEPSLATE_GOLD_ORE), 1, 1); //make unobtainable in tfc
        register(pRegistry, TOOLSMITH, getBlockStates(Blocks.DEEPSLATE_COPPER_ORE), 1, 1); //make unobtainable in tfc
        register(pRegistry, WEAPONSMITH, getBlockStates(Blocks.GRINDSTONE), 1, 1);
        register(pRegistry, HOME, BEDS, 1, 1);
        register(pRegistry, MEETING, getBlockStates(Blocks.BELL), 32, 6);
        register(pRegistry, BEEHIVE, getBlockStates(Blocks.BEEHIVE), 0, 1);
        register(pRegistry, BEE_NEST, getBlockStates(Blocks.BEE_NEST), 0, 1);
        register(pRegistry, NETHER_PORTAL, getBlockStates(Blocks.NETHER_PORTAL), 0, 1);
        register(pRegistry, LODESTONE, getBlockStates(Blocks.LODESTONE), 0, 1);
        return register(pRegistry, LIGHTNING_ROD, getBlockStates(Blocks.LIGHTNING_ROD), 0, 1);
    }

    @Shadow
    private static final Set<BlockState> BEDS = ImmutableList.of(Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED).stream().flatMap((p_218097_) -> {
        return p_218097_.getStateDefinition().getPossibleStates().stream();
    }).filter((p_218095_) -> {
        return p_218095_.getValue(BedBlock.PART) == BedPart.HEAD;
    }).collect(ImmutableSet.toImmutableSet());

    @Shadow
    private static Set<BlockState> getBlockStates(Block pBlock) {
        return ImmutableSet.copyOf(pBlock.getStateDefinition().getPossibleStates());
    }

    @Shadow
    private static PoiType register(Registry<PoiType> pKey, ResourceKey<PoiType> pValue, Set<BlockState> pMatchingStates, int pMaxTickets, int pValidRange) {
        PoiType poitype = new PoiType(pMatchingStates, pMaxTickets, pValidRange);
        return poitype;
    }

    @Shadow
    private static ResourceKey<PoiType> createKey(String pName) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, new ResourceLocation(pName));
    }
}

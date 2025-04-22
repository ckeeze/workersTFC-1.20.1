package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.MinerEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings("removal")
@Mixin(MinerEntity.class)
public abstract class MinerEntityMixin extends AbstractInventoryEntity {

    public MinerEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    //itemfromstring
    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }
    //Blockfromstring
    private static Block BFS(String S){
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(S));
    }

    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(Items.COAL, Items.COPPER_ORE, Items.IRON_ORE,
            Items.GOLD_ORE, Items.DIAMOND, Items.EMERALD, Items.STONE, Items.COBBLESTONE, Items.ANDESITE, Items.GRANITE,
            Items.GRAVEL, Items.SAND, Items.SANDSTONE, Items.RED_SAND, Items.REDSTONE, Items.DIRT, Items.DIORITE,
            Items.COARSE_DIRT, Items.RAW_COPPER, Items.RAW_IRON, Items.RAW_GOLD, Items.TUFF, Items.BLACKSTONE,
            Items.DEEPSLATE, Items.DEEPSLATE_BRICKS, Items.BASALT, Items.TORCH, Items.FLINT,
            //ORES
            IFS("tfc:ore/normal_bismuthinite"), IFS("tfc:ore/poor_bismuthinite"),IFS("tfc:ore/rich_bismuthinite"),IFS("tfc:ore/small_bismuthinite")
            ,IFS("tfc:ore/normal_cassiterite"),IFS("tfc:ore/poor_cassiterite"),IFS("tfc:ore/rich_cassiterite"),IFS("tfc:ore/small_cassiterite")
            ,IFS("tfc:ore/normal_garnierite"),IFS("tfc:ore/poor_garnierite"),IFS("tfc:ore/rich_garnierite"),IFS("tfc:ore/small_garnierite")
            ,IFS("tfc:ore/normal_hematite"),IFS("tfc:ore/poor_hematite"),IFS("tfc:ore/rich_hematite"),IFS("tfc:ore/small_hematite")
            ,IFS("tfc:ore/normal_limonite"),IFS("tfc:ore/poor_limonite"),IFS("tfc:ore/rich_limonite"),IFS("tfc:ore/small_limonite")
            ,IFS("tfc:ore/normal_magnetite"),IFS("tfc:ore/poor_magnetite"),IFS("tfc:ore/rich_magnetite"),IFS("tfc:ore/small_magnetite")
            ,IFS("tfc:ore/normal_malachite"),IFS("tfc:ore/poor_malachite"),IFS("tfc:ore/rich_malachite"),IFS("tfc:ore/small_malachite")
            ,IFS("tfc:ore/normal_native_copper"),IFS("tfc:ore/poor_native_copper"),IFS("tfc:ore/rich_native_copper"),IFS("tfc:ore/small_native_copper")
            ,IFS("tfc:ore/normal_native_gold"),IFS("tfc:ore/poor_native_gold"),IFS("tfc:ore/rich_native_gold"),IFS("tfc:ore/small_native_gold")
            ,IFS("tfc:ore/normal_native_silver"),IFS("tfc:ore/poor_native_silver"),IFS("tfc:ore/rich_native_silver"),IFS("tfc:ore/small_native_silver")
            ,IFS("tfc:ore/normal_sphalerite"),IFS("tfc:ore/poor_sphalerite"),IFS("tfc:ore/rich_sphalerite"),IFS("tfc:ore/small_sphalerite")
            ,IFS("tfc:ore/normal_tedrahedrite"),IFS("tfc:ore/poor_tedrahedrite"),IFS("tfc:ore/rich_tedrahedrite"),IFS("tfc:ore/small_tedrahedrite")
            //Raw Gem
            ,IFS("tcf:ore/amethyst"),IFS("tcf:ore/diamond"),IFS("tcf:ore/lapis_lazuli"),IFS("tcf:ore/opal")
            ,IFS("tcf:ore/pyrite"),IFS("tcf:ore/ruby"),IFS("tcf:ore/sapphire"),IFS("tcf:ore/topaz")
            //Gravels
            ,IFS("tfc:rock/gravel/andesite"),IFS("tfc:rock/gravel/basalt"),IFS("tfc:rock/gravel/chalk"),IFS("tfc:rock/gravel/chert")
            ,IFS("tfc:rock/gravel/claystone"),IFS("tfc:rock/gravel/conglomerate"),IFS("tfc:rock/gravel/dacite"),IFS("tfc:rock/gravel/diorite")
            ,IFS("tfc:rock/gravel/dolomite"),IFS("tfc:rock/gravel/gabbro"),IFS("tfc:rock/gravel/gneiss"),IFS("tfc:rock/gravel/granite")
            ,IFS("tfc:rock/gravel/limestone"),IFS("tfc:rock/gravel/marble"),IFS("tfc:rock/gravel/phyllite"),IFS("tfc:rock/gravel/quartzite")
            ,IFS("tfc:rock/gravel/rhyolite"),IFS("tfc:rock/gravel/schist"),IFS("tfc:rock/gravel/shale"),IFS("tfc:rock/gravel/slate")
            //Sand
            ,IFS("tfc:sand/black"),IFS("tfc:sand/brown"),IFS("tfc:sand/green"),IFS("tfc:sand/pink"),IFS("tfc:sand/red"),IFS("tfc:sand/white")
            ,IFS("tfc:sand/yellow")
            //Sandstone
            ,IFS("tfc:raw_sandstone/black"),IFS("tfc:raw_sandstone/brown"),IFS("tfc:raw_sandstone/green"),IFS("tfc:raw_sandstone/pink"),IFS("tfc:raw_sandstone/red"),IFS("tfc:raw_sandstone/white")
            ,IFS("tfc:raw_sandstone/yellow")
            //Soil
            ,IFS("tfc:dirt/loam"),IFS("tfc:dirt/sandy_loam"),IFS("tfc:dirt/silt"),IFS("tfc:dirt/silty_loam")
            //Rocks
            ,IFS("tfc:rock/loose/andesite"),IFS("tfc:rock/loose/basalt"),IFS("tfc:rock/loose/chalk"),IFS("tfc:rock/loose/andesite"),IFS("tfc:rock/loose/chert")
            ,IFS("tfc:rock/loose/claystone"),IFS("tfc:rock/loose/conglomerate"),IFS("tfc:rock/loose/dacite"),IFS("tfc:rock/loose/diorite")
            ,IFS("tfc:rock/loose/dolomite"),IFS("tfc:rock/loose/gabbro"),IFS("tfc:rock/loose/gneiss"),IFS("tfc:rock/loose/granite"),IFS("tfc:rock/loose/limestone")
            ,IFS("tfc:rock/loose/marble"),IFS("tfc:rock/loose/phyllite"),IFS("tfc:rock/loose/quartzite"),IFS("tfc:rock/loose/rhyolite")
            ,IFS("tfc:rock/loose/schist"),IFS("tfc:rock/loose/shale"),IFS("tfc:rock/loose/slate")
            //misc
            ,Items.CLAY_BALL,IFS("tfc:ore/borax"),IFS("tfc:dirt/cinnabar"),IFS("tfc:dirt/cryolite"),IFS("tfc:ore/graphite")
            ,IFS("tfc:ore/halite"),IFS("tfc:ore/saltpeter"),IFS("tfc:ore/sulfur"),IFS("tfc:ore/sylvite"),IFS("tfc:kaolin_clay"),IFS("tfc:peat")
            ,IFS("tfc:ore/bituminous_coal"),IFS("tfc:ore/lignite")
            );

    private static final Set<Block> IGNORING_BLOCKS = ImmutableSet.of(Blocks.CAVE_AIR, Blocks.AIR, Blocks.TORCH,
            Blocks.WALL_TORCH, Blocks.SOUL_WALL_TORCH, Blocks.REDSTONE_WIRE, Blocks.CAMPFIRE, Blocks.CAKE,
            Blocks.ACACIA_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.DARK_OAK_SIGN, Blocks.JUNGLE_SIGN,
            Blocks.OAK_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN,
            Blocks.DARK_OAK_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SOUL_LANTERN,
            Blocks.LANTERN, Blocks.DETECTOR_RAIL, Blocks.RAIL, Blocks.WATER
            //Beams
            ,BFS("tfc:wood/support/acacia"),BFS("tfc:wood/support/ash"),BFS("tfc:wood/support/aspen"),BFS("tfc:wood/support/birch")
            ,BFS("tfc:wood/support/blackwood"),BFS("tfc:wood/support/chestnut"),BFS("tfc:wood/support/douglas_fir"),BFS("tfc:wood/support/hickory")
            ,BFS("tfc:wood/support/kapok"),BFS("tfc:wood/support/mangrove"),BFS("tfc:wood/support/maple"),BFS("tfc:wood/support/oak")
            ,BFS("tfc:wood/support/palm"),BFS("tfc:wood/support/pine"),BFS("tfc:wood/support/rosewood"),BFS("tfc:wood/support/sequoia")
            ,BFS("tfc:wood/support/spruce"),BFS("tfc:wood/support/sycamore"),BFS("tfc:wood/support/white_cedar"),BFS("tfc:wood/support/willow"));

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(IFS("tfc:metal/pickaxe/copper"));
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);

        ItemStack initialTool2 = new ItemStack(IFS("tfc:stone/shovel/igneous_extrusive"));
        this.updateInventory(1, initialTool2);
    }

    public int getFarmedItemsDepositAmount(){
        return 64;
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(IFS("tfc:metal/pickaxe/wrought_iron"), IFS("tfc:metal/shovel/wrought_iron"), IFS("tfc:torch"));
    }


}

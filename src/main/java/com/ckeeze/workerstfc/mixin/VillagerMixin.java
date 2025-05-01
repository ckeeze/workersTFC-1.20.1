package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Map;
import java.util.Set;

@Mixin(Villager.class)
public class VillagerMixin {

    //itemfromstring
    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    private static final Map<Item, Integer> FOOD_POINTS = ImmutableMap.ofEntries(
            Map.entry(Items.BREAD, 4),
            Map.entry(Items.POTATO, 1),
            Map.entry(Items.CARROT, 1),
            Map.entry(Items.BEETROOT, 1),
            Map.entry(IFS("tfc:food/beet"), 1),
            Map.entry(IFS("tfc:food/soybean"), 1),
            Map.entry(IFS("tfc:food/carrot"), 1),
            Map.entry(IFS("tfc:food/cabbage"), 1),
            Map.entry(IFS("tfc:green_bean"), 1),
            Map.entry(IFS("tfc:food/garlic"), 1),
            Map.entry(IFS("tfc:food/yellow_bell_pepper"), 1),
            Map.entry(IFS("tfc:food/red_bell_pepper"), 1),
            Map.entry(IFS("tfc:food/green_bell_pepper"), 1),
            Map.entry(IFS("tfc:food/squash"), 1),
            Map.entry(IFS("tfc:food/potato"), 1),
            Map.entry(IFS("tfc:food/onion"), 1),
            Map.entry(IFS("tfc:food/tomato"), 1),
            Map.entry(IFS("tfc:food/oat_bread"), 4),
            Map.entry(IFS("tfc:food/rice_bread"), 4),
            Map.entry(IFS("tfc:food/rye_bread"), 4),
            Map.entry(IFS("tfc:food/barley_bread"), 4),
            Map.entry(IFS("tfc:food/maize_bread"), 4),
            Map.entry(IFS("tfc:food/wheat_bread"), 4)
            );

    private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD,
            IFS("tfc:food/beet"),IFS("tfc:food/soybean"),IFS("tfc:food/carrot"),
            IFS("tfc:food/cabbage"),IFS("tfc:food/green_bean"),IFS("tfc:food/garlic"),IFS("tfc:food/yellow_bell_pepper"),IFS("tfc:food/red_bell_pepper"),
            IFS("tfc:food/green_bell_pepper"),IFS("tfc:food/squash"),IFS("tfc:food/potato"),IFS("tfc:food/onion"),IFS("tfc:food/tomato"),
            IFS("tfc:food/oat_bread"), IFS("tfc:food/rice_bread"),IFS("tfc:food/rye_bread"),IFS("tfc:food/barley_bread"), IFS("tfc:food/maize_bread"), IFS("tfc:food/wheat_bread"));

}

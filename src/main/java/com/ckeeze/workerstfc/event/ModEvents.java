package com.ckeeze.workerstfc.event;

import com.ckeeze.workerstfc.Config;
import com.ckeeze.workerstfc.villager.ModVillagers;
import net.dries007.tfc.common.blocks.Gem;
import net.dries007.tfc.common.blocks.GroundcoverBlockType;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.crop.Crop;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.items.*;

import com.ckeeze.workerstfc.Workerstfc;
import com.ckeeze.workerstfc.item.ModItems;

import net.dries007.tfc.util.Metal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent; //
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.npc.VillagerTrades; //
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = Workerstfc.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){

        Item CurrencyItem = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(Config.workersCurrency));
        if (CurrencyItem == null){
            CurrencyItem = ModItems.COIN.get();
        }
        Item finalCurrencyItem = CurrencyItem;

        //Potter
        if(event.getType() == ModVillagers.POTTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.BRICK, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 2),
                    new ItemStack(Items.FLOWER_POT, 1),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.CLAY_BALL, 16),
                    new ItemStack(finalCurrencyItem, 1),
                    12, 1, 0.02f));

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 2),
                    new ItemStack(TFCBlocks.CERAMIC_BOWL.get().asItem(), 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.JUG.get(), 1),
                    8, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.POT.get(), 1),
                    8, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.VESSEL.get(), 1),
                    8, 1, 0.02f));

            //Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 6),
                    new ItemStack(TFCBlocks.LARGE_VESSEL.get().asItem(), 1),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.FIRE_CLAY.get(), 4),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            //Level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCBlocks.GLAZED_LARGE_VESSELS.get(DyeColor.RED).get().asItem(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCBlocks.GLAZED_LARGE_VESSELS.get(DyeColor.YELLOW).get().asItem(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCBlocks.GLAZED_LARGE_VESSELS.get(DyeColor.GREEN).get().asItem(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCBlocks.GLAZED_LARGE_VESSELS.get(DyeColor.BLUE).get().asItem(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCBlocks.GLAZED_LARGE_VESSELS.get(DyeColor.BLACK).get().asItem(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCBlocks.GLAZED_LARGE_VESSELS.get(DyeColor.WHITE).get().asItem(), 1),
                    8, 1, 0.02f));

            //Level 5
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 16),
                    new ItemStack(TFCItems.FIRE_INGOT_MOLD.get(), 1),
                    8, 1, 0.02f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 16),
                    new ItemStack(TFCItems.KAOLIN_CLAY.get(), 1),
                    8, 1, 0.02f));

        }

        //Hunter
        if(event.getType() == ModVillagers.HUNTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 2),
                    new ItemStack(Items.FEATHER, 4),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.FOOD.get(Food.TURKEY).get(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.FOOD.get(Food.GROUSE).get(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.FOOD.get(Food.PEAFOWL).get(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.FOOD.get(Food.PHEASANT).get(), 1),
                    16, 1, 0.02f));

            //Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.FOOD.get(Food.RABBIT).get(), 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.FOOD.get(Food.VENISON).get(), 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.FOOD.get(Food.MUTTON).get(), 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 5),
                    new ItemStack(TFCItems.HIDES.get(HideItemType.RAW).get(HideItemType.Size.MEDIUM).get(), 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 8),
                    new ItemStack(TFCItems.HIDES.get(HideItemType.RAW).get(HideItemType.Size.SMALL).get(), 1),
                    16, 1, 0.02f));

            //Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCItems.HIDES.get(HideItemType.RAW).get(HideItemType.Size.LARGE).get(), 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(Items.BONE, 4),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.ARROW, 8),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            //Level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.BOW, 1),
                    new ItemStack(finalCurrencyItem, 2),
                    16, 1, 0.02f));

        }

        //Farmer
        if(event.getType() == ModVillagers.TFCFARMER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.BARLEY).get(), 8),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.OAT).get(), 8),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.WHEAT).get(), 8),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.RYE).get(), 8),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.RICE).get(), 8),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.MAIZE).get(), 8),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.BARLEY).get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.WHEAT).get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.OAT).get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.RYE).get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.RICE).get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.MAIZE).get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            //Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.POTATO).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.GARLIC).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.CABBAGE).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.GREEN_BEAN).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.YELLOW_BELL_PEPPER).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.POTATO).get(), 4),
                    12, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.CABBAGE).get(), 4),
                    12, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.GARLIC).get(), 4),
                    12, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(), 4),
                    12, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.GREEN_BEAN).get(), 4),
                    12, 1, 0.02f));

            //Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.ONION).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.CARROT).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.TOMATO).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.SQUASH).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.BEET).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.RED_BELL_PEPPER).get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.BEET).get(), 4),
                    12, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(), 4),
                    12, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.SQUASH).get(), 4),
                    12, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.CARROT).get(), 4),
                    12, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.TOMATO).get(), 4),
                    12, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(TFCItems.FOOD.get(Food.ONION).get(), 4),
                    12, 1, 0.02f));

            //Level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.PAPYRUS).get(), 8),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.PUMPKIN).get(), 8),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.SUGARCANE).get(), 8),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.JUTE).get(), 8),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.MELON).get(), 8),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.STRAW.get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

        }

        //Weaver
        if(event.getType() == ModVillagers.WEAVER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCItems.SILK_CLOTH.get(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCItems.BURLAP_CLOTH.get(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 12),
                    new ItemStack(TFCItems.WOOL_CLOTH.get(), 1),
                    16, 1, 0.02f));

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.JUTE_FIBER.get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.WOOL_YARN.get(), 10),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.STRING, 8),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            // Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.BONE_NEEDLE.get(), 1),
                    new ItemStack(finalCurrencyItem, 1),
                    3, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.WOOL.get(), 2),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.SPINDLE.get(), 1),
                    new ItemStack(finalCurrencyItem, 2),
                    3, 1, 0.02f));

            // Level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 10),
                    new ItemStack(TFCItems.CROP_SEEDS.get(Crop.JUTE).get(), 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 6),
                    new ItemStack(Items.WHITE_WOOL, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 4),
                    new ItemStack(Items.WHITE_CARPET, 1),
                    16, 1, 0.02f));
        }

        //Jeweler
        if(event.getType() == ModVillagers.JEWELER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.AMETHYST).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.PYRITE).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.OPAL).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.TOPAZ).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.RUBY).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.LAPIS_LAZULI).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.SAPPHIRE).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.EMERALD).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.GEM_DUST.get(Gem.DIAMOND).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.METAL_ITEMS.get(Metal.Default.ROSE_GOLD).get(Metal.ItemType.INGOT).get(), 1),
                    new ItemStack(finalCurrencyItem, 16),
                    12, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.METAL_ITEMS.get(Metal.Default.STERLING_SILVER).get(Metal.ItemType.INGOT).get(), 1),
                    new ItemStack(finalCurrencyItem, 8),
                    12, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 8),
                    new ItemStack(TFCItems.GEMS.get(Gem.AMETHYST).get(), 1),
                    8, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.SANDPAPER.get(), 1),
                    new ItemStack(finalCurrencyItem, 5),
                    4, 1, 0.02f));

            // Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.AMETHYST).get(), 1),
                    new ItemStack(finalCurrencyItem, 3),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.TOPAZ).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.OPAL).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.PYRITE).get(), 1),
                    new ItemStack(finalCurrencyItem, 4),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.LAPIS_LAZULI).get(), 1),
                    new ItemStack(finalCurrencyItem, 5),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.SAPPHIRE).get(), 1),
                    new ItemStack(finalCurrencyItem, 6),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.EMERALD).get(), 1),
                    new ItemStack(finalCurrencyItem, 8),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.RUBY).get(), 1),
                    new ItemStack(finalCurrencyItem, 8),
                    8, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.ORES.get(Ore.DIAMOND).get(), 1),
                    new ItemStack(finalCurrencyItem, 7),
                    8, 1, 0.02f));

            //Level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 16),
                    new ItemStack(TFCItems.GEMS.get(Gem.OPAL).get(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 16),
                    new ItemStack(TFCItems.GEMS.get(Gem.TOPAZ).get(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 16),
                    new ItemStack(TFCItems.GEMS.get(Gem.PYRITE).get(), 1),
                    8, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 20),
                    new ItemStack(TFCItems.GEMS.get(Gem.LAPIS_LAZULI).get(), 1),
                    8, 1, 0.02f));

            //Level 5
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 28),
                    new ItemStack(TFCItems.GEMS.get(Gem.RUBY).get(), 1),
                    8, 1, 0.02f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 28),
                    new ItemStack(TFCItems.GEMS.get(Gem.EMERALD).get(), 1),
                    8, 1, 0.02f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 28),
                    new ItemStack(TFCItems.GEMS.get(Gem.SAPPHIRE).get(), 1),
                    8, 1, 0.02f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 32),
                    new ItemStack(TFCItems.GEMS.get(Gem.DIAMOND).get(), 1),
                    8, 1, 0.02f));
        }

        //Tavernkeep
        if(event.getType() == ModVillagers.TAVERNKEEP.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1

        }

        if(event.getType() == ModVillagers.TFCMASON.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.SILT_MUD_BRICK.get(), 3),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.LOAM_MUD_BRICK.get(), 3),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.SILTY_LOAM_MUD_BRICK.get(), 3),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.SANDY_LOAM_MUD_BRICK.get(), 3),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.SAND.get(SandBlockType.YELLOW).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.SAND.get(SandBlockType.WHITE).get(), 24),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.SAND.get(SandBlockType.RED).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.SAND.get(SandBlockType.GREEN).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.SAND.get(SandBlockType.BLACK).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.SAND.get(SandBlockType.PINK).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.SAND.get(SandBlockType.BROWN).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            //Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.MORTAR.get(), 24),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.POWDERS.get(Powder.FLUX).get(), 12),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.GRANITE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.DIORITE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.GABBRO).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.DACITE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.ANDESITE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.BASALT).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.RHYOLITE).get(), 2),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.SLATE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.QUARTZITE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.MARBLE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.PHYLLITE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.SCHIST).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.GNEISS).get(), 2),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.CLAYSTONE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.LIMESTONE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.DOLOMITE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.SHALE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.CONGLOMERATE).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.CHERT).get(), 2),
                    16, 1, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.BRICKS.get(Rock.CHALK).get(), 2),
                    16, 1, 0.02f));

            //Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CHALK).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CHERT).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CONGLOMERATE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.SHALE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.SCHIST).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.SLATE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.GNEISS).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.GABBRO).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.GRANITE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CLAYSTONE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.DACITE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.DOLOMITE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.DIORITE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.ANDESITE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.BASALT).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.PHYLLITE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.LIMESTONE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.QUARTZITE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.MARBLE).get(Rock.BlockType.RAW).get(), 6),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            //Level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.PLAIN_ALABASTER.get(), 4),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCItems.ALABASTER_BRICK.get(), 2),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CHALK).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CHERT).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CONGLOMERATE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.SHALE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.SCHIST).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.SLATE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.GNEISS).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.GABBRO).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.GRANITE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.CLAYSTONE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.DACITE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.DOLOMITE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.DIORITE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.ANDESITE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.BASALT).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.PHYLLITE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.LIMESTONE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.QUARTZITE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCBlocks.ROCK_BLOCKS.get(Rock.MARBLE).get(Rock.BlockType.GRAVEL).get(), 32),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));
        }

        //druid
        if(event.getType() == ModVillagers.DRUID.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.CLAM).get().asItem(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.MOLLUSK).get().asItem(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.MUSSEL).get().asItem(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.POWDERS.get(Powder.SULFUR).get(), 3),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.POWDERS.get(Powder.CHARCOAL).get(), 4),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(TFCItems.POWDERS.get(Powder.SALTPETER).get(), 3),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.BONE_MEAL, 4),
                    new ItemStack(finalCurrencyItem, 1),
                    16, 1, 0.02f));

            //Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 5),
                    new ItemStack(Items.REDSTONE, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(Items.GUNPOWDER, 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 5),
                    new ItemStack(TFCBlocks.CANDLE.get().asItem(), 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.BLUBBER.get(), 1),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.BONE, 6),
                    new ItemStack(finalCurrencyItem, 1),

                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.SKELETON_SKULL, 1),
                    new ItemStack(finalCurrencyItem, 2),
                    16, 1, 0.02f));


            //Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.PURE_NITROGEN.get(), 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.PURE_PHOSPHORUS.get(), 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.PURE_POTASSIUM.get(), 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 3),
                    new ItemStack(TFCItems.OLIVE_PASTE.get(), 1),
                    16, 1, 0.02f));

            //Level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 20),
                    new ItemStack(ModItems.WEAKNESSBOMB.get(), 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 6),
                    new ItemStack(Items.BLAZE_POWDER, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 8),
                    new ItemStack(Items.GLOWSTONE_DUST, 1),
                    16, 1, 0.02f));

            //Level 5
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 32),
                    new ItemStack(ModItems.GILDEDFRUIT.get(), 1),
                    16, 1, 0.02f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 16),
                    new ItemStack(Items.EXPERIENCE_BOTTLE, 1),
                    16, 1, 0.02f));

        }

        //Dye trader
        if(event.getType() == ModVillagers.DYETRADER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.WHITE_DYE, 3),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.BLACK_DYE, 3),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.RED_DYE, 3),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.ORANGE_DYE, 3),
                    16, 1, 0.02f));

            //level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.GRAY_DYE, 2),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.LIGHT_GRAY_DYE, 2),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.BROWN_DYE, 2),
                    16, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.GREEN_DYE, 2),
                    16, 1, 0.02f));

            //level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.LIGHT_BLUE_DYE, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.LIME_DYE, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.YELLOW_DYE, 1),
                    16, 1, 0.02f));

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.PINK_DYE, 1),
                    16, 1, 0.02f));

            //level 4
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.BLUE_DYE, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.CYAN_DYE, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.PURPLE_DYE, 1),
                    16, 1, 0.02f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 1),
                    new ItemStack(Items.MAGENTA_DYE, 1),
                    16, 1, 0.02f));

        }

        if(event.getType() == ModVillagers.SPICETRADER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 6),
                    new ItemStack(TFCItems.POWDERS.get(Powder.SALT).get(), 1),
                    16, 1, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(finalCurrencyItem, 8),
                    new ItemStack(Items.SUGAR, 1),
                    16, 1, 0.02f));

        }
    }
}

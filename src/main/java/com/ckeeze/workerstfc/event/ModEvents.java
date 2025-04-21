package com.ckeeze.workerstfc.event;

import net.dries007.tfc.common.items.TFCItems;

import com.ckeeze.workerstfc.Workerstfc;
import com.ckeeze.workerstfc.item.ModItems;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer; //
import net.minecraftforge.event.village.VillagerTradesEvent; //
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.npc.VillagerProfession; //
import net.minecraft.world.entity.npc.VillagerTrades; //
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.util.List;

@Mod.EventBusSubscriber(modid = Workerstfc.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){
        if(event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COIN.get(), 1),
                    new ItemStack(TFCItems.POT.get(), 1),
                    10, 8, 0.02f));
        }
    }
}

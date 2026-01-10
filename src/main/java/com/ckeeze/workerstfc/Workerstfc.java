package com.ckeeze.workerstfc;

import com.ckeeze.workerstfc.block.ModBlocks;
import com.ckeeze.workerstfc.entity.ModEntities;
import com.ckeeze.workerstfc.item.ModCreativeTabs;
import com.ckeeze.workerstfc.item.ModItems;
import com.ckeeze.workerstfc.villager.ModVillagers;
import com.mojang.logging.LogUtils;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.workers.config.WorkersModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Workerstfc.MODID)
public class Workerstfc
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "workerstfc";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ArrayList<List<String>> START_EQUIPMENT_RECRUIT = new ArrayList<>(
            List.of(Arrays.asList("tfc:stone/javelin/igneous_extrusive", "", "", "", "", ""),
                    Arrays.asList("tfc:stone/axe/igneous_extrusive", "", "", "", "", "")
            ));
    public static ArrayList<List<String>> START_EQUIPMENT_SHIELDMAN = new ArrayList<>(
            List.of(Arrays.asList("tfc:stone/javelin/igneous_extrusive", "minecraft:shield", "", "", "", ""),
                    Arrays.asList("tfc:stone/axe/igneous_extrusive", "minecraft:shield", "", "", "", "")
            ));
    public static ArrayList<List<String>> START_EQUIPMENT_HORSEMAN = new ArrayList<>(
            List.of(Arrays.asList("tfc:stone/javelin/igneous_extrusive", "minecraft:shield", "", "", "", ""),
                    Arrays.asList("tfc:stone/axe/igneous_extrusive", "minecraft:shield", "", "", "", "")
            ));

    ArrayList<String> RECRUITMOUNTS = new ArrayList<>();

    public Workerstfc(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModVillagers.register(modEventBus);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        RECRUITMOUNTS = RecruitsServerConfig.MOUNTS;
        RECRUITMOUNTS.add("tfc:horse");

        RecruitsServerConfig.RecruitCurrency.set(Config.recruitsCurrency);
        RecruitsServerConfig.RecruitHorseUnitsHorse.set(Config.recruitHorseUnitHorse);
        WorkersModConfig.WorkersCurrency.set(Config.workersCurrency);
        if (Config.replaceStarterEquipment)
        {
            RecruitsServerConfig.RecruitStartEquipments.set(START_EQUIPMENT_RECRUIT);
            RecruitsServerConfig.ShieldmanStartEquipments.set(START_EQUIPMENT_SHIELDMAN);
            RecruitsServerConfig.HorsemanStartEquipments.set(START_EQUIPMENT_HORSEMAN);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.WEAKNESS_PROJECTILY.get(), ThrownItemRenderer::new);

            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}

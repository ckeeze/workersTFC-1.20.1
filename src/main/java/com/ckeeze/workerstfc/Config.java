package com.ckeeze.workerstfc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Workerstfc.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static ArrayList<String> MOUNTS = new ArrayList<>(
            Arrays.asList("tfc:mule", "tfc:donkey", "tfc:horse"));

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue FULL_MOON_VILLAGER_ZOMBIES = BUILDER
            .comment("Should Villager Zombies spawn on the surface every full moon? (not yet implemented")
            .comment("Does not affect underground spawns")
            .comment("Ex: true, false")
            .comment("Default: true")
            .define("fullMoonVillagerZombies",true);

    private static final ForgeConfigSpec.ConfigValue<String> WORKER_RECRUIT_CURRENCY = BUILDER
            .comment("Overrides the default currency Villager Workers and Villager Recruits mods use")
            .comment("Ex: tfc:metal/bars/gold, minecraft:emerald") //tfc example incorrect
            .comment("Default: workerstfc:coin")
            .define("workersAndRecruitsCurrency", "workerstfc:coin");

    private static final ForgeConfigSpec.BooleanValue RECRUIT_HORSE_SPAWN = BUILDER
            .comment("Overrides Horse spawns by horsemen of Villager Recruits mod")
            .comment("Ex: true, false")
            .comment("Default: false")
            .define("recruitHorseUnitsHorse", false);

    private static final ForgeConfigSpec.BooleanValue REPLACE_STARTER_EQUIPMENT = BUILDER
            .comment("Should the starter equipment of Recruits be replaced with TFC weapons?")
            .comment("Ex: true, false")
            .comment("Default: true")
            .define("replaceStarterEquipment", true);

    private static final ForgeConfigSpec.ConfigValue<List<String>> TFC_MOUNT_LIST = BUILDER
            .comment("Adds tfc mobs to the Mount Whitelist of Villager Recruits mod")
            .comment("Ex: [tfc:horse, tfc:lion]")
            .comment("Default: [tfc:horse, tfc:mule, tfc:donkey]")
            .define("recruitTFCMounts", MOUNTS);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean fullMoonVillagerZombies;
    public static String workersAndRecruitsCurrency;
    public static boolean recruitHorseUnitHorse;
    public static boolean replaceStarterEquipment;
    public static List<String> recruitTFCMounts;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        fullMoonVillagerZombies = FULL_MOON_VILLAGER_ZOMBIES.get();
        workersAndRecruitsCurrency = WORKER_RECRUIT_CURRENCY.get();
        recruitHorseUnitHorse = RECRUIT_HORSE_SPAWN.get();
        replaceStarterEquipment = REPLACE_STARTER_EQUIPMENT.get();
        recruitTFCMounts = TFC_MOUNT_LIST.get();
    }
}
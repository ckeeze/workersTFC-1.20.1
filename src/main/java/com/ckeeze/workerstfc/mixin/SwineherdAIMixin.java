package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.SwineherdEntity;
import com.talhanation.workers.entities.ai.AnimalFarmerAI;
import com.talhanation.workers.entities.ai.SwineherdAI;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.livestock.Mammal;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.calendar.Calendars;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Mixin(SwineherdAI.class)
public abstract class SwineherdAIMixin extends AnimalFarmerAI {
    private boolean breeding;
    private boolean slaughtering;
    private BlockPos workPos;

    public SwineherdAIMixin(SwineherdEntity worker) {
        this.animalFarmer = worker;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private Optional <Mammal> pig;

    private static final Set<Item> PIGFEED = ImmutableSet.of(
            Items.ROTTEN_FLESH,
            TFCItems.FOOD.get(Food.HORSE_MEAT).get(),TFCItems.FOOD.get(Food.BEAR).get(),TFCItems.FOOD.get(Food.FOX).get(),TFCItems.FOOD.get(Food.VENISON).get(),
            TFCItems.FOOD.get(Food.BEEF).get(),TFCItems.FOOD.get(Food.CHICKEN).get(),TFCItems.FOOD.get(Food.QUAIL).get(),TFCItems.FOOD.get(Food.MUTTON).get(),TFCItems.FOOD.get(Food.GRAN_FELINE).get(),
            TFCItems.FOOD.get(Food.TURTLE).get(),TFCItems.FOOD.get(Food.CHEVON).get(),TFCItems.FOOD.get(Food.PEAFOWL).get(),TFCItems.FOOD.get(Food.TURKEY).get(),TFCItems.FOOD.get(Food.PHEASANT).get(),
            TFCItems.FOOD.get(Food.GROUSE).get(),TFCItems.FOOD.get(Food.WOLF).get(),TFCItems.FOOD.get(Food.RABBIT).get(),TFCItems.FOOD.get(Food.CAMELIDAE).get(),TFCItems.FOOD.get(Food.HYENA).get(),
            TFCItems.FOOD.get(Food.DUCK).get(),TFCItems.FOOD.get(Food.FROG_LEGS).get(),TFCItems.FOOD.get(Food.COD).get(),TFCItems.FOOD.get(Food.TROPICAL_FISH).get(),TFCItems.FOOD.get(Food.CALAMARI).get(),
            TFCItems.FOOD.get(Food.SHELLFISH).get(),TFCItems.FOOD.get(Food.BLUEGILL).get(),TFCItems.FOOD.get(Food.SMALLMOUTH_BASS).get(),TFCItems.FOOD.get(Food.SALMON).get(),TFCItems.FOOD.get(Food.TARO_ROOT).get(),
            TFCItems.FOOD.get(Food.LARGEMOUTH_BASS).get(),TFCItems.FOOD.get(Food.LAKE_TROUT).get(),TFCItems.FOOD.get(Food.CRAPPIE).get(),

            TFCItems.FOOD.get(Food.WHEAT).get(),TFCItems.FOOD.get(Food.OAT).get(),
            TFCItems.FOOD.get(Food.RICE).get(),TFCItems.FOOD.get(Food.MAIZE).get(),TFCItems.FOOD.get(Food.RYE).get(),TFCItems.FOOD.get(Food.BARLEY).get(),TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(),
            TFCItems.FOOD.get(Food.OAT_GRAIN).get(),TFCItems.FOOD.get(Food.RICE_GRAIN).get(),TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(),TFCItems.FOOD.get(Food.RYE_GRAIN).get(),TFCItems.FOOD.get(Food.BARLEY_GRAIN).get(),

            TFCItems.FOOD.get(Food.SNOWBERRY).get(),TFCItems.FOOD.get(Food.BLUEBERRY).get(),TFCItems.FOOD.get(Food.BLACKBERRY).get(),TFCItems.FOOD.get(Food.RASPBERRY).get(),TFCItems.FOOD.get(Food.GOOSEBERRY).get(),
            TFCItems.FOOD.get(Food.ELDERBERRY).get(),TFCItems.FOOD.get(Food.WINTERGREEN_BERRY).get(),TFCItems.FOOD.get(Food.BANANA).get(),TFCItems.FOOD.get(Food.CHERRY).get(),TFCItems.FOOD.get(Food.GREEN_APPLE).get(),
            TFCItems.FOOD.get(Food.LEMON).get(),TFCItems.FOOD.get(Food.OLIVE).get(),TFCItems.FOOD.get(Food.PLUM).get(),TFCItems.FOOD.get(Food.ORANGE).get(),TFCItems.FOOD.get(Food.PEACH).get(),
            TFCItems.FOOD.get(Food.RED_APPLE).get(),TFCItems.FOOD.get(Food.MELON_SLICE).get(),TFCItems.FOOD.get(Food.BEET).get(),TFCItems.FOOD.get(Food.SOYBEAN).get(),TFCItems.FOOD.get(Food.CARROT).get(),
            TFCItems.FOOD.get(Food.CABBAGE).get(),TFCItems.FOOD.get(Food.GREEN_BEAN).get(),TFCItems.FOOD.get(Food.GARLIC).get(),TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(),
            TFCItems.FOOD.get(Food.GREEN_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.SQUASH).get(),TFCItems.FOOD.get(Food.POTATO).get(),TFCItems.FOOD.get(Food.ONION).get(),TFCItems.FOOD.get(Food.TOMATO).get(),
            TFCItems.FOOD.get(Food.SUGARCANE).get(),TFCItems.FOOD.get(Food.CRANBERRY).get(),TFCItems.FOOD.get(Food.CLOUDBERRY).get(),TFCItems.FOOD.get(Food.BUNCHBERRY).get(),TFCItems.FOOD.get(Food.STRAWBERRY ).get()
    );

    public boolean canUse() {
        return animalFarmer.getStatus() == AbstractWorkerEntity.Status.WORK;
    }

    @Override
    public void start() {
        super.start();
        this.workPos = animalFarmer.getStartPos();
        this.breeding = true;
        this.slaughtering = false;
    }

    @Override
    public void performWork() {
        if (!workPos.closerThan(animalFarmer.getOnPos(), 10D) && workPos != null)
            this.animalFarmer.getNavigation().moveTo(workPos.getX(), workPos.getY(), workPos.getZ(), 1);


        if (breeding){
            this.pig = findPigBreeding();
            if (this.pig.isPresent() ) {
                ItemStack Pigfeed = this.hasPigFeed();
                if (Pigfeed != null) {
                    this.animalFarmer.getNavigation().moveTo(this.pig.get(), 1);
                    this.animalFarmer.changeToBreedItem(Pigfeed.getItem());

                    if (pig.get().closerThan(this.animalFarmer, 2)) {
                        this.animalFarmer.workerSwingArm();
                        pig.get().playSound(SoundEvents.GENERIC_EAT);
                        this.consumeBreedItem(Pigfeed.getItem());
                        this.animalFarmer.getLookControl().setLookAt(pig.get().getX(), pig.get().getEyeY(), pig.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());
                        pig.get().setFamiliarity(pig.get().getFamiliarity() + 0.07F);
                        pig.get().setLastFed(Calendars.get(pig.get().level()).getTotalDays());
                        pig.get().setLastFamiliarityDecay(Calendars.get(pig.get().level()).getTotalDays());

                        this.pig = Optional.empty();
                    }
                }
                else {
                    breeding = false;
                    slaughtering = true;
                }
            } else {
                breeding = false;
                slaughtering = true;
            }
        }

        if (slaughtering) {
            List<Mammal> oldpigs = findOldPigs();
            if (!oldpigs.isEmpty())
            {
                pig = oldpigs.stream().findFirst();

                if (pig.isPresent()) {
                    if(!animalFarmer.isRequiredMainTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(true);
                    this.animalFarmer.getNavigation().moveTo(pig.get().getX(), pig.get().getY(), pig.get().getZ(), 1);

                    if (pig.get().closerThan(this.animalFarmer, 2)) {
                        pig.get().kill();

                        this.animalFarmer.workerSwingArm();
                        this.animalFarmer.playSound(SoundEvents.PLAYER_ATTACK_STRONG);
                        this.animalFarmer.consumeToolDurability();
                        this.animalFarmer.increaseFarmedItems();
                        this.animalFarmer.increaseFarmedItems();
                        this.animalFarmer.increaseFarmedItems();
                    }
                }
                else {
                    slaughtering = false;
                    breeding = true;
                }
            }

            List<Mammal> normalpigs = findPigSlaughtering();

            if (normalpigs.size() > animalFarmer.getMaxAnimalCount() && animalFarmer.hasMainToolInInv()) {
                pig = normalpigs.stream().findFirst();

                if (pig.isPresent()) {
                    if(!animalFarmer.isRequiredMainTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(true);
                    this.animalFarmer.getNavigation().moveTo(pig.get().getX(), pig.get().getY(), pig.get().getZ(), 1);

                    if (pig.get().closerThan(this.animalFarmer, 2)) {
                        pig.get().kill();

                        this.animalFarmer.workerSwingArm();
                        this.animalFarmer.playSound(SoundEvents.PLAYER_ATTACK_STRONG);
                        this.animalFarmer.consumeToolDurability();
                        this.animalFarmer.increaseFarmedItems();
                        this.animalFarmer.increaseFarmedItems();
                        this.animalFarmer.increaseFarmedItems();
                        this.animalFarmer.increaseFarmedItems();
                    }
                }
                else {
                    slaughtering = false;
                    breeding = true;
                }
            }
            else {
                if(!animalFarmer.hasMainToolInInv()){
                    this.animalFarmer.needsMainTool = true;
                }
                slaughtering = false;
                breeding = true;
            }
        }

    }
    //feeding
    private Optional<Mammal> findPigBreeding() {
        return  this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.PIG.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), Mammal::isAlive)
                .stream()
                .filter(Mammal -> Mammal.getAgeType() != TFCAnimalProperties.Age.OLD)
                .filter(Mammal -> Mammal.getFamiliarity() <= 0.5)
                .filter(Mammal::isHungry)
                .findAny();
    }
    private List<Mammal> findPigSlaughtering() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.PIG.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), Mammal::isAlive)
                .stream()
                .filter(not(Mammal::isBaby))
                .filter(not(Mammal::isFertilized))
                .collect(Collectors.toList());
    }
    //old animals are killed first
    private List<Mammal> findOldPigs() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.PIG.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), Mammal::isAlive)
                .stream()
                .filter(Mammal -> Mammal.getAgeType() == TFCAnimalProperties.Age.OLD)
                .collect(Collectors.toList());
    }

    public ItemStack hasPigFeed() {
        SimpleContainer inventory = animalFarmer.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (PIGFEED.contains(itemStack.getItem()))
                return itemStack;
        }
        return null;
    }

}

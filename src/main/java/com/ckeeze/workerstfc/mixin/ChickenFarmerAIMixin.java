package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.ChickenFarmerEntity;
import com.talhanation.workers.entities.ai.AnimalFarmerAI;
import com.talhanation.workers.entities.ai.ChickenFarmerAI;

import net.dries007.tfc.common.blocks.crop.Crop;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.livestock.OviparousAnimal;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.calendar.Calendars;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@SuppressWarnings("unused")
@Mixin(ChickenFarmerAI.class)
public abstract class ChickenFarmerAIMixin extends AnimalFarmerAI{
    private Optional<OviparousAnimal> chicken;
    private boolean breeding;
    private boolean slaughtering;
    private boolean throwEggs;
    private BlockPos workPos;

    public ChickenFarmerAIMixin(ChickenFarmerEntity worker) {
        this.animalFarmer = worker;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Unique
    private static final Set<Item> workersTFC_1_20_X$CHICKENFEED = ImmutableSet.of(
            TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(),
            TFCItems.FOOD.get(Food.OAT_GRAIN).get(),TFCItems.FOOD.get(Food.RICE_GRAIN).get(),TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(),TFCItems.FOOD.get(Food.RYE_GRAIN).get(),TFCItems.FOOD.get(Food.BARLEY_GRAIN).get(),
            TFCItems.FOOD.get(Food.SNOWBERRY).get(),TFCItems.FOOD.get(Food.BLUEBERRY).get(),TFCItems.FOOD.get(Food.BLACKBERRY).get(),TFCItems.FOOD.get(Food.RASPBERRY).get(),TFCItems.FOOD.get(Food.GOOSEBERRY).get(),
            TFCItems.FOOD.get(Food.ELDERBERRY).get(),TFCItems.FOOD.get(Food.WINTERGREEN_BERRY).get(),TFCItems.FOOD.get(Food.BANANA).get(),TFCItems.FOOD.get(Food.CHERRY).get(),TFCItems.FOOD.get(Food.GREEN_APPLE).get(),
            TFCItems.FOOD.get(Food.LEMON).get(),TFCItems.FOOD.get(Food.OLIVE).get(),TFCItems.FOOD.get(Food.PLUM).get(),TFCItems.FOOD.get(Food.ORANGE).get(),TFCItems.FOOD.get(Food.PEACH).get(),
            TFCItems.FOOD.get(Food.RED_APPLE).get(),TFCItems.FOOD.get(Food.MELON_SLICE).get(),TFCItems.FOOD.get(Food.BEET).get(),TFCItems.FOOD.get(Food.SOYBEAN).get(),TFCItems.FOOD.get(Food.CARROT).get(),
            TFCItems.FOOD.get(Food.CABBAGE).get(),TFCItems.FOOD.get(Food.GREEN_BEAN).get(),TFCItems.FOOD.get(Food.GARLIC).get(),TFCItems.FOOD.get(Food.YELLOW_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.RED_BELL_PEPPER).get(),
            TFCItems.FOOD.get(Food.GREEN_BELL_PEPPER).get(),TFCItems.FOOD.get(Food.SQUASH).get(),TFCItems.FOOD.get(Food.POTATO).get(),TFCItems.FOOD.get(Food.ONION).get(),TFCItems.FOOD.get(Food.TOMATO).get(),
            TFCItems.FOOD.get(Food.SUGARCANE).get(),TFCItems.FOOD.get(Food.CRANBERRY).get(),TFCItems.FOOD.get(Food.CLOUDBERRY).get(),TFCItems.FOOD.get(Food.BUNCHBERRY).get(),TFCItems.FOOD.get(Food.STRAWBERRY ).get(),
            TFCItems.CROP_SEEDS.get(Crop.RED_BELL_PEPPER).get(),
            TFCItems.CROP_SEEDS.get(Crop.PAPYRUS).get(),
            TFCItems.CROP_SEEDS.get(Crop.JUTE).get(),
            TFCItems.CROP_SEEDS.get(Crop.SUGARCANE).get(),
            TFCItems.CROP_SEEDS.get(Crop.SQUASH).get(),
            TFCItems.CROP_SEEDS.get(Crop.BEET).get(),
            TFCItems.CROP_SEEDS.get(Crop.WHEAT).get(),
            TFCItems.CROP_SEEDS.get(Crop.BARLEY).get(),
            TFCItems.CROP_SEEDS.get(Crop.OAT).get(),
            TFCItems.CROP_SEEDS.get(Crop.RYE).get(),
            TFCItems.CROP_SEEDS.get(Crop.MAIZE).get(),
            TFCItems.CROP_SEEDS.get(Crop.CABBAGE).get(),
            TFCItems.CROP_SEEDS.get(Crop.SOYBEAN).get(),
            TFCItems.CROP_SEEDS.get(Crop.ONION).get(),
            TFCItems.CROP_SEEDS.get(Crop.POTATO).get(),
            TFCItems.CROP_SEEDS.get(Crop.CARROT).get(),
            TFCItems.CROP_SEEDS.get(Crop.GARLIC).get(),
            TFCItems.CROP_SEEDS.get(Crop.GREEN_BEAN).get()
    );

    public void performWork() {
        if (workPos != null && !workPos.closerThan(animalFarmer.getOnPos(), 10D))
            this.animalFarmer.getNavigation().moveTo(workPos.getX(), workPos.getY(), workPos.getZ(), 1);

        if (breeding) {
            this.chicken = workersTFC_1_20_X$findChickenBreeding();
            if (this.chicken.isPresent()) {
                ItemStack ChickenFeed = this.workersTFC_1_20_X$hasSeeds();
                if (ChickenFeed != null) {
                    this.animalFarmer.changeToBreedItem(ChickenFeed.getItem());

                    this.animalFarmer.getNavigation().moveTo(this.chicken.get(), 1);
                    if (chicken.get().closerThan(this.animalFarmer, 2)) {
                        this.animalFarmer.workerSwingArm();
                        chicken.get().playSound(SoundEvents.GENERIC_EAT);
                        this.consumeBreedItem(ChickenFeed.getItem());
                        this.animalFarmer.getLookControl().setLookAt(chicken.get().getX(), chicken.get().getEyeY(), chicken.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());
                        chicken.get().setFamiliarity(chicken.get().getFamiliarity() + 0.07F);
                        chicken.get().setLastFed(Calendars.get(chicken.get().level()).getTotalDays());
                        chicken.get().setLastFamiliarityDecay(Calendars.get(chicken.get().level()).getTotalDays());
                        this.chicken = Optional.empty();
                    }
                } else {
                    breeding = false;
                    slaughtering = true;
                }
            } else {
                breeding = false;
                slaughtering = true;
            }
        }

        if (slaughtering) {
            List<OviparousAnimal> chickens = workersTFC_1_20_X$findOldChickenSlaughtering();
            boolean kill;
            if (chickens.isEmpty()){
                chickens = workersTFC_1_20_X$findChickenSlaughtering();
                if(chickens.size() <= animalFarmer.getMaxAnimalCount()){
                    chickens = workersTFC_1_20_X$findDuckSlaughtering();
                    if(chickens.size() <= animalFarmer.getMaxAnimalCount()){
                        chickens = workersTFC_1_20_X$findQuailSlaughtering();
                        kill = chickens.size() > animalFarmer.getMaxAnimalCount();
                    }
                    else{
                        kill = true;
                    }
                }
                else{
                    kill = true;
                }
            }
            else{
                kill = true;
            }
            if (kill) {
                chicken = chickens.stream().findFirst();

                if (chicken.isPresent() && animalFarmer.hasMainToolInInv()) {
                    this.animalFarmer.getNavigation().moveTo(chicken.get().getX(), chicken.get().getY(), chicken.get().getZ(), 1);

                    if(!animalFarmer.isRequiredSecondTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(false);

                    if (chicken.get().getOnPos().closerThan(animalFarmer.getOnPos(), 2)) {

                        animalFarmer.workerSwingArm();

                        chicken.get().kill();
                        animalFarmer.playSound(SoundEvents.PLAYER_ATTACK_STRONG);

                        this.animalFarmer.consumeToolDurability();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                    }
                } else {
                    slaughtering = false;
                    breeding = true;
                }
            } else {
                if(!animalFarmer.hasMainToolInInv()){
                    this.animalFarmer.needsMainTool = true;
                }
                slaughtering = false;
                breeding = true;
            }
        }
    }

    @Unique
    public ItemStack workersTFC_1_20_X$hasSeeds() {
        SimpleContainer inventory = animalFarmer.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (workersTFC_1_20_X$CHICKENFEED.contains(itemStack.getItem()))
                return itemStack;
        }
        return null;
    }

    @Unique
    private Optional<OviparousAnimal> workersTFC_1_20_X$findChickenBreeding() {
        return  this.animalFarmer.getCommandSenderWorld().getEntitiesOfClass(OviparousAnimal.class, this.animalFarmer.getBoundingBox()
                        .inflate(8D), OviparousAnimal::isAlive)
                .stream()
                .filter(OviparousAnimal -> OviparousAnimal.getAgeType() != TFCAnimalProperties.Age.OLD)
                .filter(OviparousAnimal -> OviparousAnimal.getFamiliarity() <= 0.5)
                .filter(OviparousAnimal::isHungry)
                .findAny();
    }

    @Unique
    private List<OviparousAnimal> workersTFC_1_20_X$findOldChickenSlaughtering() {
        return animalFarmer.getCommandSenderWorld()
                .getEntitiesOfClass(OviparousAnimal.class, animalFarmer.getBoundingBox().inflate(8D), OviparousAnimal::isAlive)
                .stream()
                .filter(OviparousAnimal -> OviparousAnimal.getAgeType() == TFCAnimalProperties.Age.OLD)
                .collect(Collectors.toList());

    }

    @Unique
    private List<OviparousAnimal> workersTFC_1_20_X$findChickenSlaughtering() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.CHICKEN.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), OviparousAnimal::isAlive)
                .stream()
                .filter(not(OviparousAnimal::isBaby))
                .filter(not(OviparousAnimal::isFertilized))
                .collect(Collectors.toList());
    }

    @Unique
    private List<OviparousAnimal> workersTFC_1_20_X$findDuckSlaughtering() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.DUCK.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), OviparousAnimal::isAlive)
                .stream()
                .filter(not(OviparousAnimal::isBaby))
                .filter(not(OviparousAnimal::isFertilized))
                .collect(Collectors.toList());
    }

    @Unique
    private List<OviparousAnimal> workersTFC_1_20_X$findQuailSlaughtering() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.QUAIL.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), OviparousAnimal::isAlive)
                .stream()
                .filter(not(OviparousAnimal::isBaby))
                .filter(not(OviparousAnimal::isFertilized))
                .collect(Collectors.toList());
    }

}

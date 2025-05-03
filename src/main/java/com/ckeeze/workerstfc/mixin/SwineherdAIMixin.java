package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.Translatable;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.SwineherdEntity;
import com.talhanation.workers.entities.ai.AnimalFarmerAI;
import com.talhanation.workers.entities.ai.SwineherdAI;
import net.dries007.tfc.common.entities.Faunas;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.livestock.DairyAnimal;
import net.dries007.tfc.common.entities.livestock.Mammal;
import net.dries007.tfc.common.entities.Faunas;
import net.dries007.tfc.common.entities.livestock.TFCAnimal;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.config.animals.AnimalConfig;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static net.minecraft.commands.arguments.EntityArgument.getEntity;

@Mixin(SwineherdAI.class)
public abstract class SwineherdAIMixin extends AnimalFarmerAI {
    private Mammal pig1;
    //private Optional<Faunas> pig;
    private boolean breeding;
    private boolean slaughtering;
    private BlockPos workPos;

    public SwineherdAIMixin(SwineherdEntity worker) {
        this.animalFarmer = worker;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private Optional <Mammal> pig;

    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    private static final Set<Item> PIGFEED = ImmutableSet.of(
            Items.ROTTEN_FLESH,
            IFS("tfc:food/horse_meat"),IFS("tfc:food/bear"),IFS("tfc:food/fox"),IFS("tfc:food/venison"),
            IFS("tfc:food/beef"),IFS("tfc:food/chicken"),IFS("tfc:food/quail"),IFS("tfc:food/mutton"),IFS("tfc:food/gran_feline"),
            IFS("tfc:food/turtle"),IFS("tfc:food/chevon"),IFS("tfc:food/peafowl"),IFS("tfc:food/turkey"),IFS("tfc:food/pheasant"),
            IFS("tfc:food/grouse"),IFS("tfc:food/wolf"),IFS("tfc:food/rabbit"),IFS("tfc:food/camelidae"),IFS("tfc:food/hyena"),
            IFS("tfc:food/duck"),IFS("tfc:food/frog_legs"),IFS("tfc:food/cod"),IFS("tfc:food/tropical_fish"),IFS("tfc:food/calamari"),
            IFS("tfc:food/shellfish"),IFS("tfc:food/bluegill"),IFS("tfc:food/smallmouth_bass"),IFS("tfc:food/salmon"),IFS("tfc:food/trout"),
            IFS("tfc:food/largemouth_bass"),IFS("tfc:food/lake_trout"),IFS("tfc:food/crappie"),IFS("tfc:food/wheat"),IFS("tfc:food/oat"),
            IFS("tfc:food/rice"),IFS("tfc:food/maize"),IFS("tfc:food/rye"),IFS("tfc:food/barley"),IFS("tfc:food/wheat_grain"),
            IFS("tfc:food/oat_grain"),IFS("tfc:food/rice_grain"),IFS("tfc:food/maize_grain"),IFS("tfc:food/rye_grain"),IFS("tfc:food/barley_grain"),

            IFS("tfc:food/snowberry"),IFS("tfc:food/blueberry"),IFS("tfc:food/blackberry"),IFS("tfc:food/raspberry"),IFS("tfc:food/gooseberry"),
            IFS("tfc:food/elderberry"),IFS("tfc:food/wintergreen_berry"),IFS("tfc:food/banana"),IFS("tfc:food/cherry"),IFS("tfc:food/green_apple"),
            IFS("tfc:food/lemon"),IFS("tfc:food/olive"),IFS("tfc:food/plum"),IFS("tfc:food/orange"),IFS("tfc:food/peach"),
            IFS("tfc:food/red_apple"),IFS("tfc:food/melon_slice"),IFS("tfc:food/beet"),IFS("tfc:food/soybean"),IFS("tfc:food/carrot"),
            IFS("tfc:food/cabbage"),IFS("tfc:food/green_bean"),IFS("tfc:food/garlic"),IFS("tfc:food/yellow_bell_pepper"),IFS("tfc:food/red_bell_pepper"),
            IFS("tfc:food/green_bell_pepper"),IFS("tfc:food/squash"),IFS("tfc:food/potato"),IFS("tfc:food/onion"),IFS("tfc:food/tomato"),
            IFS("tfc:food/sugarcane"),IFS("tfc:food/cranberry"),IFS("tfc:food/cloudberry"),IFS("tfc:food/bunchberry"),IFS("tfc:food/strawberry")
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
            if (oldpigs.size() > 0)
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

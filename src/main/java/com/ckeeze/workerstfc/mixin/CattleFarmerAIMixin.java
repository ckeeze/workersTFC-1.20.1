package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.CattleFarmerEntity;
import com.talhanation.workers.entities.ai.AnimalFarmerAI;
import com.talhanation.workers.entities.ai.CattleFarmerAI;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.livestock.DairyAnimal;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@SuppressWarnings("unused")
@Mixin(CattleFarmerAI.class)
public abstract class CattleFarmerAIMixin extends AnimalFarmerAI {
    private Optional<DairyAnimal> cow;
    private boolean milking;
    private boolean breeding;
    private boolean slaughtering;
    private BlockPos workPos;

    public CattleFarmerAIMixin(CattleFarmerEntity worker) {
        this.animalFarmer = worker;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private static final Set<Item> CATTLEFEED = ImmutableSet.of(
            TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(), TFCItems.FOOD.get(Food.OAT_GRAIN).get(),TFCItems.FOOD.get(Food.RICE_GRAIN).get(),
            TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(),TFCItems.FOOD.get(Food.RYE_GRAIN).get(),TFCItems.FOOD.get(Food.BARLEY_GRAIN).get()
            );

    public void performWork() {
        if (!workPos.closerThan(animalFarmer.getOnPos(), 10D) && workPos != null)
            this.animalFarmer.getNavigation().moveTo(workPos.getX(), workPos.getY(), workPos.getZ(), 1);

        if (milking){
            this.cow = findCowMilking();
            if (this.cow.isPresent() && hasBucket()) {
                this.animalFarmer.getNavigation().moveTo(this.cow.get(), 1);

                if(!animalFarmer.isRequiredMainTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(true);

                if (cow.get().closerThan(this.animalFarmer, 2)) {

                    this.animalFarmer.getLookControl().setLookAt(cow.get().getX(), cow.get().getEyeY(), cow.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());

                    animalFarmer.workerSwingArm();
                    milkCow(this.cow.get());
                    this.cow = Optional.empty();
                }
            }
            this.cow = findYakMilking();
            if (this.cow.isPresent() && hasBucket()) {
                this.animalFarmer.getNavigation().moveTo(this.cow.get(), 1);

                if(!animalFarmer.isRequiredMainTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(true);

                if (cow.get().closerThan(this.animalFarmer, 2)) {

                    this.animalFarmer.getLookControl().setLookAt(cow.get().getX(), cow.get().getEyeY(), cow.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());

                    animalFarmer.workerSwingArm();
                    milkCow(this.cow.get());
                    this.cow = Optional.empty();
                }
            }
            this.cow = findGoatMilking();
            if (this.cow.isPresent() && hasBucket()) {
                this.animalFarmer.getNavigation().moveTo(this.cow.get(), 1);

                if(!animalFarmer.isRequiredMainTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(true);

                if (cow.get().closerThan(this.animalFarmer, 2)) {

                    this.animalFarmer.getLookControl().setLookAt(cow.get().getX(), cow.get().getEyeY(), cow.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());

                    animalFarmer.workerSwingArm();
                    milkCow(this.cow.get());
                    this.cow = Optional.empty();
                }
            }
            else {
                milking = false;
                breeding = true;
            }

        }

        if (breeding){
            this.cow = findDairyAnimalFeeding();
            if (this.cow.isPresent() ) {
                int i = cow.get().getAge();

                ItemStack CattleFeed = this.hasCattleFeed();
                if (CattleFeed != null) {
                    this.animalFarmer.getNavigation().moveTo(this.cow.get(), 1);
                    this.animalFarmer.changeToBreedItem(CattleFeed.getItem());

                    if (cow.get().closerThan(this.animalFarmer, 2)) {
                        this.animalFarmer.workerSwingArm();
                        cow.get().playSound(SoundEvents.GENERIC_EAT);
                        this.consumeBreedItem(CattleFeed.getItem());
                        this.animalFarmer.getLookControl().setLookAt(cow.get().getX(), cow.get().getEyeY(), cow.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());
                        cow.get().setFamiliarity(cow.get().getFamiliarity() + 0.07F);
                        cow.get().setLastFed(Calendars.get(cow.get().level()).getTotalDays());
                        cow.get().setLastFamiliarityDecay(Calendars.get(cow.get().level()).getTotalDays());

                        this.cow = Optional.empty();
                    }
                }
                else {
                    breeding = false;
                    slaughtering = true;
                }
            }

            else {
                breeding = false;
                slaughtering = true;
            }
        }

        if (slaughtering) {
            List<DairyAnimal> cows = findOldDairyAnimal();
            boolean kill;
            if (cows.isEmpty()) {
                cows = findCowSlaughtering();
                if (cows.size() <= animalFarmer.getMaxAnimalCount()) {
                    cows = findGoatlaughtering();
                    if (cows.size() <= animalFarmer.getMaxAnimalCount()) {
                        cows = findYakSlaughtering();
                        kill = cows.size() > animalFarmer.getMaxAnimalCount();
                    } else {
                        kill = true;
                    }
                } else {
                    kill = true;
                }
            } else {
                kill = true;
            }
            if (kill) {
                cow = cows.stream().findFirst();

                if (cow.isPresent() && animalFarmer.hasSecondToolInInv()) {
                    this.animalFarmer.getNavigation().moveTo(cow.get().getX(), cow.get().getY(), cow.get().getZ(), 1);

                    if (!animalFarmer.isRequiredSecondTool(animalFarmer.getMainHandItem()))
                        this.animalFarmer.changeToTool(false);

                    if (cow.get().getOnPos().closerThan(animalFarmer.getOnPos(), 2)) {

                        animalFarmer.workerSwingArm();

                        cow.get().kill();
                        animalFarmer.playSound(SoundEvents.PLAYER_ATTACK_STRONG);

                        this.animalFarmer.consumeToolDurability();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                    }
                } else {
                    if (!animalFarmer.hasSecondToolInInv()) {
                        animalFarmer.needsSecondTool = true;
                    }
                    slaughtering = false;
                    milking = true;
                }
            } else {
                slaughtering = false;
                milking = true;
            }
        }
    }

    public boolean hasBucket(){
        SimpleContainer inventory = animalFarmer.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem().equals(TFCItems.WOODEN_BUCKET.get())){
                final IFluidHandlerItem destFluidItemHandler = Helpers.getCapability(itemStack, Capabilities.FLUID_ITEM);
                if (destFluidItemHandler != null){
                    return destFluidItemHandler.getFluidInTank(1) == FluidStack.EMPTY;
                }
                else{
                    return true;
                }
            }
        }
        return false;
    }

    public ItemStack hasCattleFeed() {
        SimpleContainer inventory = animalFarmer.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (CATTLEFEED.contains(itemStack.getItem()))
                return itemStack;
        }
        return null;
    }

    //COWS
    public void milkCow(DairyAnimal cow) {
        animalFarmer.workerSwingArm();
        SimpleContainer inventory = animalFarmer.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem().equals(TFCItems.WOODEN_BUCKET.get())){
                final IFluidHandlerItem destFluidItemHandler = Helpers.getCapability(itemStack, Capabilities.FLUID_ITEM);
                if (destFluidItemHandler != null){
                    if(destFluidItemHandler.getFluidInTank(1) == FluidStack.EMPTY){
                        itemStack.shrink(1);
                    }
                }
                else{
                    itemStack.shrink(1);
                }
            }
        }
        cow.addUses(1);
        cow.setProductsCooldown();
        ItemStack bucket = TFCItems.WOODEN_BUCKET.get().getDefaultInstance();
        FluidStack milk = new FluidStack(ForgeMod.MILK.get(), 1000);
        final IFluidHandlerItem destFluidItemHandler = Helpers.getCapability(bucket, Capabilities.FLUID_ITEM);
        if (destFluidItemHandler != null){
            destFluidItemHandler.fill(milk, IFluidHandler.FluidAction.EXECUTE);
        }
        inventory.addItem(bucket);
        animalFarmer.increaseFarmedItems();
        cow.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
    }

    private Optional<DairyAnimal> findCowMilking() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.COW.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(DairyAnimal::hasProduct)
                .filter(DairyAnimal -> DairyAnimal.getFamiliarity() >= 0.15F)
                .findAny();
    }

    private Optional<DairyAnimal> findDairyAnimalFeeding() {
        return  this.animalFarmer.getCommandSenderWorld().getEntitiesOfClass(DairyAnimal.class, this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(DairyAnimal -> DairyAnimal.getAgeType() != TFCAnimalProperties.Age.OLD)
                .filter(DairyAnimal -> DairyAnimal.getFamiliarity() <= 0.5)
                .filter(DairyAnimal::isHungry)
                .findAny();
    }

    private List<DairyAnimal> findCowSlaughtering() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.COW.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(not(DairyAnimal::isBaby))
                .filter(not(DairyAnimal::isFertilized))
                .collect(Collectors.toList());
    }

    private List<DairyAnimal> findOldDairyAnimal() {
        return this.animalFarmer.getCommandSenderWorld().getEntitiesOfClass(DairyAnimal.class, this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(DairyAnimal -> DairyAnimal.getAgeType() == TFCAnimalProperties.Age.OLD)
                .collect(Collectors.toList());
    }

    //YAKS-------------------------------------------------------
    public void milkYak(DairyAnimal cow) {
        animalFarmer.workerSwingArm();
        SimpleContainer inventory = animalFarmer.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem().equals(TFCItems.WOODEN_BUCKET.get())){
                itemStack.shrink(1);
            }
        }
        cow.addUses(1);
        cow.setProductsCooldown();
        ItemStack bucket = TFCItems.WOODEN_BUCKET.get().getDefaultInstance();
        FluidStack milk = new FluidStack(ForgeMod.MILK.get(), 1000);
        final IFluidHandlerItem destFluidItemHandler = Helpers.getCapability(bucket, Capabilities.FLUID_ITEM);
        if (destFluidItemHandler != null){
            destFluidItemHandler.fill(milk, IFluidHandler.FluidAction.EXECUTE);
        }
        inventory.addItem(bucket);
        animalFarmer.increaseFarmedItems();
        cow.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
    }

    private Optional<DairyAnimal> findYakMilking() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.YAK.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(DairyAnimal::hasProduct)
                .filter(DairyAnimal -> DairyAnimal.getFamiliarity() >= 0.15F)
                .findAny();
    }

    private List<DairyAnimal> findYakSlaughtering() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.YAK.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(not(DairyAnimal::isBaby))
                .filter(not(DairyAnimal::isFertilized))
                .collect(Collectors.toList());
    }


    //GOATS

    public void milkGoat(DairyAnimal cow) {
        animalFarmer.workerSwingArm();
        SimpleContainer inventory = animalFarmer.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem().equals(TFCItems.WOODEN_BUCKET.get())){
                itemStack.shrink(1);
            }
        }
        cow.addUses(1);
        cow.setProductsCooldown();
        ItemStack bucket = TFCItems.WOODEN_BUCKET.get().getDefaultInstance();
        FluidStack milk = new FluidStack(ForgeMod.MILK.get(), 1000);
        final IFluidHandlerItem destFluidItemHandler = Helpers.getCapability(bucket, Capabilities.FLUID_ITEM);
        if (destFluidItemHandler != null){
            destFluidItemHandler.fill(milk, IFluidHandler.FluidAction.EXECUTE);
        }
        inventory.addItem(bucket);
        animalFarmer.increaseFarmedItems();
        cow.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
    }

    private Optional<DairyAnimal> findGoatMilking() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.GOAT.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(DairyAnimal::hasProduct)
                .filter(DairyAnimal -> DairyAnimal.getFamiliarity() >= 0.15F)
                .findAny();
    }

    private List<DairyAnimal> findGoatlaughtering() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.GOAT.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(not(DairyAnimal::isBaby))
                .filter(not(DairyAnimal::isFertilized))
                .collect(Collectors.toList());
    }

}

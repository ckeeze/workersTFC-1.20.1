package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.CattleFarmerEntity;
import com.talhanation.workers.entities.ai.AnimalFarmerAI;
import com.talhanation.workers.entities.ai.CattleFarmerAI;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.capabilities.ItemStackFluidHandler;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.livestock.DairyAnimal;
import net.dries007.tfc.common.entities.livestock.Mammal;
import net.dries007.tfc.common.entities.livestock.TFCAnimal;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.FluidContainerItem;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.ingredients.FluidStackIngredient;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.events.AnimalProductEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.common.extensions.IForgeFluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

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

    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

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
                    animalFarmer.increaseFarmedItems();
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
                    animalFarmer.increaseFarmedItems();
                    milkYak(this.cow.get());
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
                    animalFarmer.increaseFarmedItems();
                    milkGoat(this.cow.get());
                    this.cow = Optional.empty();
                }
            }
            else {
                milking = false;
                breeding = true;
            }

        }

        if (breeding){
            this.cow = findCowfeeding();
            if (this.cow.isPresent() ) {
                int i = cow.get().getAge();

                if (i == 0 && this.hasBreedItem(Items.WHEAT)) {
                    this.animalFarmer.getNavigation().moveTo(this.cow.get(), 1);
                    this.animalFarmer.changeToBreedItem(Items.WHEAT);

                    if (cow.get().closerThan(this.animalFarmer, 2)) {

                        this.animalFarmer.getLookControl().setLookAt(cow.get().getX(), cow.get().getEyeY(), cow.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());

                        this.consumeBreedItem(Items.WHEAT);
                        animalFarmer.workerSwingArm();
                        this.cow.get().setInLove(null);
                        this.cow = Optional.empty();
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
            List<DairyAnimal> cows = findCowSlaughtering();
            if (cows.size() > animalFarmer.getMaxAnimalCount() && animalFarmer.hasMainToolInInv()) {
                cow = cows.stream().findFirst();

                if(!animalFarmer.isRequiredSecondTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(false);

                if (cow.isPresent()) {
                    this.animalFarmer.getNavigation().moveTo(cow.get().getX(), cow.get().getY(), cow.get().getZ(), 1);
                    if (cow.get().closerThan(this.animalFarmer, 2)) {
                        cow.get().kill();
                        animalFarmer.playSound(SoundEvents.PLAYER_ATTACK_STRONG);

                        this.animalFarmer.consumeToolDurability();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.workerSwingArm();
                    }
                }

            }
            else {
                if(!animalFarmer.hasMainToolInInv()){
                    this.animalFarmer.needsMainTool = true;
                    this.animalFarmer.updateNeedsTool();
                }
                slaughtering = false;
                milking = true;
            }
        }

    }

    public boolean hasBucket(){
        SimpleContainer inventory = animalFarmer.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem().equals(IFS("tfc:wooden_bucket"))){
                return true;
            }
        }
        return false;
    }

    //COWS
    public void milkCow(DairyAnimal cow) {
        animalFarmer.workerSwingArm();
        SimpleContainer inventory = animalFarmer.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem().equals(IFS("tfc:wooden_bucket"))){
                itemStack.shrink(1);
            }
        }
        cow.addUses(1);
        cow.setProductsCooldown();
        ItemStack bucket = IFS("tfc:wooden_bucket").getDefaultInstance();
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
                .findAny();
    }

    private Optional<DairyAnimal> findCowfeeding() {
        return  this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.COW.get(), this.animalFarmer.getBoundingBox()
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
                .filter(not(DairyAnimal::isInLove))
                .filter(not(DairyAnimal::hasProduct))
                .collect(Collectors.toList());
    }

    private List<DairyAnimal> findOldDairyAnimal() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.COW.get(), this.animalFarmer.getBoundingBox()
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
            if (itemStack.getItem().equals(IFS("tfc:wooden_bucket"))){
                itemStack.shrink(1);
            }
        }
        cow.addUses(1);
        cow.setProductsCooldown();
        ItemStack bucket = IFS("tfc:wooden_bucket").getDefaultInstance();
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
                .findAny();
    }

    private List<DairyAnimal> findYakSlaughtering() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.YAK.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(not(DairyAnimal::isBaby))
                .filter(not(DairyAnimal::isInLove))
                .filter(not(DairyAnimal::hasProduct))
                .collect(Collectors.toList());
    }

    private List<DairyAnimal> findOldYaks() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.YAK.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(DairyAnimal -> DairyAnimal.getAgeType() == TFCAnimalProperties.Age.OLD)
                .collect(Collectors.toList());
    }

    //GOATS

    public void milkGoat(DairyAnimal cow) {
        animalFarmer.workerSwingArm();
        SimpleContainer inventory = animalFarmer.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem().equals(IFS("tfc:wooden_bucket"))){
                itemStack.shrink(1);
            }
        }
        cow.addUses(1);
        cow.setProductsCooldown();
        ItemStack bucket = IFS("tfc:wooden_bucket").getDefaultInstance();
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
                .findAny();
    }

    private List<DairyAnimal> findGoatlaughtering() {
        return  animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.GOAT.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(not(DairyAnimal::isBaby))
                .filter(not(DairyAnimal::isInLove))
                .filter(not(DairyAnimal::hasProduct))
                .collect(Collectors.toList());
    }

    private List<DairyAnimal> findOldGoats() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.GOAT.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), DairyAnimal::isAlive)
                .stream()
                .filter(DairyAnimal -> DairyAnimal.getAgeType() == TFCAnimalProperties.Age.OLD)
                .collect(Collectors.toList());
    }
}

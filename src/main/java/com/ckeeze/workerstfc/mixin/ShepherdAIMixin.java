package com.ckeeze.workerstfc.mixin;

import com.talhanation.workers.entities.ShepherdEntity;
import com.talhanation.workers.entities.ai.AnimalFarmerAI;
import com.talhanation.workers.entities.ai.ShepherdAI;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.entities.livestock.WoolyAnimal;
import net.dries007.tfc.util.calendar.Calendars;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.IForgeShearable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@SuppressWarnings("unused")
@Mixin(ShepherdAI.class)
public abstract class ShepherdAIMixin extends AnimalFarmerAI implements IForgeShearable {

    private Optional<WoolyAnimal> sheep;
    private boolean sheering;
    private boolean breeding;
    private boolean slaughtering;
    private BlockPos workPos;

    public ShepherdAIMixin(ShepherdEntity worker) {
        this.animalFarmer = worker;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public void performWork() {


        if (workPos != null && !workPos.closerThan(animalFarmer.getOnPos(), 10D))
            this.animalFarmer.getNavigation().moveTo(workPos.getX(), workPos.getY(), workPos.getZ(), 1);

        if (sheering) {
            this.sheep = findSheepSheering();
            if (animalFarmer.hasMainToolInInv() && this.sheep.isPresent()) {

                this.animalFarmer.getNavigation().moveTo(this.sheep.get(), 1);

                if(!animalFarmer.isRequiredMainTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(true);

                if (sheep.get().closerThan(this.animalFarmer, 2)) {
                    this.sheerSheep(this.sheep.get());
                    sheep.get().playSound(SoundEvents.SHEEP_SHEAR);
                    this.animalFarmer.getLookControl().setLookAt(sheep.get().getX(), sheep.get().getEyeY(), sheep.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());
                    this.sheep = Optional.empty();
                }
            }
            else {
                if(!animalFarmer.hasMainToolInInv()){
                    animalFarmer.needsMainTool = true;
                }
                sheering = false;
                breeding = true;
            }
        }

        if (breeding) {
            this.sheep = findSheepBreeding();
            if (this.sheep.isPresent()) {
                ItemStack SheepFeed = this.hasSheepFeed();
                if (SheepFeed != null) {
                    this.animalFarmer.changeToBreedItem(SheepFeed.getItem());

                    this.animalFarmer.getNavigation().moveTo(this.sheep.get(), 1);
                    if (sheep.get().closerThan(this.animalFarmer, 2)) {
                        this.animalFarmer.workerSwingArm();
                        sheep.get().playSound(SoundEvents.GENERIC_EAT);
                        this.consumeBreedItem(SheepFeed.getItem());
                        this.animalFarmer.getLookControl().setLookAt(sheep.get().getX(), sheep.get().getEyeY(), sheep.get().getZ(), 10.0F, (float) this.animalFarmer.getMaxHeadXRot());
                        sheep.get().setFamiliarity(sheep.get().getFamiliarity() + 0.07F);
                        sheep.get().setLastFed(Calendars.get(sheep.get().level()).getTotalDays());
                        sheep.get().setLastFamiliarityDecay(Calendars.get(sheep.get().level()).getTotalDays());
                        this.sheep = Optional.empty();
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
            List<WoolyAnimal> sheeps = findOldWoolySlaughtering();
            boolean kill;
            if (sheeps.isEmpty()){
                sheeps = findSheepSlaughtering();
                if(sheeps.size() <= animalFarmer.getMaxAnimalCount()){
                    sheeps = findAlpacaSlaughtering();
                    if(sheeps.size() <= animalFarmer.getMaxAnimalCount()){
                        sheeps = findMuskOxSlaughtering();
                        kill = sheeps.size() > animalFarmer.getMaxAnimalCount();
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
                sheep = sheeps.stream().findFirst();

                if (sheep.isPresent() && animalFarmer.hasSecondToolInInv()) {
                    this.animalFarmer.getNavigation().moveTo(sheep.get().getX(), sheep.get().getY(), sheep.get().getZ(), 1);

                    if(!animalFarmer.isRequiredSecondTool(animalFarmer.getMainHandItem())) this.animalFarmer.changeToTool(false);

                    if (sheep.get().getOnPos().closerThan(animalFarmer.getOnPos(), 2)) {

                        animalFarmer.workerSwingArm();

                        sheep.get().kill();
                        animalFarmer.playSound(SoundEvents.PLAYER_ATTACK_STRONG);

                        this.animalFarmer.consumeToolDurability();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                        animalFarmer.increaseFarmedItems();
                    }
                } else {
                    if(!animalFarmer.hasSecondToolInInv()){
                        animalFarmer.needsSecondTool = true;
                    }
                    slaughtering = false;
                    sheering = true;
                }
            } else {
                slaughtering = false;
                sheering = true;
            }
        }
    }

    public void sheerSheep(WoolyAnimal sheepEntity) {
        sheepEntity.addUses(1);
        sheepEntity.setProductsCooldown();
        this.animalFarmer.getInventory().addItem(sheepEntity.getWoolItem());
        animalFarmer.increaseFarmedItems();
        if (!this.animalFarmer.swinging) {
            this.animalFarmer.workerSwingArm();
        }
        this.animalFarmer.consumeToolDurability();
    }

    private Optional<WoolyAnimal> findSheepSheering() {
        return animalFarmer.getCommandSenderWorld()
                .getEntitiesOfClass(WoolyAnimal.class, animalFarmer.getBoundingBox().inflate(8D), WoolyAnimal::hasProduct)
                .stream()
                .filter(WoolyAnimal -> WoolyAnimal.getAgeType() != TFCAnimalProperties.Age.OLD)
                .filter(WoolyAnimal -> WoolyAnimal.getFamiliarity() > 0.15F)
                .findAny();
    }

    private Optional<WoolyAnimal> findSheepBreeding() {
        return animalFarmer.getCommandSenderWorld()
                .getEntitiesOfClass(WoolyAnimal.class, animalFarmer.getBoundingBox().inflate(8D), WoolyAnimal::isHungry)
                .stream()
                .filter(WoolyAnimal -> WoolyAnimal.getAgeType() != TFCAnimalProperties.Age.OLD)
                .filter(WoolyAnimal -> WoolyAnimal.getFamiliarity() < 0.9F)
                .findAny();
    }

    private List<WoolyAnimal> findOldWoolySlaughtering() {
        return animalFarmer.getCommandSenderWorld()
                .getEntitiesOfClass(WoolyAnimal.class, animalFarmer.getBoundingBox().inflate(8D), WoolyAnimal::isAlive)
                .stream()
                .filter(WoolyAnimal -> WoolyAnimal.getAgeType() == TFCAnimalProperties.Age.OLD)
                .collect(Collectors.toList());

    }

    private List<WoolyAnimal> findSheepSlaughtering() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.SHEEP.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), WoolyAnimal::isAlive)
                .stream()
                .filter(not(WoolyAnimal::isBaby))
                .filter(not(WoolyAnimal::isFertilized))
                .collect(Collectors.toList());

    }

    private List<WoolyAnimal> findAlpacaSlaughtering() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.ALPACA.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), WoolyAnimal::isAlive)
                .stream()
                .filter(not(WoolyAnimal::isBaby))
                .filter(not(WoolyAnimal::isFertilized))
                .collect(Collectors.toList());

    }

    private List<WoolyAnimal> findMuskOxSlaughtering() {
        return this.animalFarmer.getCommandSenderWorld().getEntities(TFCEntities.MUSK_OX.get(), this.animalFarmer.getBoundingBox()
                        .inflate(8D), WoolyAnimal::isAlive)
                .stream()
                .filter(not(WoolyAnimal::isBaby))
                .filter(not(WoolyAnimal::isFertilized))
                .collect(Collectors.toList());

    }

    public ItemStack hasSheepFeed() {
        SimpleContainer inventory = animalFarmer.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.is(TFCTags.Items.SHEEP_FOOD))
                return itemStack;
        }
        return null;
    }
}

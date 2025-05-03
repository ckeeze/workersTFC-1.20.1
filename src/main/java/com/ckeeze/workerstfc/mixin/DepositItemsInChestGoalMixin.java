package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.Translatable;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ai.DepositItemsInChestGoal;
import com.talhanation.workers.entities.*;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.food.FoodCapability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;

import java.util.EnumSet;
import java.util.Set;

import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DepositItemsInChestGoal.class)
public abstract class DepositItemsInChestGoalMixin extends Goal {
    private final AbstractWorkerEntity worker;

    @Shadow
    public BlockPos chestPos;
    @Shadow
    public Container container;
    @Shadow
    public boolean messageCantFindChest;
    @Shadow
    public boolean messageNoFood;
    @Shadow
    public boolean messageChestFull;
    @Shadow
    public int timer = 0;
    @Shadow
    public boolean setTimer = false;
    @Shadow
    public boolean noSpaceInvMessage;
    @Shadow
    public boolean noToolMessage;
    @Shadow
    public boolean messageNoChest;
    @Shadow
    public boolean canResetPaymentTimer = false;

    public DepositItemsInChestGoalMixin(AbstractWorkerEntity worker) {
        this.worker = worker;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private static Item IFS(String S){
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(S));
    }

    //FOOD CHANGES
    private static final Set<Item> RAW_FOOD = ImmutableSet.of(
            IFS("tfc:food/horse_meat"),IFS("tfc:food/bear"),IFS("tfc:food/fox"),IFS("tfc:food/pork"),IFS("tfc:food/venison"),
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

    private ItemStack getFoodFromInv(Container inv){
        ItemStack itemStack = null;
        for(int i = 0; i < inv.getContainerSize(); i++){
            ItemStack itemStack2 = inv.getItem(i);
            if(itemStack2.isEdible() && !FoodCapability.isRotten(itemStack2) && !RAW_FOOD.contains(itemStack2.getItem())){
                itemStack = inv.getItem(i);
                break;
            }
        }
        return itemStack;
    }

    private boolean hasFoodInInv(){
        boolean hasfood = false;
        SimpleContainer inventory = worker.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.isEdible() && !FoodCapability.isRotten(itemStack) && !RAW_FOOD.contains(itemStack.getItem())) hasfood = true;
        }
        return hasfood;
    }

    private boolean isFoodInChest(Container container){
        for(int i = 0; i < container.getContainerSize(); i++) {
            ItemStack foodItem = container.getItem(i);
            if(foodItem.isEdible() && !FoodCapability.isRotten(foodItem) && !RAW_FOOD.contains(foodItem.getItem())){
                return true;
            }
        }
        return false;
    }
    //FOOD CHANGES

    //TICK CHANGES
    public void tick() {
        this.chestPos = worker.getChestPos();

        if (chestPos != null) {

            this.worker.getNavigation().moveTo(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1.1D);

            if (chestPos.closerThan(worker.getOnPos(), 2.5)) {
                this.worker.getNavigation().stop();
                this.worker.getLookControl().setLookAt(chestPos.getX(), chestPos.getY() + 1, chestPos.getZ(), 10.0F, (float) this.worker.getMaxHeadXRot());

                if(container != null){
                    if(!setTimer){
                        this.interactChest(container, true);

                        if(worker.paymentTimer == 0){
                            worker.checkPayment(container);
                            canResetPaymentTimer = true;
                        }

                        this.depositItems(container);

                        if(!canAddItemsInInventory()){
                            if(worker.getOwner() != null && noSpaceInvMessage){
                                worker.tellPlayer(worker.getOwner(), Translatable.TEXT_NO_SPACE_INV);
                                noSpaceInvMessage = false;
                            }

                        }

                        this.reequipMainTool();
                        this.reequipSecondTool();

                        if(this.worker instanceof MinerEntity){
                            if(!hasEnoughOfItem(IFS("tfc:torch"), 16)) this.getItemFromChest(IFS("tfc:torch"));
                        }
                        //TODO: ADD fisherman takes boat
                        if(this.worker instanceof FarmerEntity){
                            if(!hasEnoughOfItem(Items.BONE_MEAL, 32)) this.getItemFromChest(Items.BONE_MEAL);
                            if(!hasEnoughOfItem(IFS("tfc:powder/wood_ash"), 32)) this.getItemFromChest(IFS("tfc:powder/wood_ash"));
                            if(!hasEnoughOfItem(IFS("tfc:powder/sylvite"), 32)) this.getItemFromChest(IFS("tfc:powder/sylvite"));
                            if(!hasEnoughOfItem(IFS("tfc:powder/saltpeter"), 32)) this.getItemFromChest(IFS("tfc:powder/saltpeter"));
                            if(!hasEnoughOfItem(IFS("tfc:compost"), 32)) this.getItemFromChest(IFS("tfc:compost"));
                            if(!hasEnoughOfItem(IFS("tfc:groundcover/guano"), 32)) this.getItemFromChest(IFS("tfc:groundcover/guano"));
                        }

                        if(this.worker instanceof ChickenFarmerEntity chickenFarmer){
                            if(chickenFarmer.getUseEggs()) this.getItemFromChest(Items.EGG);
                            if(!hasEnoughOfItem(Items.WHEAT_SEEDS, 32)) this.getItemFromChest(Items.WHEAT_SEEDS);
                            if(!hasEnoughOfItem(Items.PUMPKIN_SEEDS, 32)) this.getItemFromChest(Items.PUMPKIN_SEEDS);
                            if(!hasEnoughOfItem(Items.MELON_SEEDS, 32)) this.getItemFromChest(Items.MELON_SEEDS);
                            if(!hasEnoughOfItem(Items.BEETROOT_SEEDS, 32)) this.getItemFromChest(Items.BEETROOT_SEEDS);
                        }

                        if(this.worker instanceof ShepherdEntity || this.worker instanceof CattleFarmerEntity){
                            if(!hasEnoughOfItem(Items.WHEAT, 32)) this.getItemFromChest(Items.WHEAT);
                        }

                        if(this.worker instanceof SwineherdEntity){
                            if(!hasEnoughOfItem(Items.CARROT, 32)) this.getItemFromChest(Items.CARROT);
                            if(!hasEnoughOfItem(Items.ROTTEN_FLESH, 32)) this.getItemFromChest(Items.ROTTEN_FLESH);
                        }

                        if(this.worker instanceof CattleFarmerEntity){
                            if(!hasEnoughOfItem(Items.MILK_BUCKET, 3)) this.getItemFromChest(Items.BUCKET);
                        }

                        if(this.worker.needsToGetFood() && !this.hasFoodInInv()){
                            if (isFoodInChest(container)) {
                                for (int i = 0; i < 3; i++) {
                                    ItemStack foodItem = this.getFoodFromInv(container);
                                    ItemStack food;

                                    if (foodItem != null){
                                        food = foodItem.copy();
                                        food.setCount(1);
                                        worker.getInventory().addItem(food);
                                        foodItem.shrink(1);
                                    }
                                }
                            }
                            else {
                                if(worker.getOwner() != null && messageNoFood){
                                    worker.tellPlayer(worker.getOwner(), Translatable.TEXT_NO_FOOD);
                                    messageNoFood = false;
                                }
                            }
                        }

                        if(((!worker.hasMainToolInInv() || worker.needsMainTool) && worker.hasAMainTool()) || ((!worker.hasSecondToolInInv() || worker.needsSecondTool) && worker.hasASecondTool())){
                            if(worker.getOwner() != null && noToolMessage) {
                                worker.tellPlayer(worker.getOwner(), Translatable.TEXT_OUT_OF_TOOLS());
                                noToolMessage = false;
                            }
                        }

                        timer = 30;
                        setTimer = true;
                    }
                }
                else {
                    container = getContainer(chestPos);

                    if(container == null){
                        if(messageCantFindChest && worker.getOwner() != null){
                            this.worker.tellPlayer(worker.getOwner(), Translatable.TEXT_CANT_FIND_CHEST);
                            this.messageCantFindChest = false;
                        }
                        this.worker.clearChestPos();
                    }
                }

            }
        }
        else {
            if(messageNoChest && worker.getOwner() != null){
                this.worker.tellPlayer(worker.getOwner(), Translatable.NEED_CHEST);
                messageNoChest = false;
            }
        }

        if(setTimer){
            if(timer > 0) timer--;
            if(timer == 0) stop();
        }
    }
    //TICK SHADOWS
    @Shadow
    private Container getContainer(BlockPos chestPos) {
        BlockEntity entity = worker.getCommandSenderWorld().getBlockEntity(chestPos);
        BlockState blockState = worker.getCommandSenderWorld().getBlockState(chestPos);
        if (blockState.getBlock() instanceof ChestBlock chestBlock) {
            return ChestBlock.getContainer(chestBlock, blockState, worker.getCommandSenderWorld(), chestPos, false);
        } else if (entity instanceof Container containerEntity) {
            return containerEntity;
        }
        else {
            messageCantFindChest = true;
        }
        return null;
    }

    @Shadow
    private boolean hasEnoughOfItem(Item item, int x){
        return true;
    }

    @Shadow
    private void getItemFromChest(Item item){}

    @Shadow
    private void depositItems(Container container){}

    @Shadow
    private void reequipSecondTool(){}

    @Shadow
    private void reequipMainTool(){}

    @Shadow
    public void interactChest(Container container, boolean open) {}

    @Shadow
    private boolean canAddItemsInInventory(){
        return false;
    }
    //TICK CHANGES
}

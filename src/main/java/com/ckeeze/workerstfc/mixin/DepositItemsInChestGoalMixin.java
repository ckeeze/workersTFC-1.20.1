package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.Translatable;
import com.talhanation.workers.entities.AbstractWorkerEntity;
import com.talhanation.workers.entities.ai.DepositItemsInChestGoal;
import com.talhanation.workers.entities.*;
import net.dries007.tfc.common.blocks.GroundcoverBlockType;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.capabilities.food.FoodCapability;

import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.Powder;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.EnumSet;
import java.util.Set;

import net.minecraftforge.common.data.ForgeItemTagsProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
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

    //FOOD CHANGES
    private static final Set<Item> RAW_FOOD = ImmutableSet.of(
            TFCItems.FOOD.get(Food.HORSE_MEAT).get(),TFCItems.FOOD.get(Food.BEAR).get(),TFCItems.FOOD.get(Food.FOX).get(),TFCItems.FOOD.get(Food.PORK).get(),TFCItems.FOOD.get(Food.VENISON).get(),
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

    private boolean hasEnoughofTag(ForgeItemTagsProvider Tag) {

        return true;
    }


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
                            if(worker.getOwner() != null && noSpaceInvMessage) {
                                worker.tellPlayer(worker.getOwner(), Translatable.TEXT_NO_SPACE_INV);
                                noSpaceInvMessage = false;
                            }
                        }

                        this.reequipMainTool();
                        this.reequipSecondTool();

                        this.worker.updateNeedsTool();

                        if(this.worker instanceof MinerEntity){
                            if(!hasEnoughOfItem(TFCItems.TORCH.get(), 16)) this.getItemFromChest(TFCItems.TORCH.get());
                        }
                        //TODO: ADD fisherman takes boat
                        if(this.worker instanceof FarmerEntity){
                            if(!hasEnoughOfItem(Items.BONE_MEAL, 32)) this.getItemFromChest(Items.BONE_MEAL);
                            if(!hasEnoughOfItem(Items.STICK, 32)) this.getItemFromChest(Items.STICK);
                            if(!hasEnoughOfItem(TFCItems.POWDERS.get(Powder.WOOD_ASH).get(), 32)) this.getItemFromChest(TFCItems.POWDERS.get(Powder.WOOD_ASH).get());
                            if(!hasEnoughOfItem(TFCItems.POWDERS.get(Powder.SYLVITE).get(), 32)) this.getItemFromChest(TFCItems.POWDERS.get(Powder.SYLVITE).get());
                            if(!hasEnoughOfItem(TFCItems.POWDERS.get(Powder.SALTPETER).get(), 32)) this.getItemFromChest(TFCItems.POWDERS.get(Powder.SALTPETER).get());
                            if(!hasEnoughOfItem(TFCItems.COMPOST.get(), 32)) this.getItemFromChest(TFCItems.COMPOST.get());
                            if(!hasEnoughOfItem(TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem(), 32)) this.getItemFromChest(TFCBlocks.GROUNDCOVER.get(GroundcoverBlockType.GUANO).get().asItem());
                        }

                        if(this.worker instanceof ChickenFarmerEntity chickenFarmer){
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.OAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.OAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RYE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RYE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RICE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RICE_GRAIN).get());
                        }

                        if(this.worker instanceof ShepherdEntity){
                            if(!hasEnoughOfItem(Items.WHEAT, 32)) this.getItemFromChest(Items.WHEAT);
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.OAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.OAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RYE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RYE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RICE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RICE_GRAIN).get());
                        }

                        if(this.worker instanceof SwineherdEntity){
                            if(!hasEnoughOfItem(Items.CARROT, 32)) this.getItemFromChest(Items.CARROT);
                            if(!hasEnoughOfItem(Items.ROTTEN_FLESH, 32)) this.getItemFromChest(Items.ROTTEN_FLESH);
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.OAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.OAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RYE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RYE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RICE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RICE_GRAIN).get());
                        }

                        if(this.worker instanceof CattleFarmerEntity){
                            if(!hasEnoughOfItem(Items.MILK_BUCKET, 3)) this.getItemFromChest(Items.BUCKET);
                            if(!hasEnoughOfItem(TFCItems.WOODEN_BUCKET.get(), 3)) this.getItemFromChest(TFCItems.WOODEN_BUCKET.get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.OAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.OAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.WHEAT_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.MAIZE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RYE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RYE_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.BARLEY_GRAIN).get());
                            if(!hasEnoughOfItem(TFCItems.FOOD.get(Food.RICE_GRAIN).get(), 32)) this.getItemFromChest(TFCItems.FOOD.get(Food.RICE_GRAIN).get());
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
            worker.updateNeedsTool();
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
    private void depositItems(Container container) {
        SimpleContainer inventory = worker.getInventory();

        if (this.isContainerFull(container)) {

            if(worker.getOwner() != null && messageChestFull) {
                this.worker.tellPlayer(worker.getOwner(), Translatable.TEXT_CHEST_FULL);
                messageChestFull = false;
            }
            return;
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            // This avoids depositing items such as tools, food,
            // or anything the workers wouldn't pick up while they're working.
            // It also avoids depositing items that the worker needs to continue working.
            if (stack.is(Items.AIR) || stack.isEmpty() || !worker.wantsToPickUp(stack) || (worker.wantsToKeep(stack) && getAmountOfItem(stack.getItem()) <= 64)) {
                continue;
            }
            // Attempt to deposit the stack in the container, keep the remainder
            ItemStack remainder = this.deposit(stack, container);
            inventory.setItem(i, remainder);


            //Main.LOGGER.debug("Stored {} x {}", stack.getCount() - remainder.getCount(), stack.getDisplayName().getString());
            //Main.LOGGER.debug("Kept {} x {}", remainder.getCount(), stack.getDisplayName().getString());
        }
    }

    @Shadow
    private boolean isContainerFull(Container c){
        return 1 == 1;
    }

    @Shadow
    private int getAmountOfItem(Item i){
        return 64;
    }

    @Shadow
    private ItemStack deposit(ItemStack a, Container c){
        return a;
    }

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

package com.ckeeze.workerstfc.mixin;

import com.google.common.collect.ImmutableSet;
import com.talhanation.workers.entities.AbstractInventoryEntity;
import com.talhanation.workers.entities.MinerEntity;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"removal", "unused"})
@Mixin(MinerEntity.class)
public abstract class MinerEntityMixin extends AbstractInventoryEntity {

    public MinerEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private static final Set<Item> BLACKLIST = ImmutableSet.of(Items.ROTTEN_FLESH);

    @Unique
    private static final Set<Block> IGNORING_BLOCKS = ImmutableSet.of(Blocks.CAVE_AIR, Blocks.AIR, Blocks.TORCH,
            Blocks.WALL_TORCH, Blocks.SOUL_WALL_TORCH, Blocks.REDSTONE_WIRE, Blocks.CAMPFIRE, Blocks.CAKE,
            Blocks.ACACIA_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.DARK_OAK_SIGN, Blocks.JUNGLE_SIGN,
            Blocks.OAK_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN,
            Blocks.DARK_OAK_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SOUL_LANTERN,
            Blocks.LANTERN, Blocks.DETECTOR_RAIL, Blocks.RAIL, Blocks.WATER
            //Beams
            ,TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.MANGROVE).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.HORIZONTAL_SUPPORT).get()

            ,TFCBlocks.WOODS.get(Wood.ACACIA).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.BLACKWOOD).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.KAPOK).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.MANGROVE).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.PALM).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.SYCAMORE).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.SEQUOIA).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.ROSEWOOD).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.VERTICAL_SUPPORT).get()
            ,TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.VERTICAL_SUPPORT).get()
    );

    public void setEquipment() {
        ItemStack initialTool = new ItemStack(TFCItems.METAL_ITEMS.get(Metal.Default.COPPER).get(Metal.ItemType.PICKAXE).get());
        this.updateInventory(0, initialTool);
        this.equipTool(initialTool);

        ItemStack initialTool2 = new ItemStack(TFCItems.ROCK_TOOLS.get(RockCategory.IGNEOUS_EXTRUSIVE).get(RockCategory.ItemType.SHOVEL).get());
        this.updateInventory(1, initialTool2);
    }

    public int getFarmedItemsDepositAmount(){
        return 128;
    }

    public List<Item> inventoryInputHelp() {
        return Arrays.asList(TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.PICKAXE).get(),
                TFCItems.METAL_ITEMS.get(Metal.Default.WROUGHT_IRON).get(Metal.ItemType.SHOVEL).get(), TFCItems.TORCH.get());
    }

    public boolean wantsToPickUp(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (!BLACKLIST.contains(item));
    }

}

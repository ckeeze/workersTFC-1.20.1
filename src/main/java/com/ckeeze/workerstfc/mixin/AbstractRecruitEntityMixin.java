package com.ckeeze.workerstfc.mixin;

import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.dries007.tfc.common.capabilities.food.FoodCapability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractRecruitEntity.class)
public class AbstractRecruitEntityMixin {

    public boolean canEatItemStack(ItemStack stack){
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if(RecruitsServerConfig.FoodBlackList.get().contains(location.toString())){
            return false;
        }
        if(FoodCapability.isRotten(stack)){
            return false;
        }
        return stack.isEdible();
    }
}

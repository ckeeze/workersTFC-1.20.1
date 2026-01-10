package com.ckeeze.workerstfc.mixin;

import com.talhanation.recruits.TeamEvents;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.dries007.tfc.common.capabilities.food.FoodCapability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unused")
@Mixin(AbstractRecruitEntity.class)
public abstract class AbstractRecruitEntityMixin extends AbstractInventoryEntity {

    @Shadow @Nullable public abstract Player getOwner();

    @Shadow public abstract boolean isOwned();

    public AbstractRecruitEntityMixin(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 6;
        this.navigation = this.createNavigation(world);
        this.setMaxUpStep(1.0F);
    }

    /**
     * @author Ckeeze
     * @reason Allowing modded food to be eaten
     */
    @Overwrite(remap = false)
    public boolean canEatItemStack(ItemStack stack){
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if(FoodCapability.isRotten(stack) || stack.is(Items.ROTTEN_FLESH) || RecruitsServerConfig.FoodBlackList.get().contains(location.toString())){
            return false;
        }
        return stack.isEdible();
    }

    @Inject(at = @At("HEAD"), method = "disband", remap = false)
    protected void disband(Player player, boolean keepTeam, boolean increaseCost, CallbackInfo ci){
        if (this.getTeam() != null) {
            Objects.requireNonNull(TeamEvents.recruitsTeamManager.getTeamByStringID(this.getTeam().getName())).addNPCs(-1);
        }
    }

    @Inject(at = @At("HEAD"), method = "die")
    public void die(DamageSource dmg, CallbackInfo ci) {
        if (this.isOwned() && this.getTeam() != null) {
            Objects.requireNonNull(TeamEvents.recruitsTeamManager.getTeamByStringID(Objects.requireNonNull(Objects.requireNonNull(this.getOwner()).getTeam()).getName())).addNPCs(-1);
        }
        if (!this.isOwned() && this.getTeam() != null){
            Objects.requireNonNull(TeamEvents.recruitsTeamManager.getTeamByStringID(Objects.requireNonNull(Objects.requireNonNull(this.getTeam()).getName()))).addNPCs(1);
        }
    }
}

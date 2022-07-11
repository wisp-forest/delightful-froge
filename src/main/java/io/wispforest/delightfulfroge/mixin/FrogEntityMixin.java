package io.wispforest.delightfulfroge.mixin;

import io.wispforest.delightfulfroge.DelightfulFroge;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrogEntity.class)
public abstract class FrogEntityMixin extends LivingEntity {

    protected FrogEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract void setVariant(FrogVariant variant);

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/FrogBrain;coolDownLongJump(Lnet/minecraft/entity/passive/FrogEntity;Lnet/minecraft/util/math/random/Random;)V"))
    private void injectFroge(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (world.getBiome(this.getBlockPos()).isIn(DelightfulFroge.FROGE_PLACES)) {
            this.setVariant(DelightfulFroge.FROGE);
            return;
        }

        final var chance = serverWorld.getGameRules().getInt(DelightfulFroge.FROGE_CHANCE);
        if (serverWorld.random.nextBetween(0, 100) > chance) return;

        this.setVariant(DelightfulFroge.FROGE);
    }

}

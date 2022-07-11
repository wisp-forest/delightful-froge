package io.wispforest.delightfulfroge;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;

public class DelightfulFroge implements ModInitializer {

    public static final FrogVariant FROGE = new FrogVariant(DelightfulFroge.id("textures/entity/frog/froge.png"));

    public static final TagKey<Biome> FROGE_PLACES = TagKey.of(Registry.BIOME_KEY, DelightfulFroge.id("froge_places"));

    public static final GameRules.Key<GameRules.IntRule> FROGE_CHANCE = GameRuleRegistry.register(
            "frogeChance",
            GameRules.Category.MOBS,
            GameRuleFactory.createIntRule(1, 0, 100)
    );

    @Override
    public void onInitialize() {
        Registry.register(
                Registry.FROG_VARIANT,
                DelightfulFroge.id("froge"),
                FROGE
        );
    }

    public static Identifier id(String path) {
        return new Identifier("delightful", path);
    }
}

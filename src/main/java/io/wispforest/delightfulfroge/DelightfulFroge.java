package io.wispforest.delightfulfroge;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.TypeSpecificPredicate;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;

public class DelightfulFroge implements ModInitializer {

    public static final FrogVariant FROGE = new FrogVariant(DelightfulFroge.id("textures/entity/frog/froge.png"));
    public static final Block FROGELIGHT = new PillarBlock(FabricBlockSettings.copyOf(Blocks.OCHRE_FROGLIGHT));

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

        Registry.register(
                Registry.BLOCK,
                DelightfulFroge.id("frogelight"),
                FROGELIGHT
        );
        Registry.register(
                Registry.ITEM,
                DelightfulFroge.id("frogelight"),
                new BlockItem(FROGELIGHT, new Item.Settings().group(ItemGroup.DECORATIONS))
        );

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!id.equals(EntityType.MAGMA_CUBE.getLootTableId())) return;
            tableBuilder.pool(
                    LootPool.builder().with(ItemEntry.builder(FROGELIGHT).conditionally(
                            DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                    .sourceEntity(EntityPredicate.Builder.create().typeSpecific(TypeSpecificPredicate.frog(FROGE))))
                    )).build()
            );
        });
    }

    public static Identifier id(String path) {
        return new Identifier("delightful", path);
    }
}

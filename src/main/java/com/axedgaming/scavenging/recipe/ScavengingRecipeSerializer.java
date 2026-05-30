package com.axedgaming.scavenging.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class ScavengingRecipeSerializer implements RecipeSerializer<ScavengingRecipe> {

    public static final MapCodec<ScavengingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.optionalFieldOf("tool").forGetter(ScavengingRecipe::tool),
            Codec.BOOL.optionalFieldOf("requires_empty_hand", false).forGetter(ScavengingRecipe::requiresEmptyHand),
            Codec.BOOL.optionalFieldOf("damage_tool", false).forGetter(ScavengingRecipe::damageTool),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(ScavengingRecipe::block),
            ScavengingLoot.CODEC.listOf().fieldOf("outputs").forGetter(ScavengingRecipe::outputs)
    ).apply(instance, ScavengingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ScavengingRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC), ScavengingRecipe::tool,
            ByteBufCodecs.BOOL, ScavengingRecipe::requiresEmptyHand,
            ByteBufCodecs.BOOL, ScavengingRecipe::damageTool,
            ByteBufCodecs.registry(Registries.BLOCK), ScavengingRecipe::block,
            ScavengingLoot.STREAM_CODEC.apply(ByteBufCodecs.list()), ScavengingRecipe::outputs,
            ScavengingRecipe::new
    );

    @Override
    public MapCodec<ScavengingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ScavengingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
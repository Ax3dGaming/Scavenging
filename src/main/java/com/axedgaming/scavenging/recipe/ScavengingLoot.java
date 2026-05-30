package com.axedgaming.scavenging.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record ScavengingLoot(ItemStack item, float chance) {

    public static final Codec<ScavengingLoot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(ScavengingLoot::item),
            Codec.FLOAT.fieldOf("chance").forGetter(ScavengingLoot::chance)
    ).apply(instance, ScavengingLoot::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ScavengingLoot> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, ScavengingLoot::item,
            ByteBufCodecs.FLOAT, ScavengingLoot::chance,
            ScavengingLoot::new
    );
}
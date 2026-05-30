package com.axedgaming.scavenging.registry;

import com.axedgaming.scavenging.Scavenging;
import com.axedgaming.scavenging.recipe.ScavengingRecipe;
import com.axedgaming.scavenging.recipe.ScavengingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Scavenging.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Scavenging.MODID);

    public static final Supplier<RecipeType<ScavengingRecipe>> SCAVENGING_TYPE =
            RECIPE_TYPES.register("scavenging",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Scavenging.MODID, "scavenging")));

    public static final Supplier<RecipeSerializer<ScavengingRecipe>> SCAVENGING_SERIALIZER =
            RECIPE_SERIALIZERS.register("scavenging", ScavengingRecipeSerializer::new);
}
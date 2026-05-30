package com.axedgaming.scavenging.recipe;

import com.axedgaming.scavenging.registry.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public record ScavengingRecipe(
        Optional<Ingredient> tool,
        boolean requiresEmptyHand,
        boolean damageTool,
        Block block,
        List<ScavengingLoot> outputs
) implements Recipe<ScavengingInput> {

    @Override
    public boolean matches(ScavengingInput input, Level level) {
        if (!input.blockState().is(block)) {
            return false;
        }

        if (requiresEmptyHand) {
            return input.tool().isEmpty();
        }

        return tool.isPresent() && tool.get().test(input.tool());
    }

    @Override
    public ItemStack assemble(ScavengingInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.getFirst().item();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        tool.ifPresent(ingredients::add);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SCAVENGING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SCAVENGING_TYPE.get();
    }
}
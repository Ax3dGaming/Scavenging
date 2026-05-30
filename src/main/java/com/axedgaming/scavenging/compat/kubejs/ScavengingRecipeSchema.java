package com.axedgaming.scavenging.compat.kubejs;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BlockComponent;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.CustomObjectRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface ScavengingRecipeSchema {

    RecipeKey<Ingredient> TOOL = IngredientComponent.INGREDIENT.instance()
            .inputKey("tool")
            .defaultOptional();

    RecipeKey<Boolean> REQUIRES_EMPTY_HAND = BooleanComponent.BOOLEAN
            .otherKey("requires_empty_hand")
            .optional(false);

    RecipeKey<Boolean> DAMAGE_TOOL = BooleanComponent.BOOLEAN
            .otherKey("damage_tool")
            .optional(false);

    RecipeKey<Block> BLOCK = BlockComponent.BLOCK
            .inputKey("block");

    CustomObjectRecipeComponent OUTPUT = new CustomObjectRecipeComponent(List.of(
            new CustomObjectRecipeComponent.Key(
                    "item",
                    ItemStackComponent.ITEM_STACK.instance()
            ),
            new CustomObjectRecipeComponent.Key(
                    "chance",
                    NumberComponent.FLOAT
            )
    ));

    RecipeKey<List<List<CustomObjectRecipeComponent.Value>>> OUTPUTS = OUTPUT
            .asList()
            .outputKey("outputs");

    RecipeSchema SCHEMA = new RecipeSchema(
            BLOCK,
            OUTPUTS,
            TOOL,
            REQUIRES_EMPTY_HAND,
            DAMAGE_TOOL
    );
}
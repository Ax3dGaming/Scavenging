package com.axedgaming.scavenging.compat.jei;

import com.axedgaming.scavenging.recipe.ScavengingLoot;
import com.axedgaming.scavenging.recipe.ScavengingRecipe;
import com.axedgaming.scavenging.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ScavengingRecipeCategory implements IRecipeCategory<ScavengingRecipe> {

    private final IDrawableStatic background;
    private final IDrawable icon;

    public ScavengingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(150, 70);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(net.minecraft.world.item.Items.STONE_SHOVEL));
    }

    @Override
    public mezz.jei.api.recipe.RecipeType<ScavengingRecipe> getRecipeType() {
        return ScavengingJeiPlugin.SCAVENGING_JEI_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Scavenging");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ScavengingRecipe recipe, IFocusGroup focuses) {
        if (recipe.requiresEmptyHand()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 10, 25)
                    .addItemStack(new ItemStack(ModItems.EMPTY_HAND_ICON.get()));
        } else {
            recipe.tool().ifPresent(tool ->
                    builder.addSlot(RecipeIngredientRole.INPUT, 10, 25)
                            .addIngredients(tool)
            );
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 40, 25)
                .addItemStack(new ItemStack(recipe.block()));

        int x = 85;
        int y = 15;

        for (ScavengingLoot loot : recipe.outputs()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, y)
                    .addItemStack(loot.item());

            x += 22;

            if (x > 130) {
                x = 85;
                y += 32;
            }
        }
    }

    @Override
    public void draw(ScavengingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawString(
                net.minecraft.client.Minecraft.getInstance().font,
                "+",
                29,
                29,
                0x404040,
                false
        );

        guiGraphics.drawString(
                net.minecraft.client.Minecraft.getInstance().font,
                "→",
                65,
                29,
                0x404040,
                false
        );

        int x = 85;
        int y = 35;

        for (ScavengingLoot loot : recipe.outputs()) {
            String chanceText = Math.round(loot.chance() * 100) + "%";

            guiGraphics.drawString(
                    net.minecraft.client.Minecraft.getInstance().font,
                    chanceText,
                    x,
                    y,
                    0x404040,
                    false
            );

            x += 22;

            if (x > 130) {
                x = 85;
                y += 32;
            }
        }
    }
}
package com.axedgaming.scavenging.compat.jei;

import com.axedgaming.scavenging.Scavenging;
import com.axedgaming.scavenging.recipe.ScavengingRecipe;
import com.axedgaming.scavenging.registry.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@JeiPlugin
public class ScavengingJeiPlugin implements IModPlugin {

    public static final RecipeType<ScavengingRecipe> SCAVENGING_JEI_TYPE =
            RecipeType.create(Scavenging.MODID, "scavenging", ScavengingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Scavenging.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ScavengingRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) {
            return;
        }

        List<ScavengingRecipe> recipes = minecraft.level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.SCAVENGING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();

        registration.addRecipes(SCAVENGING_JEI_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(Items.STONE_SHOVEL),
                SCAVENGING_JEI_TYPE
        );
    }
}
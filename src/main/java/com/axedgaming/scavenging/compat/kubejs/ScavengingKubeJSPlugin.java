package com.axedgaming.scavenging.compat.kubejs;

import com.axedgaming.scavenging.Scavenging;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import net.minecraft.resources.ResourceLocation;

public class ScavengingKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(
                ResourceLocation.fromNamespaceAndPath(Scavenging.MODID, "scavenging"),
                ScavengingRecipeSchema.SCHEMA
        );
    }
}
package com.axedgaming.scavenging.event;

import com.axedgaming.scavenging.Scavenging;
import com.axedgaming.scavenging.recipe.ScavengingInput;
import com.axedgaming.scavenging.recipe.ScavengingLoot;
import com.axedgaming.scavenging.recipe.ScavengingRecipe;
import com.axedgaming.scavenging.registry.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Scavenging.MODID)
public class ScavengingEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        Level level = event.getLevel();

        if (level.isClientSide()) {
            return;
        }

        ItemStack tool = event.getEntity().getMainHandItem();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);

        ScavengingInput input = new ScavengingInput(tool, blockState);

        level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.SCAVENGING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .filter(recipe -> recipe.matches(input, level))
                .findFirst()
                .ifPresent(recipe -> {
                    runScavengingRecipe((ServerLevel) level, pos, recipe);

                    if (recipe.damageTool() && !tool.isEmpty() && tool.isDamageableItem()) {
                        tool.hurtAndBreak(1, event.getEntity(), EquipmentSlot.MAINHAND);
                    }

                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                });
    }

    private static void runScavengingRecipe(ServerLevel level, BlockPos pos, ScavengingRecipe recipe) {
        for (ScavengingLoot loot : recipe.outputs()) {
            if (level.random.nextFloat() < loot.chance()) {
                ItemStack stack = loot.item().copy();

                if (stack.isEmpty()) {
                    continue;
                }

                ItemEntity itemEntity = new ItemEntity(
                        level,
                        pos.getX() + 0.5,
                        pos.getY() + 1.0,
                        pos.getZ() + 0.5,
                        stack
                );

                itemEntity.setPickUpDelay(10);
                level.addFreshEntity(itemEntity);
            }
        }
    }
}
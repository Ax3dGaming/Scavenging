package com.axedgaming.scavenging.registry;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.axedgaming.scavenging.Scavenging.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final Supplier<Item> EMPTY_HAND_ICON = ITEMS.register(
            "empty_hand_icon",
            () -> new Item(new Item.Properties())
    );
}

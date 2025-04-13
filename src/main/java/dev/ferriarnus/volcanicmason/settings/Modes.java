package dev.ferriarnus.volcanicmason.settings;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum Modes {
    COBBLESTONE(Items.COBBLESTONE, Items.AIR),
    STONE(Items.STONE, Items.AIR),
    OBSIDIAN(Items.OBSIDIAN, Items.LAVA_BUCKET),
    BASALT(Items.BASALT, Items.AIR);

    private final ItemStack stack;
    private final ItemStack fluidStack;

    Modes(Item main, Item fluid) {
        this.stack = new ItemStack(main);
        this.fluidStack = new ItemStack(fluid);
    }

    public ItemStack getItem() {
        return this.stack;
    }

    public ItemStack getFluidStack() {
        return fluidStack;
    }
}

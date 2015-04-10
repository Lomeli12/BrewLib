package net.lomeli.brewlib.api;

import net.minecraft.item.ItemStack;

public interface IBrewRecipe {
    boolean matches(ItemStack input, ItemStack ingredient);

    boolean matchesInput(ItemStack input);

    boolean matchesIngredient(ItemStack ingredient);

    Object getInput();

    Object getIngredient();

    ItemStack getOutput();
}

package net.lomeli.brewlib.api;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class BrewOreRecipe implements IBrewRecipe {
    private Object[] inputs;
    private ItemStack output;

    public BrewOreRecipe(ItemStack result, Object input, Object ingredient) {
        inputs = new Object[2];
        output = result;
        inputs[0] = getItemOrOreName(input);
        inputs[1] = getItemOrOreName(ingredient);
    }

    private Object getItemOrOreName(Object obj) {
        Object out = null;
        if (obj != null) {
            if (obj instanceof ItemStack)
                out = obj;
            else if (obj instanceof Item)
                out = new ItemStack((Item) obj);
            else if (obj instanceof Block)
                out = new ItemStack((Block) obj);
            else if (obj instanceof String)
                out = OreDictionary.getOres((String) obj);
            else {
                String ret = "Invalid brew item: " + obj.toString();
                throw new RuntimeException(ret);
            }
        } else
            throw new RuntimeException("Input cannot be null!");
        return out;
    }

    @Override
    public boolean matches(ItemStack input, ItemStack ingredient) {
        return matchesInput(input) && matchesIngredient(ingredient);
    }

    @Override
    public boolean matchesInput(ItemStack input) {
        boolean flag = false;
        Object in = inputs[0];
        if (in instanceof ItemStack)
            flag = OreDictionary.itemMatches((ItemStack) in, input, false);
        else if (in instanceof List) {
            Iterator<ItemStack> it = ((List) in).iterator();
            while (it.hasNext() && !flag) {
                ItemStack item = it.next();
                flag = OreDictionary.itemMatches(item, input, false);
            }
        }
        return flag;
    }

    @Override
    public boolean matchesIngredient(ItemStack ingredient) {
        boolean flag = false;
        Object in = inputs[1];
        if (in instanceof ItemStack)
            flag = OreDictionary.itemMatches((ItemStack) in, ingredient, false);
        else if (in instanceof List) {
            Iterator<ItemStack> it = ((List) in).iterator();
            while (it.hasNext() && !flag) {
                ItemStack item = it.next();
                flag = OreDictionary.itemMatches(item, ingredient, false);
            }
        }
        return flag;
    }

    @Override
    public Object getInput() {
        return inputs[0];
    }

    @Override
    public Object getIngredient() {
        return inputs[1];
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }
}

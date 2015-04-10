package net.lomeli.brewlib.api;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PotionRegistry {
    private ArrayList<IBrewRecipe> recipeList;

    public PotionRegistry() {
        recipeList = new ArrayList<>();
        initVanillaBrews();
    }

    public void registerBrewRecipe(IBrewRecipe recipe) {
        if (recipe == null) throw new RuntimeException("Cannot register null recipe!");
        recipeList.add(recipe);
    }

    public void registerBrewRecipe(ItemStack result, Object input, Object ingredient) {
        registerBrewRecipe(new BrewOreRecipe(result, input, ingredient));
    }

    public boolean isPotionIngredient(ItemStack ingredient) {
        IBrewRecipe recipe = null;
        Iterator<IBrewRecipe> it = recipeList.iterator();
        while (it.hasNext() && recipe == null) {
            IBrewRecipe rep = it.next();
            if (rep.matchesIngredient(ingredient))
                recipe = rep;
        }
        return recipe != null;
    }

    public boolean isValidInput(ItemStack input) {
        IBrewRecipe recipe = null;
        Iterator<IBrewRecipe> it = recipeList.iterator();
        while (it.hasNext() && recipe == null) {
            IBrewRecipe rep = it.next();
            if (rep.matchesInput(input))
                recipe = rep;
        }
        return input.getItem() == Items.glass_bottle || recipe != null;
    }

    public IBrewRecipe getRecipeForInput(ItemStack input, ItemStack ingredient) {
        IBrewRecipe recipe = null;
        Iterator<IBrewRecipe> it = recipeList.iterator();
        while (it.hasNext() && recipe == null) {
            IBrewRecipe rep = it.next();
            if (rep.matches(input, ingredient))
                recipe = rep;
        }
        return recipe;
    }

    // Very tedious and boring stuff, hooray!
    private void initVanillaBrews() {
        // Base Potions
        registerBrewRecipe(awkwardPotion, waterBottle, Items.nether_wart);
        registerBrewRecipe(thickPotion, waterBottle, "dustGlowstone");
        registerBrewRecipe(mundanePlus, waterBottle, "dustRedstone");
        registerBrewRecipe(mundanePotion, waterBottle, Items.speckled_melon);
        registerBrewRecipe(mundanePotion, waterBottle, Items.ghast_tear);
        registerBrewRecipe(mundanePotion, waterBottle, Items.rabbit_foot);
        registerBrewRecipe(mundanePotion, waterBottle, Items.blaze_powder);
        registerBrewRecipe(mundanePotion, waterBottle, Items.spider_eye);
        registerBrewRecipe(mundanePotion, waterBottle, Items.sugar);
        registerBrewRecipe(mundanePotion, waterBottle, Items.magma_cream);

        // Basic Potions
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8200), waterBottle, Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8201), awkwardPotion, Items.blaze_powder);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8193), awkwardPotion, Items.ghast_tear);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8196), awkwardPotion, Items.spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8197), awkwardPotion, Items.speckled_melon);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8205), awkwardPotion, new ItemStack(Items.fish, 1, 3));
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8194), awkwardPotion, Items.sugar);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8195), awkwardPotion, Items.magma_cream);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8203), awkwardPotion, Items.rabbit_foot);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8198), awkwardPotion, Items.golden_carrot);

        // Lasting Potions
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8262), new ItemStack(Items.potionitem, 1, 8198), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8270), new ItemStack(Items.potionitem, 1, 8206), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8267), new ItemStack(Items.potionitem, 1, 8203), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8259), new ItemStack(Items.potionitem, 1, 8195), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8266), new ItemStack(Items.potionitem, 1, 8202), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8258), new ItemStack(Items.potionitem, 1, 8194), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8269), new ItemStack(Items.potionitem, 1, 8205), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8260), new ItemStack(Items.potionitem, 1, 8196), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8257), new ItemStack(Items.potionitem, 1, 8193), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8265), new ItemStack(Items.potionitem, 1, 8201), "dustRedstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8264), new ItemStack(Items.potionitem, 1, 8200), "dustRedstone");

        // Effective Potions
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8235), new ItemStack(Items.potionitem, 1, 8203), "dustGlowstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8226), new ItemStack(Items.potionitem, 1, 8194), "dustGlowstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8229), new ItemStack(Items.potionitem, 1, 8197), "dustGlowstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8236), new ItemStack(Items.potionitem, 1, 8204), "dustGlowstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8228), new ItemStack(Items.potionitem, 1, 8196), "dustGlowstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8225), new ItemStack(Items.potionitem, 1, 8193), "dustGlowstone");
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8233), new ItemStack(Items.potionitem, 1, 8201), "dustGlowstone");

        // Corrupted Potions
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8206), new ItemStack(Items.potionitem, 1, 8198), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8202), new ItemStack(Items.potionitem, 1, 8195), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8202), new ItemStack(Items.potionitem, 1, 8194), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8202), new ItemStack(Items.potionitem, 1, 8203), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8204), new ItemStack(Items.potionitem, 1, 8205), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8204), new ItemStack(Items.potionitem, 1, 8197), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8204), new ItemStack(Items.potionitem, 1, 8196), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8270), new ItemStack(Items.potionitem, 1, 8262), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8266), new ItemStack(Items.potionitem, 1, 8259), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8266), new ItemStack(Items.potionitem, 1, 8258), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8236), new ItemStack(Items.potionitem, 1, 8229), Items.fermented_spider_eye);
        registerBrewRecipe(new ItemStack(Items.potionitem, 1, 8236), new ItemStack(Items.potionitem, 1, 8260), Items.fermented_spider_eye);

        // Splash Potions
        // Ugh, forgot about splash potions. I'll do it later!
    }

    // ----------------------------- INSTANCE STUFF -------------------------------------------
    private static PotionRegistry INSTANCE;

    public static ItemStack waterBottle = new ItemStack(Items.potionitem);
    public static ItemStack awkwardPotion = new ItemStack(Items.potionitem, 1, 16);
    public static ItemStack mundanePotion = new ItemStack(Items.potionitem, 1, 8192);
    public static ItemStack thickPotion = new ItemStack(Items.potionitem, 1, 32);
    public static ItemStack mundanePlus = new ItemStack(Items.potionitem, 1, 64);

    public static PotionRegistry getInstance() {
        if (INSTANCE == null) INSTANCE = new PotionRegistry();
        return INSTANCE;
    }
}

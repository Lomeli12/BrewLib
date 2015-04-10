package net.lomeli.brewlib.core;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.lomeli.brewlib.BrewLib;
import net.lomeli.brewlib.blocks.BlockAdvBrewStand;
import net.lomeli.brewlib.blocks.TileBrewingStand;

public class Proxy {
    public void preInit() {
        BrewLib.brewingStand = new BlockAdvBrewStand();

        GameRegistry.registerBlock(BrewLib.brewingStand, "brewingStand");
    }

    public void init() {
        GameRegistry.registerTileEntity(TileBrewingStand.class, BrewLib.MOD_ID + ".brewingStand");

        NetworkRegistry.INSTANCE.registerGuiHandler(BrewLib.instance, new GuiHandler());
    }

    public void postInit() {
        GameRegistry.addShapelessRecipe(new ItemStack(BrewLib.brewingStand), Items.brewing_stand);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.brewing_stand), BrewLib.brewingStand);
    }
}

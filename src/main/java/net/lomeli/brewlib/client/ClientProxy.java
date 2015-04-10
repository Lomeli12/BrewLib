package net.lomeli.brewlib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.Loader;

import net.lomeli.brewlib.BrewLib;
import net.lomeli.brewlib.client.gui.NEIHandler;
import net.lomeli.brewlib.core.Proxy;

public class ClientProxy extends Proxy {
    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(BrewLib.brewingStand), 0, new ModelResourceLocation("brewlib:brewingStand", "inventory"));
    }

    @Override
    public void postInit() {
        super.postInit();
        if (Loader.isModLoaded("NotEnoughItems"))
            NEIHandler.loadRecipeHandlers();
    }
}

package net.lomeli.brewlib;

import net.minecraft.block.Block;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.brewlib.core.Proxy;

@Mod(modid = BrewLib.MOD_ID, name = BrewLib.MOD_NAME, version = BrewLib.VERSION)
public class BrewLib {
    public static final String MOD_ID = "brewlib";
    public static final String MOD_NAME = "BrewLib";
    public static final String VERSION = "1.0.0";

    public static Block brewingStand;

    @Mod.Instance(MOD_ID)
    public static BrewLib instance;

    @SidedProxy(serverSide = "net.lomeli.brewlib.core.Proxy", clientSide = "net.lomeli.brewlib.client.ClientProxy")
    public static Proxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}

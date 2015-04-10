package net.lomeli.brewlib.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;

import net.lomeli.brewlib.blocks.TileBrewingStand;
import net.lomeli.brewlib.client.gui.GuiBrewingStand;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (tile instanceof TileBrewingStand)
            return new ContainerBrewingStand(player.inventory, (TileBrewingStand) tile);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (tile instanceof TileBrewingStand)
            return new GuiBrewingStand(player.inventory, (TileBrewingStand) tile);
        return null;
    }
}

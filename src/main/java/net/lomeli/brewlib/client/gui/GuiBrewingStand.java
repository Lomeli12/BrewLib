package net.lomeli.brewlib.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import net.lomeli.brewlib.blocks.TileBrewingStand;
import net.lomeli.brewlib.core.ContainerBrewingStand;

public class GuiBrewingStand extends GuiContainer {
    private static final ResourceLocation brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
    private final InventoryPlayer playerInventory;
    private final TileBrewingStand tile;

    public GuiBrewingStand(InventoryPlayer playerInventory, TileBrewingStand tile) {
        super(new ContainerBrewingStand(playerInventory, tile));
        this.playerInventory = playerInventory;
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.tile.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(brewingStandGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1 = this.tile.getField(0);

        if (i1 > 0) {
            int j1 = (int) (28.0F * (1.0F - (float) i1 / 400.0F));

            if (j1 > 0)
                this.drawTexturedModalRect(k + 97, l + 16, 176, 0, 9, j1);

            int k1 = i1 / 2 % 7;

            switch (k1) {
                case 0:
                    j1 = 29;
                    break;
                case 1:
                    j1 = 24;
                    break;
                case 2:
                    j1 = 20;
                    break;
                case 3:
                    j1 = 16;
                    break;
                case 4:
                    j1 = 11;
                    break;
                case 5:
                    j1 = 6;
                    break;
                case 6:
                    j1 = 0;
            }

            if (j1 > 0)
                this.drawTexturedModalRect(k + 65, l + 14 + 29 - j1, 185, 29 - j1, 12, j1);
        }
    }
}

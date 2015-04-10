package net.lomeli.brewlib.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.brewlib.api.PotionRegistry;
import net.lomeli.brewlib.blocks.TileBrewingStand;

public class ContainerBrewingStand extends Container {
    private TileBrewingStand tile;
    private final Slot theSlot;
    private int brewTime;

    public ContainerBrewingStand(InventoryPlayer playerInv, TileBrewingStand tile) {
        this.tile = tile;
        this.addSlotToContainer(new ContainerBrewingStand.Potion(playerInv.player, tile, 0, 56, 46));
        this.addSlotToContainer(new ContainerBrewingStand.Potion(playerInv.player, tile, 1, 79, 53));
        this.addSlotToContainer(new ContainerBrewingStand.Potion(playerInv.player, tile, 2, 102, 46));
        this.theSlot = this.addSlotToContainer(new ContainerBrewingStand.Ingredient(tile, 3, 79, 17));
        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting listener) {
        super.addCraftingToCrafters(listener);
        listener.func_175173_a(this, this.tile);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.brewTime != this.tile.getField(0))
                icrafting.sendProgressBarUpdate(this, 0, this.tile.getField(0));
        }

        this.brewTime = this.tile.getField(0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        this.tile.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if ((index < 0 || index > 2) && index != 3) {
                if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 3, 4, false))
                        return null;
                } else if (ContainerBrewingStand.Potion.canHoldPotion(itemstack)) {
                    if (!this.mergeItemStack(itemstack1, 0, 3, false))
                        return null;
                } else if (index >= 4 && index < 31) {
                    if (!this.mergeItemStack(itemstack1, 31, 40, false))
                        return null;
                } else if (index >= 31 && index < 40) {
                    if (!this.mergeItemStack(itemstack1, 4, 31, false))
                        return null;
                } else if (!this.mergeItemStack(itemstack1, 4, 40, false))
                    return null;
            } else {
                if (!this.mergeItemStack(itemstack1, 4, 40, true))
                    return null;

                slot.onSlotChange(itemstack1, itemstack);
            }

            if (itemstack1.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (itemstack1.stackSize == itemstack.stackSize)
                return null;

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    static class Ingredient extends Slot {
        public Ingredient(IInventory inventory, int slotNum, int x, int y) {
            super(inventory, slotNum, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack != null ? PotionRegistry.getInstance().isPotionIngredient(stack) : false;
        }

        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    }

    static class Potion extends Slot {
        private EntityPlayer player;

        public Potion(EntityPlayer player, IInventory inventory, int slotNum, int x, int y) {
            super(inventory, slotNum, x, y);
            this.player = player;
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return canHoldPotion(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
            if (stack.getItem() instanceof net.minecraft.item.ItemPotion && stack.getMetadata() > 0)
                this.player.triggerAchievement(AchievementList.potion);

            super.onPickupFromSlot(playerIn, stack);
        }

        public static boolean canHoldPotion(ItemStack stack) {
            return stack != null && PotionRegistry.getInstance().isValidInput(stack);
        }
    }
}

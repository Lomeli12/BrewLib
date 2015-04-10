package net.lomeli.brewlib.blocks;

import java.util.Arrays;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

import net.minecraftforge.oredict.OreDictionary;

import net.lomeli.brewlib.api.IBrewRecipe;
import net.lomeli.brewlib.api.PotionRegistry;
import net.lomeli.brewlib.core.BasicInventory;

public class TileBrewingStand extends TileEntity implements ISidedInventory, IUpdatePlayerListBox {
    private static final int[] inputSlots = new int[]{3};
    private static final int[] outputSlots = new int[]{0, 1, 2};
    private BasicInventory inventory;
    private IBrewRecipe[] recipes;
    private boolean[] filledSlots;
    private int brewTime;
    private ItemStack mainIngredient;

    public TileBrewingStand() {
        inventory = new BasicInventory(4, "tile.brewlib.brewingStand.name");
        recipes = new IBrewRecipe[3];
    }

    @Override
    public void update() {
        if (brewTime > 0) {
            --brewTime;
            if (brewTime == 0) {
                brewPotions();
                markDirty();
            } else if (!this.canBrew()) {
                brewTime = 0;
                markDirty();
            } else if (!OreDictionary.itemMatches(mainIngredient, inventory.getStackInSlot(3), false)) {
                brewTime = 0;
                markDirty();
            }
        } else if (canBrew()) {
            this.brewTime = 400;
        }
        if (!this.worldObj.isRemote) {
            boolean[] aboolean = this.getSlotsWithItems();
            if (!Arrays.equals(aboolean, this.filledSlots)) {
                this.filledSlots = aboolean;
                IBlockState iblockstate = this.worldObj.getBlockState(this.getPos());

                if (!(iblockstate.getBlock() instanceof BlockAdvBrewStand))
                    return;

                for (int i = 0; i < BlockAdvBrewStand.HAS_BOTTLE.length; ++i)
                    iblockstate = iblockstate.withProperty(BlockAdvBrewStand.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));

                this.worldObj.setBlockState(this.pos, iblockstate, 2);
            }
        }
    }

    public boolean canBrew() {
        ItemStack ingredient = inventory.getStackInSlot(3);
        if (ingredient != null && ingredient.stackSize > 0) {
            if (PotionRegistry.getInstance().isPotionIngredient(ingredient)) {
                boolean flag = false;
                for (int i = 0; i < 3; i++) {
                    ItemStack input = inventory.getStackInSlot(i);
                    if (input != null && input.getItem() != null && input.stackSize > 0) {
                        IBrewRecipe recipe = PotionRegistry.getInstance().getRecipeForInput(input, ingredient);
                        if (recipes == null) {
                            flag = true;
                            break;
                        }
                        recipes[i] = recipe;
                    }
                }
                if (flag || (inventory.getStackInSlot(0) == null && inventory.getStackInSlot(1) == null && inventory.getStackInSlot(2) == null)) {
                    mainIngredient = null;
                    for (int i = 0; i < recipes.length; i++)
                        recipes[i] = null;
                    return false;
                } else {
                    mainIngredient = ingredient;
                    return true;
                }
            }
        }
        return false;
    }

    public void brewPotions() {
        if (net.minecraftforge.event.ForgeEventFactory.onPotionAttemptBreaw(inventory.getInventory())) return;
        if (this.canBrew()) {
            for (int i = 0; i < recipes.length; i++) {
                IBrewRecipe recipe = recipes[i];
                if (recipe != null) {
                    inventory.setInventorySlotContents(i, recipe.getOutput());
                    recipes[i] = null;
                }
            }
            inventory.decrStackSize(3, 1);
            net.minecraftforge.event.ForgeEventFactory.onPotionBrewed(inventory.getInventory());
        }
    }

    public boolean[] getSlotsWithItems() {
        boolean[] aboolean = new boolean[3];

        for (int i = 0; i < 3; ++i) {
            if (this.inventory.getStackInSlot(i) != null)
                aboolean[i] = true;
        }

        return aboolean;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = inventory.decrStackSize(index, count);
        this.markDirty();
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        ItemStack stack = inventory.getStackInSlotOnClosing(index);
        this.markDirty();
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.setInventorySlotContents(index, stack);
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        inventory.openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        inventory.closeInventory(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return inventory.isItemValidForSlot(index, stack) ? (index == 3 ? PotionRegistry.getInstance().isPotionIngredient(stack) : PotionRegistry.getInstance().isValidInput(stack)) : false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.brewTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.brewTime = value;
            default:
        }
    }

    @Override
    public int getFieldCount() {
        return 1;
    }

    @Override
    public void clear() {
        inventory.clear();
        for (int i = 0; i < recipes.length; i++)
            recipes[i] = null;
    }

    @Override
    public String getName() {
        return inventory.getName();
    }

    @Override
    public boolean hasCustomName() {
        return inventory.hasCustomName();
    }

    @Override
    public IChatComponent getDisplayName() {
        return inventory.getDisplayName();
    }

    public void setCustomName(String name) {
        inventory.setCustomName(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        read(compound);
    }

    public NBTTagCompound read(NBTTagCompound tag) {
        inventory.readNBT(tag);
        brewTime = tag.getInteger("brewTime");
        return tag;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        write(compound);
    }

    public NBTTagCompound write(NBTTagCompound tag) {
        inventory.writeNBT(tag);
        tag.setInteger("brewTime", brewTime);
        return tag;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.UP ? inputSlots : outputSlots;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing side) {
        return isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing side) {
        return true;
    }
}

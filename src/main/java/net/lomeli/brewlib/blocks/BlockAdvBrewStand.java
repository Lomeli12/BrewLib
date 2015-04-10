package net.lomeli.brewlib.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.brewlib.BrewLib;

public class BlockAdvBrewStand extends BlockContainer {
    public static final PropertyBool[] HAS_BOTTLE = new PropertyBool[]{PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2")};

    public BlockAdvBrewStand() {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabBrewing);
        this.setHardness(0.5F);
        this.setLightLevel(0.125F);
        this.setUnlocalizedName(BrewLib.MOD_ID + ".brewingStand");
        this.setDefaultState(this.blockState.getBaseState().withProperty(HAS_BOTTLE[0], Boolean.valueOf(false)).withProperty(HAS_BOTTLE[1], Boolean.valueOf(false)).withProperty(HAS_BOTTLE[2], Boolean.valueOf(false)));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBrewingStand();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileBrewingStand) {
                player.openGui(BrewLib.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
        }
        return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity instanceof IInventory)
            InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileentity);

        super.breakBlock(world, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileBrewingStand)
                ((TileBrewingStand) tileentity).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        double d0 = (double) ((float) pos.getX() + 0.4F + rand.nextFloat() * 0.2F);
        double d1 = (double) ((float) pos.getY() + 0.7F + rand.nextFloat() * 0.3F);
        double d2 = (double) ((float) pos.getZ() + 0.4F + rand.nextFloat() * 0.2F);
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
        this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();

        for (int j = 0; j < 3; ++j)
            iblockstate = iblockstate.withProperty(HAS_BOTTLE[j], Boolean.valueOf((meta & 1 << j) > 0));

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        for (int j = 0; j < 3; ++j) {
            if (((Boolean) state.getValue(HAS_BOTTLE[j])).booleanValue())
                i |= 1 << j;
        }

        return i;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[]{HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2]});
    }
}

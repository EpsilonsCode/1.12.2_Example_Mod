package com.l.mod.util.handlers;

import com.l.mod.blocks.ChargeTableContainer;
import com.l.mod.blocks.ChargeTableScreen;
import com.l.mod.blocks.ChargeTableTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ChargeTableGuiHandler implements IGuiHandler {
    private static final int GUI_ID = 7213;
    public static int getGuiID() {return GUI_ID;}
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof ChargeTableTileEntity) {
            ChargeTableTileEntity chargeTableTileEntity = (ChargeTableTileEntity) tileEntity;
            return new ChargeTableContainer(player.inventory, chargeTableTileEntity);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof ChargeTableTileEntity) {
            ChargeTableTileEntity tileEntityInventoryBasic = (ChargeTableTileEntity) tileEntity;
            return new ChargeTableScreen(player.inventory, tileEntityInventoryBasic);
        }
        return null;
    }
}

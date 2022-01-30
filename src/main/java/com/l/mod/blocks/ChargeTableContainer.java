package com.l.mod.blocks;

import com.l.mod.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ChargeTableContainer extends Container {

    private ChargeTableTileEntity tileEntity;

    private final int HOTBAR_SLOT_COUNT = 9;
    private final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private final int VANILLA_FIRST_SLOT_INDEX = 0;
    private final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private final int TE_INVENTORY_SLOT_COUNT = 3;

    public ChargeTableContainer(InventoryPlayer inventoryPlayer, ChargeTableTileEntity chargeTableTileEntity) {
        this.tileEntity = chargeTableTileEntity;

        final int X_OFFSET = 40;
        final int Y_OFFSET = 78;
        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 8 + X_OFFSET;
        final int HOTBAR_YPOS = 109 + Y_OFFSET;
        // Add the players hotbar to the gui - the [xpos, ypos] location of each item
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlotToContainer(new Slot(inventoryPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 8 + X_OFFSET;
        final int PLAYER_INVENTORY_YPOS = 51 + Y_OFFSET;
        // Add the rest of the players inventory to the gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlotToContainer(new Slot(inventoryPlayer, slotNumber, xpos, ypos));
            }
        }

        final int TILE_INVENTORY_XPOS = 64;
        final int TILE_INVENTORY_YPOS = 80;

        addSlotToContainer(new Slot(chargeTableTileEntity, 0, TILE_INVENTORY_XPOS, TILE_INVENTORY_YPOS));
        addSlotToContainer(new Slot(chargeTableTileEntity, 1, TILE_INVENTORY_XPOS + 34, TILE_INVENTORY_YPOS));
        addSlotToContainer(new OutputSlot(chargeTableTileEntity, 2, TILE_INVENTORY_XPOS + 106, TILE_INVENTORY_YPOS));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        this.slotsChanged(tileEntity);
    }

    private static int getCharge(Item item)
    {
        if(item == ModItems.BLUE_METAL)
            return 1;
        else if(item == ModItems.GREEN_METAL)
            return 5;
        else if(item == ModItems.RED_METAL)
            return 30;
        else if(item == ModItems.PURPLE_METAL)
            return 100;
        else
            return 0;
    }

    public void slotsChanged(IInventory inventoryIn) {
        if(inventoryIn.getStackInSlot(0).getItem() == ModItems.CHARGE_ITEM && isMetal(inventoryIn.getStackInSlot(1).getItem()))
        {
            ItemStack stack = inventoryIn.getStackInSlot(0);
            NBTTagCompound tag = new NBTTagCompound();
            if(stack.hasTagCompound())
                tag = stack.getTagCompound();
            tag.setInteger("charge", tag.getInteger("charge") + getCharge(inventoryIn.getStackInSlot(1).getItem()));
            stack.setTagCompound(tag);
            inventoryIn.setInventorySlotContents(2, inventoryIn.getStackInSlot(0));
        }
        else
        {
            inventoryIn.setInventorySlotContents(2, ItemStack.EMPTY);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
    {
        Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!mergeItemStack(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)){
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (sourceSlotIndex >= TE_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;   // EMPTY_ITEM
            }
        } else {
            System.err.print("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;   // EMPTY_ITEM
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {  // getStackSize
            sourceSlot.putStack(ItemStack.EMPTY);  // EMPTY_ITEM
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(player, sourceStack);  //onPickupFromSlot()
        if(sourceSlot.inventory instanceof ChargeTableTileEntity) {
            IInventory inventoryIn = sourceSlot.inventory;
            if (inventoryIn.getStackInSlot(0).getItem() == ModItems.CHARGE_ITEM && isMetal(inventoryIn.getStackInSlot(1).getItem())) {
                ItemStack stack = inventoryIn.getStackInSlot(0);
                NBTTagCompound tag = new NBTTagCompound();
                if (stack.hasTagCompound())
                    tag = stack.getTagCompound();
                tag.setInteger("charge", tag.getInteger("charge") + getCharge(inventoryIn.getStackInSlot(1).getItem()));
                stack.setTagCompound(tag);
                inventoryIn.setInventorySlotContents(2, inventoryIn.getStackInSlot(0));
            } else {
                inventoryIn.setInventorySlotContents(2, ItemStack.EMPTY);
            }
        }
        return copyOfSourceStack;
    }

    public static boolean isMetal(Item item)
    {
        return item == ModItems.BLUE_METAL || item == ModItems.GREEN_METAL || item == ModItems.RED_METAL || item == ModItems.PURPLE_METAL;
    }

    public static class OutputSlot extends Slot
    {

        public OutputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
        {
            inventory.setInventorySlotContents(0, ItemStack.EMPTY);
            ItemStack itemStack = inventory.getStackInSlot(1);
            if(inventory.getStackInSlot(1).getCount() == 1)
                itemStack = ItemStack.EMPTY;
            else
                itemStack.setCount(itemStack.getCount() - 1);
            inventory.setInventorySlotContents(1, itemStack);
            this.onSlotChanged();
            return stack;
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return false;
        }



    }

    @Override
    public boolean canDragIntoSlot(Slot slotIn)
    {
        if(slotIn.inventory instanceof ChargeTableTileEntity && slotIn.getSlotIndex() == 2)
            return false;
        return true;
    }
    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        if(slotIn.inventory instanceof ChargeTableTileEntity && slotIn.getSlotIndex() == 2)
            return false;
        return true;
    }

}
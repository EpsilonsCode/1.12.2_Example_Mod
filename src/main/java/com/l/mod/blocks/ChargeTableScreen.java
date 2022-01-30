package com.l.mod.blocks;

import com.l.mod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class ChargeTableScreen extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/charge_gui.png");
    private ChargeTableTileEntity chargeTableTileEntity;

    public ChargeTableScreen(InventoryPlayer inventorySlotsIn, ChargeTableTileEntity chargeTableTileEntity) {
        super(new ChargeTableContainer(inventorySlotsIn, chargeTableTileEntity));
        this.chargeTableTileEntity = chargeTableTileEntity;
        ySize = 256;
        xSize = 256;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft + (256-176)/2, guiTop + (256-166)/2, 0, 0, 176, 166);
    }
}

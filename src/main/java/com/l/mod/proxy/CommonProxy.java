package com.l.mod.proxy;

import com.l.mod.TmodMain;
import com.l.mod.blocks.ChargeTableTileEntity;
import com.l.mod.util.GuiHandlerRegistry;
import com.l.mod.util.Reference;
import com.l.mod.util.handlers.ChargeTableGuiHandler;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id){}

	public void preInit()
	{
		GameRegistry.registerTileEntity(ChargeTableTileEntity.class, Reference.MODID + ":charge_table_tile_entity");

		NetworkRegistry.INSTANCE.registerGuiHandler(TmodMain.instance, GuiHandlerRegistry.getInstance());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new ChargeTableGuiHandler(), ChargeTableGuiHandler.getGuiID());
		//EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "ghoul"), GhoulEntity.class, "ghoul", 876, TmodMain.instance,30, 1, true);
	}

	public void init()
	{

	}

	public void postInit()
	{

	}
}

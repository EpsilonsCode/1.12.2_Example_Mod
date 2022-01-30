package com.l.mod.proxy;

import com.l.mod.entities.GhoulEntity;
import com.l.mod.entities.GhoulRenderer;
import com.l.mod.init.ModBlocks;
import com.l.mod.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id){
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public void preInit()
	{
		super.preInit();
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(ModBlocks.CHARGE_TABLE.getRegistryName(), "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		ModelLoader.setCustomModelResourceLocation(ModItems.CHARGE_TABLE_ITEM, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		RenderingRegistry.registerEntityRenderingHandler(GhoulEntity.class, GhoulRenderer.FACTORY);
		//Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(GhoulEntity.class, new GhoulRenderer(Minecraft.getMinecraft().getRenderManager()));
	}
	
}

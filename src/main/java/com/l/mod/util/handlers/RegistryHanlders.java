package com.l.mod.util.handlers;


import com.l.mod.entities.GhoulEntity;
import com.l.mod.init.ModBlocks;
import com.l.mod.init.ModItems;
import com.l.mod.util.IHasModel;

import com.l.mod.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@EventBusSubscriber
public class RegistryHanlders {
	
	// on item registry register all items in the ITEMS array in ModItems.java class
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event){
		EntityEntry entry = EntityEntryBuilder.create()
				.entity(GhoulEntity.class)
				.id("ghoul", 1)
				.name(Reference.MODID + ".ghoul")
				.tracker(30, 1, true)
				.build();
		event.getRegistry().register(entry);
	}
	
	// on item registry register all items in the ITEMS array in ModItems.java class
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event){
		for(Item item : ModItems.ITEMS){
			if(item instanceof IHasModel){
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : ModBlocks.BLOCKS){
			if(block instanceof IHasModel){
				((IHasModel)block).registerModels();
			}
		}
		
		
	}
	
	

}

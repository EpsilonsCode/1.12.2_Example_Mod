package com.l.mod.items;


import com.l.mod.TmodMain;
import com.l.mod.init.ModItems;
import com.l.mod.util.IHasModel;

import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel{

	public ItemBase(String name){
		setUnlocalizedName(name); //set unlocalized name
		setRegistryName(name); // set registry name
		setCreativeTab(TmodMain.TRISTONIUM_TAB); // set creative tab
		
		// tell minecraft this is an item
		ModItems.ITEMS.add(this); // add this item to mod items list

	}
	
	@Override
	public void registerModels() {
		TmodMain.proxy.registerItemRenderer(this, 0, "inventory");
	}

}


package com.l.mod.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Tab extends CreativeTabs {
	
	public Tab(String label){
		super("tab");
	}
	
	public ItemStack getTabIconItem(){
		return new ItemStack(Items.IRON_INGOT);
	}

}

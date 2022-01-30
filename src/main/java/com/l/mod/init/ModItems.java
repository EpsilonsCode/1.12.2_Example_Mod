package com.l.mod.init;

import com.l.mod.items.BlockItemBase;
import com.l.mod.items.ItemBase;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {


	// list of minecraft items
	public static final List<Item> ITEMS = new ArrayList<Item>();


	public static Item CHARGE_ITEM = new ItemBase("charge_item").setMaxStackSize(1);
	public static Item BLUE_METAL = new ItemBase("blue_metal");
	public static Item GREEN_METAL = new ItemBase("green_metal");
	public static Item RED_METAL = new ItemBase("red_metal");
	public static Item PURPLE_METAL = new ItemBase("purple_metal");

	public static final Item CHARGE_TABLE_ITEM = new BlockItemBase(ModBlocks.CHARGE_TABLE, ModBlocks.CHARGE_TABLE.getRegistryName().getResourcePath());


}

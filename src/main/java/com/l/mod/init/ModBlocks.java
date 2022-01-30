package com.l.mod.init;

import java.util.ArrayList;
import java.util.List;

import com.l.mod.blocks.ChargeTableBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block CHARGE_TABLE = new ChargeTableBlock("charge_table", Material.IRON);
}



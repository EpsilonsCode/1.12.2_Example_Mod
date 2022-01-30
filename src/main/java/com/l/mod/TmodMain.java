package com.l.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.l.mod.util.Reference;
import com.l.mod.proxy.CommonProxy;
import com.l.mod.tabs.Tab;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class TmodMain
{
	//global variables
	// custom creative tabs
	public static final CreativeTabs TRISTONIUM_TAB = new Tab("tab");
	
	
	
	@Instance
	public static TmodMain instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event)
    {
        proxy.preInit();
    }

    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
        proxy.init();
    }
    
    @EventHandler
    public void PostInit(FMLInitializationEvent event)
    {
    	proxy.postInit();
    }
    
}


package api;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import toolbox.Toolbox;

@Mod(modid = ToolboxAPI.MODID, name = ToolboxAPI.NAME, version = ToolboxAPI.VERSION, dependencies = ToolboxAPI.DEPENDENCIES)
public class ToolboxAPI {
	
	public static final String MODID = "toolbox_api";
	public static final String NAME = "Adventurer's Toolbox API";
	public static final String VERSION = "0.1";
	public static final String DEPENDENCIES = "required-before:toolbox";
	
	@Mod.Instance
	public static ToolboxAPI instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}

}

package toolbox.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	private final static String CATEGORY_GENERAL = "all.general";
	private final static String CATEGORY_TOOLS = "all.tools";
	
	private final static List<String> PROPERTY_ORDER_GENERAL = new ArrayList<String>();
	private final static List<String> PROPERTY_ORDER_TOOLS = new ArrayList<String>();
	
	public static boolean DISABLE_VANILLA_TOOLS;
	public static boolean HIDE_UNCRAFTABLE_HEADS;
	
	public static boolean ENABLE_SCHEMATICS;
	
	public static boolean DISABLE_HANDPICK;
	public static boolean DISABLE_PICKAXE;
	public static boolean DISABLE_AXE;
	public static boolean DISABLE_SHOVEL;
	public static boolean DISABLE_HOE;
	public static boolean DISABLE_HAMMER;
	public static boolean DISABLE_CLIMBING_PICK;
	public static boolean DISABLE_SWORD;
	public static boolean DISABLE_DAGGER;
	public static boolean DISABLE_MACE;
	
	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception e1) {

		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}
	
	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General Options");
		cfg.addCustomCategoryComment(CATEGORY_TOOLS, "Tool Options");

		DISABLE_VANILLA_TOOLS = cfg.getBoolean("Disable Vanilla Tools", CATEGORY_GENERAL, true, "This option disables recipes for vanilla tools.\nIt also attempts to replace all instances of vanilla tools in crafting recipes (including mod recipes) with suitable replacements from Adventurer's Toolbox.\n");
		HIDE_UNCRAFTABLE_HEADS = cfg.getBoolean("Hide Uncraftables", CATEGORY_GENERAL, true, "This option prevents tool heads from showing up in the creative tab if they are made from materials that aren't present in your game.\n");
		ENABLE_SCHEMATICS = cfg.getBoolean("Enable Schematic Mode", CATEGORY_GENERAL, false, "This option replaces tool part recipes with a system using schematic items.\nThis can be helpful if you are experiencing recipe conflicts, or if you want tools to have costs more similar to vanilla.\n");
		
		DISABLE_HANDPICK = cfg.getBoolean("Disable Handpicks", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's handpicks from your game.\n");
		DISABLE_PICKAXE = cfg.getBoolean("Disable Pickaxes", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's pickaxes from your game.\n");
		DISABLE_AXE = cfg.getBoolean("Disable Axes", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's axes from your game.\n");
		DISABLE_SHOVEL = cfg.getBoolean("Disable Shovels", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's shovels from your game.\n");
		DISABLE_HOE = cfg.getBoolean("Disable Hoes", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's hoes from your game.\n");
		DISABLE_HAMMER = cfg.getBoolean("Disable Hammers", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's hammers from your game.\n");
		DISABLE_CLIMBING_PICK = cfg.getBoolean("Disable Climbing Picks", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's climbing picks from your game.\n");
		DISABLE_SWORD = cfg.getBoolean("Disable Swords", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's swords from your game.\n");
		DISABLE_DAGGER = cfg.getBoolean("Disable Daggers", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's daggers from your game.\n");
		DISABLE_MACE = cfg.getBoolean("Disable Maces", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's maces from your game.\n");
		
		PROPERTY_ORDER_GENERAL.add("Disable Vanilla Tools");
		PROPERTY_ORDER_GENERAL.add("Hide Uncraftables");
		PROPERTY_ORDER_GENERAL.add("Enable Schematic Mode");
		
		PROPERTY_ORDER_TOOLS.add("Disable Pickaxes");
		PROPERTY_ORDER_TOOLS.add("Disable Axes");
		PROPERTY_ORDER_TOOLS.add("Disable Shovels");
		PROPERTY_ORDER_TOOLS.add("Disable Hoes");
		PROPERTY_ORDER_TOOLS.add("Disable Hammers");
		PROPERTY_ORDER_TOOLS.add("Disable Handpicks");
		PROPERTY_ORDER_TOOLS.add("Disable Climbing Picks");
		PROPERTY_ORDER_TOOLS.add("Disable Swords");
		PROPERTY_ORDER_TOOLS.add("Disable Daggers");
		PROPERTY_ORDER_TOOLS.add("Disable Maces");
		
		cfg.setCategoryPropertyOrder(CATEGORY_GENERAL, PROPERTY_ORDER_GENERAL);
		cfg.setCategoryPropertyOrder(CATEGORY_TOOLS, PROPERTY_ORDER_TOOLS);
	}

}

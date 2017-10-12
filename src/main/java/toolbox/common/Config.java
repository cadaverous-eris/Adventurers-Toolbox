package toolbox.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	private final static String CATEGORY_GENERAL = "all.general";
	private final static String CATEGORY_TOOLS = "all.tools";
	
	private final static List<String> PROPERTY_ORDER_GENERAL = new ArrayList<String>();
	
	public static boolean DISABLE_VANILLA_TOOLS = false;
	public static boolean HIDE_UNCRAFTABLE_HEADS = false;
	
	public static boolean DISABLE_HAND_PICK = false;
	public static boolean DISABLE_PICKAXE = false;
	public static boolean DISABLE_AXE = false;
	public static boolean DISABLE_SHOVEL = false;
	public static boolean DISABLE_HOE = false;
	public static boolean DISABLE_HAMMER = false;
	public static boolean DISABLE_CLIMBING_PICK = false;
	public static boolean DISABLE_SWORD = false;
	public static boolean DISABLE_DAGGER = false;
	public static boolean DISABLE_MACE = false;
	
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

		DISABLE_VANILLA_TOOLS = cfg.getBoolean("Disable Vanilla Tools", CATEGORY_GENERAL, true, "This option disables recipes for vanilla tools.\nIt also attempts to replace all instances of vanilla tools in crafting recipes (including mod recipes) with suitable replacements from Adventurer's Toolbox.\n");
		HIDE_UNCRAFTABLE_HEADS = cfg.getBoolean("Hide Uncraftables", CATEGORY_GENERAL, true, "This option prevents tool heads from showing up in the creative tab if they are made from materials that aren't present in your game.\n");
		
		PROPERTY_ORDER_GENERAL.add("Disable Vanilla Tools");
		PROPERTY_ORDER_GENERAL.add("Hide Uncraftables");
		
		cfg.setCategoryPropertyOrder(CATEGORY_GENERAL, PROPERTY_ORDER_GENERAL);

		cfg.addCustomCategoryComment(CATEGORY_TOOLS, "Tool Options");
		
		DISABLE_HAND_PICK = cfg.getBoolean("Disable Hand Picks", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's hand picks from your game.\n");
		DISABLE_PICKAXE = cfg.getBoolean("Disable Pickaxes", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's pickaxes from your game.\n");
		DISABLE_AXE = cfg.getBoolean("Disable Axes", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's axes from your game.\n");
		DISABLE_SHOVEL = cfg.getBoolean("Disable Shovels", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's shovels from your game.\n");
		DISABLE_HOE = cfg.getBoolean("Disable Hoes", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's hoes from your game.\n");
		DISABLE_HAMMER = cfg.getBoolean("Disable Hammers", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's hammers from your game.\n");
		DISABLE_CLIMBING_PICK = cfg.getBoolean("Disable Climbing Picks", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's climbing picks from your game.\n");
		DISABLE_SWORD = cfg.getBoolean("Disable Swords", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's swords from your game.\n");
		DISABLE_DAGGER = cfg.getBoolean("Disable Daggers", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's daggers from your game.\n");
		DISABLE_MACE = cfg.getBoolean("Disable Maces", CATEGORY_TOOLS, false, "This option removes Adventurer's Toolbox's maces from your game.\n");
	}

}

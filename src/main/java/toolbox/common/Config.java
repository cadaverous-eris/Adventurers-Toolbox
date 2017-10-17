package toolbox.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {
	
	private final static String CATEGORY_GENERAL = "all.general";
	private final static String CATEGORY_TOOLS = "all.tools";
	private final static String CATEGORY_SCHEMATICS = "all.schematics";
	private final static String CATEGORY_COMPAT = "all.compat";
	
	private final static List<String> PROPERTY_ORDER_GENERAL = new ArrayList<String>();
	private final static List<String> PROPERTY_ORDER_TOOLS = new ArrayList<String>();
	private final static List<String> PROPERTY_ORDER_SCHEMATICS = new ArrayList<String>();
	private final static List<String> PROPERTY_ORDER_COMPAT = new ArrayList<String>();
	
	public static boolean DISABLE_VANILLA_TOOLS;
	public static boolean HIDE_UNCRAFTABLE_HEADS;
	public static boolean SPAWN_WITH_BOOK;
	
	public static List<String> DISABLED_TOOLS = new ArrayList<String>();
	public static List<String> DISABLED_MATERIALS = new ArrayList<String>();
	
	public static boolean ENABLE_SCHEMATICS;
	
	public static boolean ENABLE_TINKERS_COMPAT;
	public static boolean DISABLE_TOOL_HEAD_RECIPES;
	
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
		cfg.addCustomCategoryComment(CATEGORY_SCHEMATICS, "Schematics Options");
		cfg.addCustomCategoryComment(CATEGORY_COMPAT, "Compatability Options");

		DISABLE_VANILLA_TOOLS = cfg.getBoolean("Disable Vanilla Tools", CATEGORY_GENERAL, true, "This option disables recipes for vanilla tools.\nIt also attempts to replace all instances of vanilla tools in crafting recipes (including mod recipes) with suitable replacements from Adventurer's Toolbox.\n");
		HIDE_UNCRAFTABLE_HEADS = cfg.getBoolean("Hide Uncraftables", CATEGORY_GENERAL, true, "This option prevents tool heads from showing up in the creative tab if they are made from materials that aren't present in your game.\n");
		SPAWN_WITH_BOOK= cfg.getBoolean("Spawn With Book", CATEGORY_GENERAL, true, "Disable this if you don't want to be given the guide book when first joining a world.\n");
		
		DISABLED_TOOLS = Arrays.asList(cfg.getStringList("Disabled Tools", CATEGORY_TOOLS, new String[0], "Add tool names to this list on each new line, don't use commas (Find tool names on GitHib).\n"));
		DISABLED_MATERIALS = Arrays.asList(cfg.getStringList("Disabled Materials", CATEGORY_TOOLS, new String[0], "Add material names to this list on each new line, don't use commas (Find material names on GitHib).\n"));
		
		ENABLE_SCHEMATICS = cfg.getBoolean("Enable Schematic Mode", CATEGORY_GENERAL, true, "This option replaces tool part recipes with a system using schematic items.\nThis can be helpful if you are experiencing recipe conflicts, or if you want tools to have costs more similar to vanilla.\n");
		
		ENABLE_TINKERS_COMPAT = cfg.getBoolean("Enable Tinkers Compatibility", CATEGORY_COMPAT, false, "This option adds casts and recipes to the Tinkers' Construct smeltery.\n");
		DISABLE_TOOL_HEAD_RECIPES = cfg.getBoolean("Disable Metal Tool Head Recipes", CATEGORY_COMPAT, false, "This option disables the crafting recipes for metal tool heads.\nUse if you want to only be able to use the smeltery.\n");
		
		PROPERTY_ORDER_GENERAL.add("Disable Vanilla Tools");
		PROPERTY_ORDER_GENERAL.add("Hide Uncraftables");
		PROPERTY_ORDER_GENERAL.add("Spawn With Book");
		
		PROPERTY_ORDER_TOOLS.add("Disabled Tools");
		PROPERTY_ORDER_TOOLS.add("Disabled Materials");
		
		PROPERTY_ORDER_SCHEMATICS.add("Enable Schematic Mode");
		
		PROPERTY_ORDER_COMPAT.add("Enable Tinkers Compatibility");
		PROPERTY_ORDER_COMPAT.add("Disable Metal Tool Head Recipes");
		
		cfg.setCategoryPropertyOrder(CATEGORY_GENERAL, PROPERTY_ORDER_GENERAL);
		cfg.setCategoryPropertyOrder(CATEGORY_TOOLS, PROPERTY_ORDER_TOOLS);
		cfg.setCategoryPropertyOrder(CATEGORY_SCHEMATICS, PROPERTY_ORDER_SCHEMATICS);
		cfg.setCategoryPropertyOrder(CATEGORY_COMPAT, PROPERTY_ORDER_COMPAT);
	}

}

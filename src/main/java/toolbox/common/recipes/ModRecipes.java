package toolbox.common.recipes;

import java.util.HashMap;
import java.util.Map;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import toolbox.Toolbox;
import toolbox.common.CommonProxy;
import toolbox.common.Config;
import toolbox.common.items.ItemSchematic;
import toolbox.common.items.ModItems;
import toolbox.common.materials.ModMaterials;

public class ModRecipes {

	public static Map<ItemStack, HeadMaterial> head_map = new HashMap<>();
	public static Map<ItemStack, HaftMaterial> haft_map = new HashMap<>();
	public static Map<ItemStack, HandleMaterial> handle_map = new HashMap<>();
	public static Map<ItemStack, AdornmentMaterial> adornment_map = new HashMap<>();

	public static void init() {

		if (!Config.DISABLED_TOOLS.contains("pickaxe")) {
			for (int i : ModItems.pickaxe_head.meta_map.keySet()) {
				HeadMaterial mat = ModItems.pickaxe_head.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.pickaxe_head, 1, i), mat.getCraftingItem(), "pickaxe_head", 3).setRegistryName(new ResourceLocation(Toolbox.MODID, "pickaxe_head_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.pickaxe_head, 1, i), "PPP", "S S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "pickaxe_head_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("axe")) {
			for (int i : ModItems.axe_head.meta_map.keySet()) {
				HeadMaterial mat = ModItems.axe_head.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.axe_head, 1, i), mat.getCraftingItem(), "axe_head", 3).setRegistryName(new ResourceLocation(Toolbox.MODID, "axe_head_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.axe_head, 1, i), "PPS", "P  ", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "axe_head_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("shovel")) {
			for (int i : ModItems.shovel_head.meta_map.keySet()) {
				HeadMaterial mat = ModItems.shovel_head.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.shovel_head, 1, i), mat.getCraftingItem(), "shovel_head", 1).setRegistryName(new ResourceLocation(Toolbox.MODID, "shovel_head_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.shovel_head, 1, i), "SPS", " S ", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "shovel_head_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("hoe")) {
			for (int i : ModItems.hoe_head.meta_map.keySet()) {
				HeadMaterial mat = ModItems.hoe_head.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.hoe_head, 1, i), mat.getCraftingItem(), "hoe_head", 2).setRegistryName(new ResourceLocation(Toolbox.MODID, "hoe_head_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.hoe_head, 1, i), "PP ", "  S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "hoe_head_" + mat.getName())));
					}
				}
			}
		}
		if (!Config.DISABLED_TOOLS.contains("handpick")) {
			for (int i : ModItems.handpick_head.meta_map.keySet()) {
				HeadMaterial mat = ModItems.handpick_head.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.handpick_head, 1, i), mat.getCraftingItem(), "handpick_head", 2).setRegistryName(new ResourceLocation(Toolbox.MODID, "handpick_head_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.handpick_head, 1, i), " P ", "S S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "handpick_head_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("hammer")) {
			for (int i : ModItems.hammer_head.meta_map.keySet()) {
				HeadMaterial mat = ModItems.hammer_head.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.hammer_head, 1, i), mat.getCraftingItem(), "hammer_head", 8).setRegistryName(new ResourceLocation(Toolbox.MODID, "hammer_head_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.hammer_head, 1, i), "PSP", "PPP", "PSP", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "hammer_head_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("sword")) {
			for (int i : ModItems.sword_blade.meta_map.keySet()) {
				HeadMaterial mat = ModItems.sword_blade.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.sword_blade, 1, i), mat.getCraftingItem(), "sword_blade", 1).setRegistryName(new ResourceLocation(Toolbox.MODID, "sword_blade_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.sword_blade, 1, i), " S ", " P ", "SPS", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "sword_blade_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("sword")) {
			for (int i : ModItems.sword_crossguard.meta_map.keySet()) {
				HeadMaterial mat = ModItems.sword_crossguard.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.sword_crossguard, 1, i), mat.getCraftingItem(), "sword_crossguard", 1).setRegistryName(new ResourceLocation(Toolbox.MODID, "crossguard_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.sword_crossguard, 1, i), "SPS", "   ", " S ", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "crossguard_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("dagger")) {
			for (int i : ModItems.dagger_blade.meta_map.keySet()) {
				HeadMaterial mat = ModItems.dagger_blade.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.dagger_blade, 1, i), mat.getCraftingItem(), "dagger_blade", 1).setRegistryName(new ResourceLocation(Toolbox.MODID, "dagger_blade_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.dagger_blade, 1, i), " S ", " P ", "S S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "dagger_blade_" + mat.getName())));
					}
				}
			}
		}

		if (!Config.DISABLED_TOOLS.contains("mace")) {
			for (int i : ModItems.mace_head.meta_map.keySet()) {
				HeadMaterial mat = ModItems.mace_head.meta_map.get(i);
				if (!Config.DISABLE_TOOL_HEAD_RECIPES || !CommonProxy.smelteryMaterials.contains(mat)) {
					if (Config.ENABLE_SCHEMATICS) {
						ForgeRegistries.RECIPES.register(getToolHeadSchematicRecipe(new ItemStack(ModItems.mace_head, 1, i), mat.getCraftingItem(), "mace_head", 4).setRegistryName(new ResourceLocation(Toolbox.MODID, "mace_head_" + mat.getName())));
					} else {
						ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.mace_head, 1, i), "SPS", "PPP", "SPS", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "mace_head_" + mat.getName())));
					}
				}
			}
		}

		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.handle, 1, 0), "stickWood", new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_0")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.handle, 1, 1), "stickWood", new ItemStack(Items.LEATHER)).setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_1")));
		//ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.handle, 1, 2), "stickWood", "stickWood").setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_2")));
		//ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.handle, 1, 3), "stickWood", new ItemStack(Items.BONE)).setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_3")));

		OreDictionary.registerOre("pebble", new ItemStack(ModItems.rock));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.rock, 9), "cobblestone").setRegistryName(new ResourceLocation(Toolbox.MODID, "pebble")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(Blocks.COBBLESTONE), "RRR", "RRR", "RRR", 'R', new ItemStack(ModItems.rock)).setRegistryName(new ResourceLocation(Toolbox.MODID, "cobble")));

		RecipeSorter.register("toolbox:book", BookRecipe.class, RecipeSorter.Category.SHAPELESS,
				"after:minecraft:shapeless");
		ForgeRegistries.RECIPES.register(new BookRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "book")));

		if (Config.ALLOW_ADORNMENT_REPLACEMENT) {
			ForgeRegistries.RECIPES.register(new PartReplacementRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "part_replacement")));
		}
		
		initToolRecipes();

		initMaterialItems();

		registerSchematicRecipe("pickaxe", "pickaxe_head", "PPP", " S ", " S ");
		registerSchematicRecipe("axe", "axe_head", "PP", "PS", " S");
		registerSchematicRecipe("shovel", "shovel_head", "P", "S", "S");
		registerSchematicRecipe("hoe", "hoe_head", "PP", " S", " S");
		registerSchematicRecipe("handpick", "handpick_head", "PP", " S");
		registerSchematicRecipe("hammer", "hammer_head", "PPP", "PSP", " S ");
		registerSchematicRecipe("climbing_pick", "climbing_pick_head", "PP ", " SP", " S ");
		registerSchematicRecipe("sword", "sword_blade", "P", "P", "S");
		registerSchematicRecipe("sword", "sword_crossguard", "PSP", " S ", " P ");
		registerSchematicRecipe("dagger", "dagger_blade", "P", "S");
		registerSchematicRecipe("mace", "mace_head", "PP", "PP", " S");
	}

	private static void registerSchematicRecipe(String toolType, String schematicType, String row1, String row2, String row3) {
		if (Config.ENABLE_SCHEMATICS && !Config.DISABLED_TOOLS.contains(toolType) && Config.CRAFTED_SCHEMATICS.contains(schematicType) && ItemSchematic.subtypes.contains(schematicType)) {
			ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, ModItems.schematic.createStack(schematicType), row1, row2, row3, 'P', new ItemStack(Items.PAPER), 'S', new ItemStack(Items.STICK)).setRegistryName(schematicType + "_schematic"));
		}
	}

	private static void registerSchematicRecipe(String toolType, String schematicType, String row1, String row2) {
		if (Config.ENABLE_SCHEMATICS && !Config.DISABLED_TOOLS.contains(toolType) && Config.CRAFTED_SCHEMATICS.contains(schematicType) && ItemSchematic.subtypes.contains(schematicType)) {
			ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, ModItems.schematic.createStack(schematicType), row1, row2, 'P', new ItemStack(Items.PAPER), 'S', new ItemStack(Items.STICK)).setRegistryName(schematicType + "_schematic"));
		}
	}

	public static void initToolRecipes() {
		if (!Config.DISABLED_TOOLS.contains("pickaxe")) {
			RecipeSorter.register("toolbox:pickaxe", PickaxeRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new PickaxeRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "pickaxe")));
		}
		if (!Config.DISABLED_TOOLS.contains("axe")) {
			RecipeSorter.register("toolbox:axe", AxeRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new AxeRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "axe")));
		}
		if (!Config.DISABLED_TOOLS.contains("shovel")) {
			RecipeSorter.register("toolbox:shovel", ShovelRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new ShovelRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "shovel")));
		}
		if (!Config.DISABLED_TOOLS.contains("hoe")) {
			RecipeSorter.register("toolbox:hoe", HoeRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new HoeRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "hoe")));
		}
		if (!Config.DISABLED_TOOLS.contains("handpick")) {
			RecipeSorter.register("toolbox:handpick", HandpickRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new HandpickRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "handpick")));
		}
		if (!Config.DISABLED_TOOLS.contains("hammer")) {
			RecipeSorter.register("toolbox:hammer", HammerRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new HammerRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "hammer")));
		}
		if (!Config.DISABLED_TOOLS.contains("sword")) {
			RecipeSorter.register("toolbox:sword", SwordRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new SwordRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "sword")));
		}
		if (!Config.DISABLED_TOOLS.contains("dagger")) {
			RecipeSorter.register("toolbox:dagger", DaggerRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new DaggerRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "dagger")));
		}
		if (!Config.DISABLED_TOOLS.contains("mace")) {
			RecipeSorter.register("toolbox:mace", MaceRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new MaceRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "mace")));
		}
	}

	public static void initMaterialItems() {
		haft_map.put(new ItemStack(Items.STICK), ModMaterials.HAFT_WOOD);
		haft_map.put(new ItemStack(Items.BONE), ModMaterials.HAFT_BONE);
		haft_map.put(new ItemStack(Items.BLAZE_ROD), ModMaterials.HAFT_BLAZE_ROD);
		haft_map.put(new ItemStack(Blocks.END_ROD), ModMaterials.HAFT_END_ROD);
		
		Item haft = ForgeRegistries.ITEMS.getValue(new ResourceLocation("betterwithmods:material"));
		Item witherBone = ForgeRegistries.ITEMS.getValue(new ResourceLocation("nex:item_bone_wither"));
		if (haft != null) haft_map.put(new ItemStack(haft, 1, 36), ModMaterials.HAFT_IMPROVED);
		if (witherBone != null) haft_map.put(new ItemStack(witherBone, 1, 0), ModMaterials.HAFT_WITHER_BONE);
		for (ItemStack treatedStick : OreDictionary.getOres("stickTreatedWood")) {
			haft_map.put(treatedStick, ModMaterials.HAFT_TREATED_WOOD);
		}
		for (ItemStack witheredBone : OreDictionary.getOres("boneWithered")) {
			haft_map.put(witheredBone, ModMaterials.HAFT_WITHERED_BONE);
		}
		for (ItemStack witheredBone : OreDictionary.getOres("boneWither")) {
			if (!witheredBone.getItem().getRegistryName().getResourceDomain().equals("nex")) {
				haft_map.put(witheredBone, ModMaterials.HAFT_WITHERED_BONE);
			}
		}
		
		handle_map.put(new ItemStack(ModItems.handle, 1, 0), ModMaterials.HANDLE_CLOTH);
		handle_map.put(new ItemStack(ModItems.handle, 1, 1), ModMaterials.HANDLE_LEATHER);
		handle_map.put(new ItemStack(Items.STICK, 1, 0), ModMaterials.HANDLE_WOOD);
		handle_map.put(new ItemStack(Items.BONE, 1, 0), ModMaterials.HANDLE_BONE);
		
		for (ItemStack treatedStick : OreDictionary.getOres("stickTreatedWood")) {
			handle_map.put(treatedStick, ModMaterials.HANDLE_TREATED_WOOD);
		}
		for (ItemStack witheredBone : OreDictionary.getOres("boneWithered")) {
			handle_map.put(witheredBone, ModMaterials.HANDLE_WITHERED_BONE);
		}
		for (ItemStack witheredBone : OreDictionary.getOres("boneWither")) {
			if (!witheredBone.getItem().getRegistryName().getResourceDomain().equals("nex")) {
				handle_map.put(witheredBone, ModMaterials.HANDLE_WITHERED_BONE);
			}
		}
		
		adornment_map.put(new ItemStack(Items.DIAMOND), ModMaterials.ADORNMENT_DIAMOND);
		adornment_map.put(new ItemStack(Items.EMERALD), ModMaterials.ADORNMENT_EMERALD);
		adornment_map.put(new ItemStack(Items.QUARTZ), ModMaterials.ADORNMENT_QUARTZ);
		adornment_map.put(new ItemStack(Items.PRISMARINE_CRYSTALS), ModMaterials.ADORNMENT_PRISMARINE);
		adornment_map.put(new ItemStack(Items.ENDER_PEARL), ModMaterials.ADORNMENT_ENDER_PEARL);
		adornment_map.put(new ItemStack(Items.DYE, 1, 4), ModMaterials.ADORNMENT_LAPIS);
		
		for (ItemStack biotite : OreDictionary.getOres("gemEnderBiotite")) {
			adornment_map.put(biotite, ModMaterials.ADORNMENT_BIOTITE);
		}
		for (ItemStack ruby : OreDictionary.getOres("gemRuby")) {
			adornment_map.put(ruby, ModMaterials.ADORNMENT_RUBY);
		}
		for (ItemStack amethyst : OreDictionary.getOres("gemAmethyst")) {
			adornment_map.put(amethyst, ModMaterials.ADORNMENT_AMETHYST);
		}
		for (ItemStack peridot : OreDictionary.getOres("gemPeridot")) {
			adornment_map.put(peridot, ModMaterials.ADORNMENT_PERIDOT);
		}
		for (ItemStack topaz : OreDictionary.getOres("gemTopaz")) {
			adornment_map.put(topaz, ModMaterials.ADORNMENT_TOPAZ);
		}
		for (ItemStack tanzanite : OreDictionary.getOres("gemTanzanite")) {
			adornment_map.put(tanzanite, ModMaterials.ADORNMENT_TANZANITE);
		}
		for (ItemStack malachite : OreDictionary.getOres("gemMalachite")) {
			adornment_map.put(malachite, ModMaterials.ADORNMENT_MALACHITE);
		}
		for (ItemStack sapphire : OreDictionary.getOres("gemSapphire")) {
			adornment_map.put(sapphire, ModMaterials.ADORNMENT_SAPPHIRE);
		}
		for (ItemStack amber : OreDictionary.getOres("gemAmber")) {
			adornment_map.put(amber, ModMaterials.ADORNMENT_AMBER);
		}
		for (ItemStack obsidian : OreDictionary.getOres("gemObsidian")) {
			adornment_map.put(obsidian, ModMaterials.ADORNMENT_OBSIDIAN);
		}
		for (ItemStack aquamarine : OreDictionary.getOres("gemAquamarine")) {
			adornment_map.put(aquamarine, ModMaterials.ADORNMENT_AQUAMARINE);
		}
	}

	private static IRecipe getToolHeadSchematicRecipe(ItemStack output, String material, String type, int cost) {
		NonNullList<Ingredient> inputs = NonNullList.withSize(cost + 1, Ingredient.EMPTY);
		ItemStack schematic = new ItemStack(ModItems.schematic);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString(ItemSchematic.type_tag, type);
		schematic.setTagCompound(nbt);
		Ingredient schematicIngredient = new IngredientNBT(schematic) {

		};
		inputs.set(0, schematicIngredient);
		for (int i = 1; i <= cost; i++) {
			inputs.set(i, new OreIngredient(material));
		}

		return new ShapelessOreRecipe(null, inputs, output);
	}

}

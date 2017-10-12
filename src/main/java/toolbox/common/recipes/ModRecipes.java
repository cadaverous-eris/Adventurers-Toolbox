package toolbox.common.recipes;

import java.util.HashMap;
import java.util.Map;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import toolbox.Toolbox;
import toolbox.common.Config;
import toolbox.common.items.ModItems;
import toolbox.common.materials.ModMaterials;

public class ModRecipes {

	public static Map<ItemStack, HeadMaterial> head_map = new HashMap<ItemStack, HeadMaterial>();
	public static Map<ItemStack, HaftMaterial> haft_map = new HashMap<ItemStack, HaftMaterial>();
	public static Map<ItemStack, HandleMaterial> handle_map = new HashMap<ItemStack, HandleMaterial>();
	public static Map<ItemStack, AdornmentMaterial> adornment_map = new HashMap<ItemStack, AdornmentMaterial>();

	public static void init() {

		if (!Config.DISABLE_PICKAXE) {
			for (int i : ModItems.PICKAXE_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.PICKAXE_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.PICKAXE_HEAD, 1, i), "PPP", "S S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "pickaxe_head_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_AXE) {
			for (int i : ModItems.AXE_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.AXE_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.AXE_HEAD, 1, i), "PPS", "P  ", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "axe_head_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_SHOVEL) {
			for (int i : ModItems.SHOVEL_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.SHOVEL_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.SHOVEL_HEAD, 1, i), "SPS", " S ", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "shovel_head_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_HOE) {
			for (int i : ModItems.HOE_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.HOE_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.HOE_HEAD, 1, i), "PP ", "  S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "hoe_head_" + mat.getName())));
			}
		}
		if (!Config.DISABLE_HAND_PICK) {
			for (int i : ModItems.HANDPICK_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.HANDPICK_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.HANDPICK_HEAD, 1, i), " P ", "S S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "handpick_head_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_HAMMER) {
			for (int i : ModItems.HAMMER_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.HAMMER_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.HAMMER_HEAD, 1, i), "PSP", "PPP", "PSP", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "hammer_head_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_CLIMBING_PICK) {
			for (int i : ModItems.CLIMBING_PICK_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.CLIMBING_PICK_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.CLIMBING_PICK_HEAD, 1, i), "PPS", "S  ", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "climbing_pick_head_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_SWORD) {
			for (int i : ModItems.SWORD_BLADE.meta_map.keySet()) {
				HeadMaterial mat = ModItems.SWORD_BLADE.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.SWORD_BLADE, 1, i), " S ", " P ", "SPS", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "sword_blade_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_SWORD) {
			for (int i : ModItems.CROSSGUARD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.CROSSGUARD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.CROSSGUARD, 1, i), "SPS", "   ", " S ", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "crossguard_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_DAGGER) {
			for (int i : ModItems.DAGGER_BLADE.meta_map.keySet()) {
				HeadMaterial mat = ModItems.DAGGER_BLADE.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.DAGGER_BLADE, 1, i), " S ", " P ", "S S", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "dagger_blade_" + mat.getName())));
			}
		}

		if (!Config.DISABLE_MACE) {
			for (int i : ModItems.MACE_HEAD.meta_map.keySet()) {
				HeadMaterial mat = ModItems.MACE_HEAD.meta_map.get(i);
				ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(ModItems.MACE_HEAD, 1, i), "SPS", "PPP", "SPS", 'P', mat.getCraftingItem(), 'S', mat.getSmallCraftingItem()).setRegistryName(new ResourceLocation(Toolbox.MODID, "mace_head_" + mat.getName())));
			}
		}

		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.HANDLE, 1, 0), "stickWood", new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_0")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.HANDLE, 1, 1), "stickWood", new ItemStack(Items.LEATHER)).setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_1")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.HANDLE, 1, 2), "stickWood", "stickWood").setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_2")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.HANDLE, 1, 3), "stickWood", new ItemStack(Items.BONE)).setRegistryName(new ResourceLocation(Toolbox.MODID, "handle_3")));

		OreDictionary.registerOre("pebble", new ItemStack(ModItems.ROCK));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(null, new ItemStack(ModItems.ROCK, 9), "cobblestone").setRegistryName(new ResourceLocation(Toolbox.MODID, "pebble")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, new ItemStack(Blocks.COBBLESTONE), "RRR", "RRR", "RRR", 'R', new ItemStack(ModItems.ROCK)).setRegistryName(new ResourceLocation(Toolbox.MODID, "cobble")));

		RecipeSorter.register("toolbox:book", BookRecipe.class, RecipeSorter.Category.SHAPELESS,
				"after:minecraft:shapeless");
		ForgeRegistries.RECIPES.register(new BookRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "book")));

		initToolRecipes();

		initMaterialItems();
	}

	public static void initToolRecipes() {
		if (!Config.DISABLE_PICKAXE) {
			RecipeSorter.register("toolbox:pickaxe", PickaxeRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new PickaxeRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "pickaxe")));
		}
		if (!Config.DISABLE_AXE) {
			RecipeSorter.register("toolbox:axe", AxeRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new AxeRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "axe")));
		}
		if (!Config.DISABLE_SHOVEL) {
			RecipeSorter.register("toolbox:shovel", ShovelRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new ShovelRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "shovel")));
		}
		if (!Config.DISABLE_HOE) {
			RecipeSorter.register("toolbox:hoe", HoeRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new HoeRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "hoe")));
		}
		if (!Config.DISABLE_HAND_PICK) {
			RecipeSorter.register("toolbox:handpick", HandpickRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new HandpickRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "handpick")));
		}
		if (!Config.DISABLE_HAMMER) {
			RecipeSorter.register("toolbox:hammer", HammerRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new HammerRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "hammer")));
		}
		if (!Config.DISABLE_CLIMBING_PICK) {
			RecipeSorter.register("toolbox:climbing_pick", ClimbingPickRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new ClimbingPickRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "climbing_pick")));
		}
		if (!Config.DISABLE_SWORD) {
			RecipeSorter.register("toolbox:sword", SwordRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new SwordRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "sword")));
		}
		if (!Config.DISABLE_DAGGER) {
			RecipeSorter.register("toolbox:dagger", DaggerRecipe.class, RecipeSorter.Category.SHAPELESS,
					"after:minecraft:shapeless");
			ForgeRegistries.RECIPES.register(new DaggerRecipe().setRegistryName(new ResourceLocation(Toolbox.MODID, "dagger")));
		}
		if (!Config.DISABLE_MACE) {
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

		handle_map.put(new ItemStack(ModItems.HANDLE, 1, 0), ModMaterials.HANDLE_CLOTH);
		handle_map.put(new ItemStack(ModItems.HANDLE, 1, 1), ModMaterials.HANDLE_LEATHER);
		handle_map.put(new ItemStack(ModItems.HANDLE, 1, 2), ModMaterials.HANDLE_WOOD);
		handle_map.put(new ItemStack(ModItems.HANDLE, 1, 3), ModMaterials.HANDLE_BONE);

		adornment_map.put(new ItemStack(Items.DIAMOND), ModMaterials.ADORNMENT_DIAMOND);
		adornment_map.put(new ItemStack(Items.EMERALD), ModMaterials.ADORNMENT_EMERALD);
		adornment_map.put(new ItemStack(Items.QUARTZ), ModMaterials.ADORNMENT_QUARTZ);
		adornment_map.put(new ItemStack(Items.PRISMARINE_CRYSTALS), ModMaterials.ADORNMENT_PRISMARINE);
		adornment_map.put(new ItemStack(Items.ENDER_PEARL), ModMaterials.ADORNMENT_ENDER_PEARL);
	}

}

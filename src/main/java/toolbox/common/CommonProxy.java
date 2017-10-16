package toolbox.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import toolbox.Toolbox;
import toolbox.common.entities.ModEntities;
import toolbox.common.handlers.CraftingEventHandler;
import toolbox.common.handlers.HammerHandler;
import toolbox.common.handlers.HandpickHarvestHandler;
import toolbox.common.handlers.MaceHandler;
import toolbox.common.handlers.SpecialToolAbilityHandler;
import toolbox.common.handlers.WorldHandler;
import toolbox.common.items.ItemBase;
import toolbox.common.items.ModItems;
import toolbox.common.items.parts.ItemToolPart;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.ICrossguardTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
import toolbox.common.materials.ModMaterials;
import toolbox.common.recipes.ModRecipes;
import toolbox.compat.tconstruct.TConstructCompat;

public class CommonProxy {

	public static Configuration config;

	public void preInit(FMLPreInitializationEvent event) {

		File directory = event.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "adventurers_toolbox.cfg"));
		Config.readConfig();

		MinecraftForge.EVENT_BUS.register(new HandpickHarvestHandler());
		MinecraftForge.EVENT_BUS.register(new SpecialToolAbilityHandler());
		MinecraftForge.EVENT_BUS.register(new HammerHandler());
		MinecraftForge.EVENT_BUS.register(new MaceHandler());
		MinecraftForge.EVENT_BUS.register(new WorldHandler());
		MinecraftForge.EVENT_BUS.register(new CraftingEventHandler());

		ModMaterials.init();
		Toolbox.logger.log(Level.INFO,
				"Initialized tool part materials with " + Materials.head_registry.size() + " head materials, "
						+ Materials.haft_registry.size() + " haft materials, " + Materials.handle_registry.size()
						+ " handle materials, and " + Materials.adornment_registry.size() + " adornment materials");
		ModEntities.init();

		if (Loader.isModLoaded("tconstruct"))
			TConstructCompat.preInit();
	}

	public void init(FMLInitializationEvent event) {

		NetworkRegistry.INSTANCE.registerGuiHandler(Toolbox.instance, new GuiProxy());

		ModRecipes.init();

		if (Loader.isModLoaded("tconstruct"))
			TConstructCompat.init();
		// Toolbox.HEAD_CRAFTED.registerStat();

	}

	public void postInit(FMLPostInitializationEvent event) {

		if (config.hasChanged()) {
			config.save();
		}

		ModMaterials.initHeadRepairItems();

		if (Config.DISABLE_VANILLA_TOOLS) {
			processRecipes();
		}
		
	}

	public static List<ResourceLocation> removed_recipes = new ArrayList<ResourceLocation>();
	
	protected void processRecipes() {
		if (ForgeRegistries.RECIPES instanceof IForgeRegistryModifiable) {
			IForgeRegistryModifiable registry = (IForgeRegistryModifiable) ForgeRegistries.RECIPES;

			for (Entry<ResourceLocation, IRecipe> recipeEntry : ForgeRegistries.RECIPES.getEntries()) {
				IRecipe recipe = recipeEntry.getValue();

				if ((recipe.getRecipeOutput().getItem() instanceof ItemHoe
						|| recipe.getRecipeOutput().getItem() instanceof ItemAxe
						|| recipe.getRecipeOutput().getItem() instanceof ItemSpade
						|| recipe.getRecipeOutput().getItem() instanceof ItemPickaxe
						|| recipe.getRecipeOutput().getItem() instanceof ItemSword)) {
					registry.remove(recipeEntry.getKey());
					registry.register(new IRecipe() {

						private ResourceLocation registryName;
						
						@Override
						public IRecipe setRegistryName(ResourceLocation name) {
							this.registryName = name;
							return this;
						}

						@Override
						public ResourceLocation getRegistryName() {
							return this.registryName;
						}

						@Override
						public Class<IRecipe> getRegistryType() {
							return IRecipe.class;
						}

						@Override
						public boolean matches(InventoryCrafting inv, World worldIn) {
							return false;
						}

						@Override
						public ItemStack getCraftingResult(InventoryCrafting inv) {
							return ItemStack.EMPTY;
						}

						@Override
						public boolean canFit(int width, int height) {
							return false;
						}

						@Override
						public ItemStack getRecipeOutput() {
							return ItemStack.EMPTY;
						}
						
					}.setRegistryName(recipeEntry.getKey()));
					removed_recipes.add(recipeEntry.getKey());
				} else {
					NonNullList<Ingredient> ingredients = recipe.getIngredients();
					for (int i = 0; i < ingredients.size(); i++) {
						ItemStack[] matchingStacks = ingredients.get(i).getMatchingStacks();
						boolean flag = false;
						Item ingredientItem = null;
						for (int j = 0; j < matchingStacks.length && !flag; j++) {
							Item item = matchingStacks[j].getItem();
							if (!getToolReplacement(item).isEmpty()) {
								ingredientItem = item;
	                            flag = true;
	                        }
						}
						if (flag && ingredientItem != null && !getToolReplacement(ingredientItem).isEmpty()) {
							ingredients.set(i, new IngredientNBT(getToolReplacement(ingredientItem)) {
								
							});
						}
					}
				}
			}
		}
	}

	public ItemStack createWoodTool(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_WOOD.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createStoneTool(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_STONE.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createIronTool(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createDiamondTool(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_DIAMOND.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createGoldTool(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_GOLD.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createWoodSword(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_WOOD.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createStoneSword(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_STONE.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_STONE.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createIronSword(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createDiamondSword(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_DIAMOND.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createGoldSword(ItemBase item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_GOLD.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_GOLD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	private ItemStack getToolReplacement(Item item) {
		if (item == Items.DIAMOND_PICKAXE) {
			return createDiamondTool(ModItems.pickaxe);
		}
		if (item == Items.GOLDEN_PICKAXE) {
			return createGoldTool(ModItems.pickaxe);
		}
		if (item == Items.IRON_PICKAXE) {
			return createIronTool(ModItems.pickaxe);
		}
		if (item == Items.STONE_PICKAXE) {
			return createStoneTool(ModItems.pickaxe);
		}
		if (item == Items.WOODEN_PICKAXE) {
			return createWoodTool(ModItems.pickaxe);
		}
		if (item == Items.DIAMOND_AXE) {
			return createDiamondTool(ModItems.axe);
		}
		if (item == Items.GOLDEN_AXE) {
			return createGoldTool(ModItems.axe);
		}
		if (item == Items.IRON_AXE) {
			return createIronTool(ModItems.axe);
		}
		if (item == Items.STONE_AXE) {
			return createStoneTool(ModItems.axe);
		}
		if (item == Items.WOODEN_AXE) {
			return createWoodTool(ModItems.axe);
		}
		if (item == Items.DIAMOND_SHOVEL) {
			return createDiamondTool(ModItems.shovel);
		}
		if (item == Items.GOLDEN_SHOVEL) {
			return createGoldTool(ModItems.shovel);
		}
		if (item == Items.IRON_SHOVEL) {
			return createIronTool(ModItems.shovel);
		}
		if (item == Items.STONE_SHOVEL) {
			return createStoneTool(ModItems.shovel);
		}
		if (item == Items.WOODEN_SHOVEL) {
			return createWoodTool(ModItems.hoe);
		}
		if (item == Items.DIAMOND_HOE) {
			return createDiamondTool(ModItems.hoe);
		}
		if (item == Items.GOLDEN_HOE) {
			return createGoldTool(ModItems.hoe);
		}
		if (item == Items.IRON_HOE) {
			return createIronTool(ModItems.hoe);
		}
		if (item == Items.STONE_HOE) {
			return createStoneTool(ModItems.hoe);
		}
		if (item == Items.WOODEN_HOE) {
			return createWoodTool(ModItems.hoe);
		}
		if (item == Items.DIAMOND_SWORD) {
			return createDiamondSword(ModItems.sword);
		}
		if (item == Items.GOLDEN_SWORD) {
			return createGoldSword(ModItems.sword);
		}
		if (item == Items.IRON_SWORD) {
			return createIronSword(ModItems.sword);
		}
		if (item == Items.STONE_SWORD) {
			return createStoneSword(ModItems.sword);
		}
		if (item == Items.WOODEN_SWORD) {
			return createWoodSword(ModItems.sword);
		}

		return ItemStack.EMPTY;
	}

}

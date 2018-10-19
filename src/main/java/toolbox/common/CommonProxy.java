package toolbox.common;

import api.materials.HeadMaterial;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

import api.materials.Materials;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.IThaumcraftRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.api.research.ResearchStage;
import thaumcraft.common.lib.research.ResearchManager;
import toolbox.Toolbox;
import toolbox.common.entities.ModEntities;
import toolbox.common.handlers.HammerHandler;
import toolbox.common.handlers.HandpickHarvestHandler;
import toolbox.common.handlers.WeaponHandler;
import toolbox.common.handlers.SpecialToolAbilityHandler;
import toolbox.common.handlers.ToolRepairHandler;
import toolbox.common.handlers.WorldHandler;
import toolbox.common.items.ItemBase;
import toolbox.common.items.ModItems;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.ICrossguardTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
import toolbox.common.materials.ModMaterials;
import toolbox.common.recipes.ModRecipes;
import toolbox.compat.tconstruct.TConstructCompat;
import toolbox.compat.thaumcraft.ThaumcraftCompat;

public class CommonProxy {

	public static Configuration config;
	
	public static List<HeadMaterial> smelteryMaterials = new ArrayList<>();
	
	public static boolean thaumcraftLoaded;

	public void preInit(FMLPreInitializationEvent event) {

		File directory = event.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "adventurers_toolbox.cfg"));
		Config.readConfig();

		MinecraftForge.EVENT_BUS.register(new HandpickHarvestHandler());
		MinecraftForge.EVENT_BUS.register(new SpecialToolAbilityHandler());
		MinecraftForge.EVENT_BUS.register(new ToolRepairHandler());
		MinecraftForge.EVENT_BUS.register(new HammerHandler());
		MinecraftForge.EVENT_BUS.register(new WeaponHandler());
		MinecraftForge.EVENT_BUS.register(new WorldHandler());
		
		ModMaterials.init();
		Toolbox.logger.log(Level.INFO,
				"Initialized tool part materials with " + Materials.head_registry.size() + " head materials, "
						+ Materials.haft_registry.size() + " haft materials, " + Materials.handle_registry.size()
						+ " handle materials, and " + Materials.adornment_registry.size() + " adornment materials");
		ModEntities.init();

		if (Loader.isModLoaded("tconstruct") && Config.ENABLE_TINKERS_COMPAT) {
			TConstructCompat.preInit();
		}
	}

	public void init(FMLInitializationEvent event) {

		NetworkRegistry.INSTANCE.registerGuiHandler(Toolbox.instance, new GuiProxy());

		if (Loader.isModLoaded("tconstruct") && Config.ENABLE_TINKERS_COMPAT) {
			TConstructCompat.init();
		}
		
		ModRecipes.init();
		// Toolbox.HEAD_CRAFTED.registerStat();

	}

	public void postInit(FMLPostInitializationEvent event) {

		if (config.hasChanged()) {
			config.save();
		}

		ModMaterials.initHeadRepairItems();
		
		thaumcraftLoaded = Loader.isModLoaded("thaumcraft");
		
		if (Config.DISABLE_VANILLA_TOOLS) {
			processRecipes();
			if (thaumcraftLoaded) {
				processThaumcraftRecipes();
				ThaumcraftCompat.fixThaumonomiconRecipeLists();
			}
		}
		
	}

	public static List<ResourceLocation> removed_recipes = new ArrayList<ResourceLocation>();
	
	protected void processRecipes() {
		if (ForgeRegistries.RECIPES instanceof IForgeRegistryModifiable) {
			IForgeRegistryModifiable registry = (IForgeRegistryModifiable) ForgeRegistries.RECIPES;
			
			Entry<ResourceLocation, IRecipe>[] recipeEntries = ForgeRegistries.RECIPES.getEntries().toArray(new Entry[ForgeRegistries.RECIPES.getEntries().size()]);
			for (int recipeIndex = 0; recipeIndex < recipeEntries.length; recipeIndex++) {
				Entry<ResourceLocation, IRecipe> recipeEntry = recipeEntries[recipeIndex];
				IRecipe recipe = recipeEntry.getValue();
				NonNullList<Ingredient> ingredients = recipe.getIngredients();
				
				Item output = recipe.getRecipeOutput().getItem();
				String rp = output.getRegistryName().getResourcePath();
				
				boolean exception = false;
				
				for (String s : Config.REMOVAL_EXCEPTIONS) {
					if (output.getRegistryName().toString().contains(s)) {
						exception = true;
					}
				}
				
				if (exception == false && 
						(output instanceof ItemHoe
						|| output instanceof ItemAxe
						|| output instanceof ItemSpade
						|| output instanceof ItemPickaxe
						|| output instanceof ItemSword)
						|| (output instanceof ItemTool && (rp.contains("pick") || rp.contains("axe") || rp.contains("shovel") || rp.contains("spade")))
						|| (output instanceof ItemTool && (output.getRegistryName().getResourceDomain().equals("thermalfoundation") && rp.contains("hammer")))) {
					String materialName = output instanceof ItemHoe ? ((ItemHoe) output).getMaterialName() : output instanceof ItemSword ? ((ItemSword) output).getToolMaterialName() : ((ItemTool) output).getToolMaterialName();
					
					if (Materials.canReplaceMaterial(materialName, recipe.getRecipeOutput())) {
						//System.out.println(materialName);
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
						System.out.println(output.getRegistryName() + " -> " + materialName);
					}
				} else {
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
							ingredients.set(i, new IngredientNBT(getToolReplacement(ingredientItem)) {});
						}
					}
				}
			}
		}
	}
	
	protected void processThaumcraftRecipes() {
		Entry<ResourceLocation, IThaumcraftRecipe>[] recipeEntries = ThaumcraftApi.getCraftingRecipes().entrySet().toArray(new Entry[ThaumcraftApi.getCraftingRecipes().entrySet().size()]);
		for (int recipeIndex = 0; recipeIndex < recipeEntries.length; recipeIndex++) {
			Entry<ResourceLocation, IThaumcraftRecipe> recipeEntry = recipeEntries[recipeIndex];
			if (recipeEntry.getValue() instanceof IRecipe) {
				IRecipe recipe = (IRecipe) recipeEntry.getValue();
				NonNullList<Ingredient> ingredients = recipe.getIngredients();
				
				Item output = recipe.getRecipeOutput().getItem();
				String rp = output.getRegistryName().getResourcePath();
				
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
						ingredients.set(i, new IngredientNBT(getToolReplacement(ingredientItem)) {});
					}
				}
			} else if (recipeEntry.getValue() instanceof InfusionRecipe) {
				InfusionRecipe recipe = (InfusionRecipe) recipeEntry.getValue();
				NonNullList<Ingredient> ingredients = recipe.getComponents();
				Ingredient input = recipe.getRecipeInput();
				
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
						ingredients.set(i, new IngredientNBT(getToolReplacement(ingredientItem)) {});
					}
				}
				ItemStack[] matchingStacks = input.getMatchingStacks();
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
					recipe.sourceInput = new IngredientNBT(getToolReplacement(ingredientItem)) {};
				}
			}
			
		}
		
		for (ResearchCategory category : ResearchCategories.researchCategories.values()) {
			for (ResearchEntry entry : category.research.values()) {
				for (ResearchStage stage : entry.getStages()) {
					Object[] craft = stage.getCraft();
					int[] craftRef = stage.getCraftReference();
					if (craft == null || craftRef == null || craft.length != craftRef.length) continue;
					for (int i = 0; i < craft.length; i++) {
						if (!(craft[i] instanceof ItemStack)) continue;
						
						ItemStack replacement = getToolReplacement(((ItemStack) craft[i]).getItem());
						if (!replacement.isEmpty()) {
							int replacementHash = ResearchManager.createItemStackHash(replacement);
							int originalHash = craftRef[i];
							
							stage.getCraft()[i] = replacement;
							stage.getCraftReference()[i] = replacementHash;
							if (ResearchManager.craftingReferences.contains(originalHash)) {
								ResearchManager.craftingReferences.remove(originalHash);
								ResearchManager.craftingReferences.add(replacementHash);
							}
						}
					}
				}
			}
		}
	}

	public ItemStack createWoodTool(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_WOOD.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createStoneTool(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_STONE.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createIronTool(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createDiamondTool(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_DIAMOND.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createGoldTool(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_GOLD.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}
	
	public ItemStack createThaumiumTool(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_THAUMIUM.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}
	
	public ItemStack createVoidTool(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, ModMaterials.HEAD_VOID.getName());
		tag.setString(IHaftTool.HAFT_TAG, ModMaterials.HAFT_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createWoodSword(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_WOOD.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_WOOD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createStoneSword(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_STONE.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_STONE.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createIronSword(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createDiamondSword(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_IRON.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_DIAMOND.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	public ItemStack createGoldSword(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_GOLD.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_GOLD.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}
	
	public ItemStack createThaumiumSword(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_THAUMIUM.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_THAUMIUM.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}
	
	public ItemStack createVoidSword(Item item) {
		ItemStack tool = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, ModMaterials.HEAD_VOID.getName());
		tag.setString(ICrossguardTool.CROSSGUARD_TAG, ModMaterials.HEAD_VOID.getName());
		tag.setString(IHandleTool.HANDLE_TAG, ModMaterials.HANDLE_WOOD.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, ModMaterials.ADORNMENT_NULL.getName());
		tool.setTagCompound(tag);
		return tool;
	}

	private ItemStack getToolReplacement(Item item) {
		if (item == null) return ItemStack.EMPTY;
		
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
		if (thaumcraftLoaded && item == ItemsTC.thaumiumPick) {
			return createThaumiumTool(ModItems.pickaxe);
		}
		if (thaumcraftLoaded && item == ItemsTC.voidPick) {
			return createVoidTool(ModItems.pickaxe);
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
		if (thaumcraftLoaded && item == ItemsTC.thaumiumAxe) {
			return createThaumiumTool(ModItems.axe);
		}
		if (thaumcraftLoaded && item == ItemsTC.voidAxe) {
			return createVoidTool(ModItems.axe);
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
			return createWoodTool(ModItems.shovel);
		}
		if (thaumcraftLoaded && item == ItemsTC.thaumiumShovel) {
			return createThaumiumTool(ModItems.shovel);
		}
		if (thaumcraftLoaded && item == ItemsTC.voidShovel) {
			return createVoidTool(ModItems.shovel);
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
		if (thaumcraftLoaded && item == ItemsTC.thaumiumHoe) {
			return createThaumiumTool(ModItems.hoe);
		}
		if (thaumcraftLoaded && item == ItemsTC.voidHoe) {
			return createVoidTool(ModItems.hoe);
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
		if (thaumcraftLoaded && item == ItemsTC.thaumiumSword) {
			return createThaumiumSword(ModItems.sword);
		}
		if (thaumcraftLoaded && item == ItemsTC.voidSword) {
			return createVoidSword(ModItems.sword);
		}

		return ItemStack.EMPTY;
	}

}

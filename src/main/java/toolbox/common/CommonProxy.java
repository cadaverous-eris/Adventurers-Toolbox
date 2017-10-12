package toolbox.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
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
import toolbox.common.recipes.ShapedNBTOreRecipe;
import toolbox.common.recipes.ShapelessNBTOreRecipe;

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
        Toolbox.logger.log(Level.INFO, "Initialized tool part materials with " + Materials.head_registry.size() + " head materials, " + Materials.haft_registry.size() + " haft materials, " + Materials.handle_registry.size() + " handle materials, and " + Materials.adornment_registry.size() + " adornment materials");
        ModItems.init();
        ModEntities.init();

    }

    public void init(FMLInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(Toolbox.instance, new GuiProxy());

        ModRecipes.init();

        //Toolbox.HEAD_CRAFTED.registerStat();

    }

    public void postInit(FMLPostInitializationEvent event) {

        if (config.hasChanged()) {
            config.save();
        }

        ModMaterials.initHeadRepairItems();

        if (Config.DISABLE_VANILLA_TOOLS) {
            //processRecipes();
        }

    }

    //Removed functionality temporarily
	/*
    protected void processRecipes() {
        List<IRecipe> toRemove = new ArrayList<IRecipe>();
        List<IRecipe> toAdd = new ArrayList<IRecipe>();

        RecipeSorter.register("toolbox:shapedNBT", ShapedNBTOreRecipe.class, RecipeSorter.Category.SHAPED,
                "after:forge:shapedore");
        RecipeSorter.register("toolbox:shapelessNBT", ShapelessNBTOreRecipe.class, RecipeSorter.Category.SHAPED,
                "after:forge:shapelessore");

        for (IRecipe recipe : CraftingManager.REGISTRY) {

            if (recipe.getRecipeOutput().getItem() instanceof ItemHoe
                    || recipe.getRecipeOutput().getItem() instanceof ItemAxe
                    || recipe.getRecipeOutput().getItem() instanceof ItemSpade
                    || recipe.getRecipeOutput().getItem() instanceof ItemPickaxe
                    || recipe.getRecipeOutput().getItem() instanceof ItemSword) {
                toRemove.add(recipe);
            } else if (recipe instanceof ShapelessRecipes) {
                NonNullList<Ingredient> ingredients = ((ShapelessRecipes) recipe).getIngredients();
                List<Ingredient> newIngredients = new ArrayList<>();
                boolean flag = false;
                for (Ingredient ingredient : ingredients) {
                    ItemStack[] stacks = ingredient.getMatchingStacks();
                    for (ItemStack stack : stacks) {
                        Item item = stack.getItem();
                        if (item instanceof ItemHoe || item instanceof ItemAxe || item instanceof ItemSpade
                                || item instanceof ItemPickaxe || item instanceof ItemSword) {
                            flag = true;
                            stack = getToolReplacement(item);
                        }
                    }
                    if (flag) {
                        newIngredients.add(Ingredient.fromStacks(stacks));
                    } else {
                        newIngredients.add(ingredient);
                    }
                }
                if (flag) {
                    toRemove.add(recipe);
                    toAdd.add(new ShapelessNBTOreRecipe(recipe.getRecipeOutput(), newIngredients.toArray()));
                }
            } else if (recipe instanceof ShapedRecipes) {
                NonNullList<Ingredient> ingredients = ((ShapedRecipes) recipe).getIngredients();
                List<Ingredient> newIngredients = new ArrayList<>();
                int width = ((ShapedRecipes) recipe).getRecipeWidth();
                int height = ((ShapedRecipes) recipe).getRecipeHeight();
                boolean flag = false;
                for (int i = 0; i < width; i++) {
                    if (inputs[i] == null || inputs[i].isEmpty()) {
                        newInputs[i] = inputs[i];
                    } else {
                        Item item = inputs[i].getItem();
                        if (item instanceof ItemHoe || item instanceof ItemAxe || item instanceof ItemSpade
                                || item instanceof ItemPickaxe || item instanceof ItemSword) {
                            flag = true;
                            newInputs[i] = getToolReplacement(item);
                        } else {
                            newInputs[i] = inputs[i];
                        }
                    }
                    if (flag) {
                        toRemove.add(recipe);
                        toAdd.add(new ShapedNBTOreRecipe(width, height, newInputs, recipe.getRecipeOutput()));
                    }
                }
            } else if (recipe instanceof ShapelessOreRecipe) {
                NonNullList<Object> inputs = ((ShapelessOreRecipe) recipe).getInput();
                Object[] newInputs = new Object[inputs.size()];
                boolean flag = false;
                for (int i = 0; i < inputs.size(); i++) {
                    if (inputs.get(i) instanceof ItemStack) {
                        Item item = ((ItemStack) inputs.get(i)).getItem();
                        if (item instanceof ItemHoe || item instanceof ItemAxe || item instanceof ItemSpade
                                || item instanceof ItemPickaxe || item instanceof ItemSword) {
                            flag = true;
                            newInputs[i] = getToolReplacement(item);
                        } else {
                            newInputs[i] = inputs.get(i);
                        }
                    } else {
                        newInputs[i] = inputs.get(i);
                    }
                }
                if (flag) {
                    toRemove.add(recipe);
                    toAdd.add(new ShapelessNBTOreRecipe(recipe.getRecipeOutput(), newInputs));
                }
            } else if (recipe instanceof ShapedOreRecipe) {
                Object[] inputs = ((ShapedOreRecipe) recipe).getInput();
                Object[] newInputs = new Object[inputs.length];
                boolean flag = false;
                for (int i = 0; i < inputs.length; i++) {
                    if (inputs[i] instanceof ItemStack) {
                        Item item = ((ItemStack) inputs[i]).getItem();
                        if (item instanceof ItemHoe || item instanceof ItemAxe || item instanceof ItemSpade
                                || item instanceof ItemPickaxe || item instanceof ItemSword) {
                            flag = true;
                            newInputs[i] = getToolReplacement(item);
                        } else {
                            newInputs[i] = inputs[i];
                        }
                    } else {
                        newInputs[i] = inputs[i];
                    }
                }
                if (flag) {
                    toRemove.add(recipe);
                    toAdd.add(new ShapedNBTOreRecipe(((ShapedOreRecipe) recipe).getWidth(),
                            ((ShapedOreRecipe) recipe).getHeight(), newInputs, recipe.getRecipeOutput()));
                }
            }

        }

        int i = 0;
        for (IRecipe recipe : toRemove) {
            if (CraftingManager.getInstance().getRecipeList().remove(recipe)) {
                i++;
            }
        }
        Toolbox.logger.log(Level.INFO, "Removed " + i + " crafting recipes");

        i = 0;
        for (IRecipe recipe : toAdd) {
            GameRegistry.addRecipe(recipe);
            i++;
        }
        Toolbox.logger.log(Level.INFO, "re-added " + i + " crafting recipes");

    }
*/


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
            return createDiamondTool(ModItems.PICKAXE);
        }
        if (item == Items.GOLDEN_PICKAXE) {
            return createGoldTool(ModItems.PICKAXE);
        }
        if (item == Items.IRON_PICKAXE) {
            return createIronTool(ModItems.PICKAXE);
        }
        if (item == Items.STONE_PICKAXE) {
            return createStoneTool(ModItems.PICKAXE);
        }
        if (item == Items.WOODEN_PICKAXE) {
            return createWoodTool(ModItems.PICKAXE);
        }
        if (item == Items.DIAMOND_AXE) {
            return createDiamondTool(ModItems.AXE);
        }
        if (item == Items.GOLDEN_AXE) {
            return createGoldTool(ModItems.AXE);
        }
        if (item == Items.IRON_AXE) {
            return createIronTool(ModItems.AXE);
        }
        if (item == Items.STONE_AXE) {
            return createStoneTool(ModItems.AXE);
        }
        if (item == Items.WOODEN_AXE) {
            return createWoodTool(ModItems.AXE);
        }
        if (item == Items.DIAMOND_SHOVEL) {
            return createDiamondTool(ModItems.SHOVEL);
        }
        if (item == Items.GOLDEN_SHOVEL) {
            return createGoldTool(ModItems.SHOVEL);
        }
        if (item == Items.IRON_SHOVEL) {
            return createIronTool(ModItems.SHOVEL);
        }
        if (item == Items.STONE_SHOVEL) {
            return createStoneTool(ModItems.SHOVEL);
        }
        if (item == Items.WOODEN_SHOVEL) {
            return createWoodTool(ModItems.HOE);
        }
        if (item == Items.DIAMOND_HOE) {
            return createDiamondTool(ModItems.HOE);
        }
        if (item == Items.GOLDEN_HOE) {
            return createGoldTool(ModItems.HOE);
        }
        if (item == Items.IRON_HOE) {
            return createIronTool(ModItems.HOE);
        }
        if (item == Items.STONE_HOE) {
            return createStoneTool(ModItems.HOE);
        }
        if (item == Items.WOODEN_HOE) {
            return createWoodTool(ModItems.HOE);
        }
        if (item == Items.DIAMOND_SWORD) {
            return createDiamondSword(ModItems.SWORD);
        }
        if (item == Items.GOLDEN_SWORD) {
            return createGoldSword(ModItems.SWORD);
        }
        if (item == Items.IRON_SWORD) {
            return createIronSword(ModItems.SWORD);
        }
        if (item == Items.STONE_SWORD) {
            return createStoneSword(ModItems.SWORD);
        }
        if (item == Items.WOODEN_SWORD) {
            return createWoodSword(ModItems.SWORD);
        }

        return ItemStack.EMPTY;
    }

}

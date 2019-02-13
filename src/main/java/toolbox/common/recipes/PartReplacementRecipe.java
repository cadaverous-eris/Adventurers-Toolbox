package toolbox.common.recipes;

import api.materials.AdornmentMaterial;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import toolbox.common.items.tools.IAdornedTool;

public class PartReplacementRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean foundTool = false;
		boolean foundPart = false;
		
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack temp = inv.getStackInSlot(i).copy();
			if (temp.isEmpty()) continue;
			Item item = temp.getItem();
			if (!foundTool && item instanceof IAdornedTool) {
				foundTool = true;
				continue;
			}
			if (!foundPart) {
				for (ItemStack test : ModRecipes.adornment_map.keySet()) {
					if (!foundPart && ItemStack.areItemsEqual(test, temp) && ItemStack.areItemStackTagsEqual(test, temp)) {
						foundPart = true;
					}
				}
				if (foundPart) continue;
			}
			if (!foundTool && !foundPart) return false;
		}
		
		return (foundTool && foundPart);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack oldTool = ItemStack.EMPTY;
		ItemStack newPart = ItemStack.EMPTY;
		
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack temp = inv.getStackInSlot(i);
			if (temp.isEmpty()) continue;
			Item item = temp.getItem();
			if (oldTool.isEmpty() && item instanceof IAdornedTool) {
				oldTool = temp.copy();
				continue;
			}
			if (newPart.isEmpty()) {
				for (ItemStack test : ModRecipes.adornment_map.keySet()) {
					if (newPart.isEmpty() && ItemStack.areItemsEqual(test, temp) && ItemStack.areItemStackTagsEqual(test, temp)) {
						newPart = test;
					}
				}
				if (!newPart.isEmpty()) continue;
			}
		}
		
		if (oldTool.isEmpty() || newPart.isEmpty()) return ItemStack.EMPTY;
		
		ItemStack newTool = oldTool.copy();
		AdornmentMaterial oldMat = IAdornedTool.getAdornmentMat(oldTool);
		AdornmentMaterial newMat = ModRecipes.adornment_map.get(newPart);
		
		if (oldMat == newMat || newMat == null) return ItemStack.EMPTY;
		
		setPartMaterial(newTool, IAdornedTool.ADORNMENT_TAG, newMat.getName());
		
		int oldDamage = oldTool.getItemDamage();
		if (oldDamage > newTool.getMaxDamage()) return ItemStack.EMPTY;
		
		newTool.setItemDamage(oldDamage);
		
		return newTool;
	}
	
	protected void setPartMaterial(ItemStack tool, String partType, String mat) {
		NBTTagCompound tag = tool.getTagCompound();
		if (tag == null) tag = new NBTTagCompound();
		tag.setString(partType, mat);
		tool.setTagCompound(tag);
	}

	@Override
	public boolean canFit(int width, int height) {
		return width > 1 || height > 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList items = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		return items;
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}

}

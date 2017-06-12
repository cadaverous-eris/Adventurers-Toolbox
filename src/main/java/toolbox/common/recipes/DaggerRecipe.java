package toolbox.common.recipes;

import java.util.ArrayList;
import java.util.List;

import api.materials.AdornmentMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import toolbox.common.items.ModItems;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.ICrossguardTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.materials.ModMaterials;

public class DaggerRecipe extends ToolRecipe {

	private HeadMaterial bladeMat = null;
	private HandleMaterial handleMat = null;
	private AdornmentMaterial adornmentMat = null;

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		bladeMat = null;
		handleMat = null;
		adornmentMat = null;
		List<ItemStack> items = new ArrayList<ItemStack>();
		boolean[] slots = new boolean[inv.getSizeInventory()];
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack temp = inv.getStackInSlot(i).copy();
			if (!temp.isEmpty()) {
				if (!slots[i] && bladeMat == null && temp.getItem() == ModItems.DAGGER_BLADE) {
					for (ItemStack test : ModRecipes.head_map.keySet()) {
						if (bladeMat == null && ItemStack.areItemsEqual(test, temp) && ItemStack.areItemStackTagsEqual(test, temp)) {
							bladeMat = ModRecipes.head_map.get(test);
							slots[i] = true;
						}
					}
				}
				if (!slots[i] && handleMat == null) {
					for (ItemStack test : ModRecipes.handle_map.keySet()) {
						if (handleMat == null && ItemStack.areItemsEqual(test, temp) && ItemStack.areItemStackTagsEqual(test, temp)) {
							handleMat = ModRecipes.handle_map.get(test);
							slots[i] = true;
						}
					}
				}
				if (!slots[i] && adornmentMat == null) {
					for (ItemStack test : ModRecipes.adornment_map.keySet()) {
						if (adornmentMat == null && ItemStack.areItemsEqual(test, temp) && ItemStack.areItemStackTagsEqual(test, temp)) {
							adornmentMat = ModRecipes.adornment_map.get(test);
							slots[i] = true;
						}
					}
				}
				items.add(temp);
			}
		}
		if (items.size() > getRecipeSize() || (adornmentMat == null && items.size() > getRecipeSize() - 1)) {
			return false;
		}
		
		if (bladeMat == null || handleMat == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
ItemStack out = new ItemStack(ModItems.DAGGER);
		
		if (adornmentMat == null) {
			adornmentMat = ModMaterials.ADORNMENT_NULL;
		}
		
		if (bladeMat == null || handleMat == null || adornmentMat == null) {
			return ItemStack.EMPTY;
		}
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IBladeTool.BLADE_TAG, bladeMat.getName());
		tag.setString(IHandleTool.HANDLE_TAG, handleMat.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, adornmentMat.getName());
		out.setTagCompound(tag);
		
		return out;
	}

	@Override
	public int getRecipeSize() {
		return 3;
	}

}

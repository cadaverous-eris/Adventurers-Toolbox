package toolbox.common.recipes;

import java.util.ArrayList;
import java.util.List;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import toolbox.common.items.ModItems;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
import toolbox.common.items.tools.ItemPickaxe;
import toolbox.common.materials.ModMaterials;

public class PickaxeRecipe extends ToolRecipe {
	
	private HeadMaterial headMat = null;
	private HaftMaterial haftMat = null;
	private HandleMaterial handleMat = null;
	private AdornmentMaterial adornmentMat = null;

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		headMat = null;
		haftMat = null;
		handleMat = null;
		adornmentMat = null;
		List<ItemStack> items = new ArrayList<ItemStack>();
		boolean[] slots = new boolean[inv.getSizeInventory()];
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack temp = inv.getStackInSlot(i).copy();
			if (!temp.isEmpty()) {
				if (!slots[i] && headMat == null && temp.getItem() == ModItems.pickaxe_head) {
					for (ItemStack test : ModRecipes.head_map.keySet()) {
						if (headMat == null && ItemStack.areItemsEqual(test, temp) && ItemStack.areItemStackTagsEqual(test, temp)) {
							headMat = ModRecipes.head_map.get(test);
							slots[i] = true;
						}
					}
				}
				if (!slots[i] && haftMat == null) {
					for (ItemStack test : ModRecipes.haft_map.keySet()) {
						if (haftMat == null && ItemStack.areItemsEqual(test, temp) && ItemStack.areItemStackTagsEqual(test, temp)) {
							haftMat = ModRecipes.haft_map.get(test);
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
		if (items.size() > getRecipeSize()) {
			return false;
		}
		
		if (headMat == null || haftMat == null || handleMat == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack out = new ItemStack(ModItems.pickaxe);
		
		if (adornmentMat == null) {
			adornmentMat = ModMaterials.ADORNMENT_NULL;
		}
		
		if (headMat == null || haftMat == null || handleMat == null || adornmentMat == null) {
			return ItemStack.EMPTY;
		}
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(IHeadTool.HEAD_TAG, headMat.getName());
		tag.setString(IHaftTool.HAFT_TAG, haftMat.getName());
		tag.setString(IHandleTool.HANDLE_TAG, handleMat.getName());
		tag.setString(IAdornedTool.ADORNMENT_TAG, adornmentMat.getName());
		out.setTagCompound(tag);
		
		return out;
	}

        @Override
        public boolean canFit(int width, int height) {
            return width * height >= getRecipeSize();
        }

        public int getRecipeSize() {
            if(adornmentMat == null) {
                return 3;
            }
            return 4;
        }

}

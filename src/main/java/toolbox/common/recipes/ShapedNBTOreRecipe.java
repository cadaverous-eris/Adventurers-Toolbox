package toolbox.common.recipes;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedNBTOreRecipe extends ShapedOreRecipe {

	public ShapedNBTOreRecipe(ItemStack result, Object[] recipe) {
		super(null, result, recipe);
	}
	
	public ShapedNBTOreRecipe(int width, int height, Object[] input, ItemStack output) {
		super(null, output, "EEE", "EEE", "EEE", 'E', ItemStack.EMPTY);
		this.width = width;
		this.height = height;
		this.input = input;
		this.output = output;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
		for (int x = 0; x < inv.getWidth(); x++) {
			for (int y = 0; y < inv.getHeight(); y++) {
				int subX = x - startX;
				int subY = y - startY;
				Object target = null;

				if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
					if (mirror) {
						target = input[width - subX - 1 + subY * width];
					} else {
						target = input[subX + subY * width];
					}
				}

				ItemStack slot = inv.getStackInRowAndColumn(x, y);

				if (target instanceof ItemStack) {
					if (!OreDictionary.itemMatches((ItemStack) target, slot, false)
							|| !ItemStack.areItemStackTagsEqual((ItemStack) target, slot)) {
						return false;
					}
				} else if (target instanceof List) {
					boolean matched = false;

					Iterator<ItemStack> itr = ((List<ItemStack>) target).iterator();
					while (itr.hasNext() && !matched) {
						matched = OreDictionary.itemMatches(itr.next(), slot, false);
					}

					if (!matched) {
						return false;
					}
				} else if (target == null && !slot.isEmpty()) {
					return false;
				}
			}
		}

		return true;
	}

}
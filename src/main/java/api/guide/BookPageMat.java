package api.guide;

import java.util.List;
import java.util.Set;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.PartMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import toolbox.common.recipes.ModRecipes;

public class BookPageMat extends BookPage {

	private final PartMaterial material;
	private final String description;

	public BookPageMat(PartMaterial mat) {
		super("guide.mat." + mat.getName() + ".name");
		this.material = mat;
		this.description = "guide.mat." + mat.getName() + ".desc";
	}

	public PartMaterial getMat() {
		return this.material;
	}

	public ItemStack getDisplayStack() {
		ItemStack stack = ItemStack.EMPTY;
		if (material instanceof HeadMaterial) {
			List<ItemStack> ores = OreDictionary.getOres(((HeadMaterial) material).getCraftingItem());
			if (ores.size() <= 0) {
				stack = ItemStack.EMPTY;
			} else {
				stack = ores.get(0);
			}

			if (stack.getMetadata() == 32767) {
				stack = new ItemStack(stack.getItem(), 1, 0, stack.getTagCompound());
			}
		} else if (material instanceof HaftMaterial) {
			Set<ItemStack> keys = ModRecipes.haft_map.keySet();
			for (ItemStack key : keys) {
				if (ModRecipes.haft_map.get(key) == material) {
					stack = key.copy();
					break;
				}
			}
		} else if (material instanceof HandleMaterial) {
			Set<ItemStack> keys = ModRecipes.handle_map.keySet();
			for (ItemStack key : keys) {
				if (ModRecipes.handle_map.get(key) == material) {
					stack = key.copy();
					break;
				}
			}
		} else if (material instanceof AdornmentMaterial) {
			Set<ItemStack> keys = ModRecipes.adornment_map.keySet();
			for (ItemStack key : keys) {
				if (ModRecipes.adornment_map.get(key) == material) {
					stack = key.copy();
					break;
				}
			}
		}
		return stack;
	}
	
	public String getDescription() {
		return this.description;
	}

}

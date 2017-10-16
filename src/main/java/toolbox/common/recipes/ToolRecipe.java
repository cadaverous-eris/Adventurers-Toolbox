package toolbox.common.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class ToolRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

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

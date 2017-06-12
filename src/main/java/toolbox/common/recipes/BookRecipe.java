package toolbox.common.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import toolbox.common.items.ModItems;
import toolbox.common.items.parts.ItemToolHead;

public class BookRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean book = false;
		boolean head = false;
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (!inv.getStackInSlot(i).isEmpty()) {
				if (inv.getStackInSlot(i).getItem() instanceof ItemToolHead) {
					head = true;
				} else if (inv.getStackInSlot(i).getItem() == Items.BOOK) {
					book = true;
				}
				items.add(inv.getStackInSlot(i));
			}
		}
		
		if (items.size() != 2) {
			return false;
		}
		
		return book && head;

	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return getRecipeOutput();
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.GUIDE_BOOK);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
	}

}

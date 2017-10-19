package toolbox.compat.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import toolbox.common.items.ModItems;
import toolbox.common.recipes.ModRecipes;

public class GuideBookRecipeWrapper implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final ItemStack output;

	public GuideBookRecipeWrapper() {
		this.inputs = new ArrayList<List<ItemStack>>();
		this.output = new ItemStack(ModItems.guide_book);

		List<ItemStack> heads = new ArrayList<ItemStack>();
		for (ItemStack stack : ModRecipes.head_map.keySet()) {
			heads.add(stack);
		}
		this.inputs.add(heads);
		
		List<ItemStack> book = new ArrayList<ItemStack>();
		book.add(new ItemStack(Items.BOOK));
		this.inputs.add(book);

	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}

}

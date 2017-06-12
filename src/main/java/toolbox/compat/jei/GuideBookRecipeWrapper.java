package toolbox.compat.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import toolbox.common.items.ItemBase;
import toolbox.common.items.ModItems;
import toolbox.common.items.parts.ItemToolHead;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.ICrossguardTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
import toolbox.common.recipes.ModRecipes;

public class GuideBookRecipeWrapper extends BlankRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final List<List<ItemStack>> output;

	public GuideBookRecipeWrapper() {
		this.inputs = new ArrayList<List<ItemStack>>();
		this.output = new ArrayList<List<ItemStack>>();

		List<ItemStack> heads = new ArrayList<ItemStack>();
		for (ItemStack stack : ModRecipes.head_map.keySet()) {
			heads.add(stack);
		}
		this.inputs.add(heads);
		
		List<ItemStack> book = new ArrayList<ItemStack>();
		book.add(new ItemStack(Items.BOOK));
		this.inputs.add(book);

		this.output.add(new ArrayList<ItemStack>());
		this.output.get(0).add(new ItemStack(ModItems.GUIDE_BOOK));

	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutputLists(ItemStack.class, output);
	}

}

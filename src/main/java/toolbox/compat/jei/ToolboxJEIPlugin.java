package toolbox.compat.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import toolbox.common.items.ModItems;

@JEIPlugin
public class ToolboxJEIPlugin extends BlankModPlugin {
	
	public static IRecipeRegistry recipeRegistry;
	
	@Nullable
	private ISubtypeRegistry subtypeRegistry;
	
	@Override
	public void register(IModRegistry reg) {
		IJeiHelpers helper = reg.getJeiHelpers();
		IGuiHelper guiHelper = helper.getGuiHelper();
		
		List<IRecipeWrapper> bookRecipe = new ArrayList<IRecipeWrapper>();
		bookRecipe.add(new GuideBookRecipeWrapper());
		reg.addRecipes(bookRecipe, VanillaRecipeCategoryUid.CRAFTING);
		
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		this.subtypeRegistry = subtypeRegistry;
		subtypeRegistry.useNbtForSubtypes(

		);
	}
	
	@Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        recipeRegistry = iJeiRuntime.getRecipeRegistry();
    }

}

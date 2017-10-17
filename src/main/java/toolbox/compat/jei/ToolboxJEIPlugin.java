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
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.Item;
import toolbox.common.items.ModItems;
import toolbox.common.recipes.BookRecipe;

@JEIPlugin
public class ToolboxJEIPlugin extends BlankModPlugin {
	
	public static IRecipeRegistry recipeRegistry;
	
	@Nullable
	private ISubtypeRegistry subtypeRegistry;
	
	@Override
	public void register(IModRegistry reg) {
		IJeiHelpers helper = reg.getJeiHelpers();
		IGuiHelper guiHelper = helper.getGuiHelper();
		
		reg.handleRecipes(BookRecipe.class, new IRecipeWrapperFactory<BookRecipe>() {

			@Override
			public IRecipeWrapper getRecipeWrapper(BookRecipe recipe) {
				return new GuideBookRecipeWrapper();
			}
			
		}, VanillaRecipeCategoryUid.CRAFTING);
		
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		this.subtypeRegistry = subtypeRegistry;
		List<Item> nbtSubtypeItems = new ArrayList<Item>();
		if (ModItems.schematic != null) nbtSubtypeItems.add(ModItems.schematic);
		if (ModItems.cast != null) nbtSubtypeItems.add(ModItems.cast);
		if (ModItems.axe != null) nbtSubtypeItems.add(ModItems.axe);
		if (ModItems.pickaxe != null) nbtSubtypeItems.add(ModItems.pickaxe);
		if (ModItems.shovel != null) nbtSubtypeItems.add(ModItems.shovel);
		if (ModItems.hoe != null) nbtSubtypeItems.add(ModItems.hoe);
		if (ModItems.handpick != null) nbtSubtypeItems.add(ModItems.handpick);
		if (ModItems.climbing_pick != null) nbtSubtypeItems.add(ModItems.climbing_pick);
		if (ModItems.hammer != null) nbtSubtypeItems.add(ModItems.hammer);
		if (ModItems.sword != null) nbtSubtypeItems.add(ModItems.sword);
		if (ModItems.dagger != null) nbtSubtypeItems.add(ModItems.dagger);
		if (ModItems.mace != null) nbtSubtypeItems.add(ModItems.mace);
		
		subtypeRegistry.useNbtForSubtypes(
			nbtSubtypeItems.toArray(new Item[nbtSubtypeItems.size()])
		);
	}
	
	@Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        recipeRegistry = iJeiRuntime.getRecipeRegistry();
    }

}

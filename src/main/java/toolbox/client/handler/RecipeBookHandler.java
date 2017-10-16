package toolbox.client.handler;

import java.util.Iterator;

import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.Toolbox;
import toolbox.common.CommonProxy;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Toolbox.MODID, value = { Side.CLIENT })
public class RecipeBookHandler {

	@SubscribeEvent
	public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			for (RecipeList recipeList : RecipeBookClient.ALL_RECIPES) {
				Iterator<IRecipe> it = recipeList.getRecipes().iterator();
				while (it.hasNext()) {
					IRecipe recipe = it.next();
					if (CommonProxy.removed_recipes.contains(recipe.getRegistryName())) {
						it.remove();
					}
				}
			}
		}
	}
	
}

package toolbox.compat.thaumcraft;

import java.util.ArrayList;
import java.util.Map.Entry;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.api.research.ResearchStage;
import thaumcraft.common.config.ConfigRecipes;
import toolbox.Toolbox;

public class ThaumcraftCompat {
	
	public static void fixThaumonomiconRecipeLists() {
		for (ResearchCategory researchCategory : ResearchCategories.researchCategories.values()) {
			for (ResearchEntry researchEntry : researchCategory.research.values()) {
				for (ResearchStage stage : researchEntry.getStages()) {
					if (stage.getRecipes() != null) {
						for (ResourceLocation recipeLoc : stage.getRecipes()) {
							
						}
					}
				}
			}
		}
		
		for (Entry<String, ArrayList<ResourceLocation>> recipeGroup : ConfigRecipes.recipeGroups.entrySet()) {
			ArrayList<ResourceLocation> recipeLocs = recipeGroup.getValue();
			
			for (int i = recipeLocs.size() - 1; i >= 0; i--) {
				ResourceLocation r = recipeLocs.get(i);
				if (r.getResourcePath().equals("thaumiumshovel")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "shovel_head_thaumium"));
				} else if (r.getResourcePath().equals("thaumiumpick")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "pickaxe_head_thaumium"));
				} else if (r.getResourcePath().equals("thaumiumaxe")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "axe_head_thaumium"));
				} else if (r.getResourcePath().equals("thaumiumhoe")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "hoe_head_thaumium"));
				} else if (r.getResourcePath().equals("thaumiumsword")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "sword_blade_thaumium"));
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "crossguard_thaumium"));
				} else if (r.getResourcePath().equals("voidshovel")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "shovel_head_void"));
				} else if (r.getResourcePath().equals("voidpick")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "pickaxe_head_void"));
				} else if (r.getResourcePath().equals("voidaxe")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "axe_head_void"));
				} else if (r.getResourcePath().equals("voidhoe")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "hoe_head_void"));
				} else if (r.getResourcePath().equals("voidsword")) {
					recipeLocs.remove(i);
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "sword_blade_void"));
					recipeLocs.add(new ResourceLocation(Toolbox.MODID, "crossguard_void"));
				}
			}
		}
	}

}

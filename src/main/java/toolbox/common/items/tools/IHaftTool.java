package toolbox.common.items.tools;

import api.materials.HaftMaterial;
import api.materials.Materials;
import net.minecraft.item.ItemStack;
import toolbox.common.materials.ModMaterials;

public interface IHaftTool {
	
	public static final String HAFT_TAG = "Haft";
	
	public static HaftMaterial getHaftMat(ItemStack stack) {
		HaftMaterial mat = ModMaterials.HAFT_WOOD;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(HAFT_TAG)) {
			if (Materials.haft_registry.get(stack.getTagCompound().getString(HAFT_TAG)) != null) {
				mat = Materials.haft_registry.get(stack.getTagCompound().getString(HAFT_TAG));
			}
		}
		return mat;
	}

}

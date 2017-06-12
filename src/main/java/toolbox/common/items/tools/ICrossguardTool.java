package toolbox.common.items.tools;

import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.item.ItemStack;
import toolbox.common.materials.ModMaterials;

public interface ICrossguardTool {
	
	public static final String CROSSGUARD_TAG = "Crossguard";
	
	public static HeadMaterial getCrossguardMat(ItemStack stack) {
		HeadMaterial mat = ModMaterials.HEAD_WOOD;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(CROSSGUARD_TAG)) {
			if (Materials.head_registry.get(stack.getTagCompound().getString(CROSSGUARD_TAG)) != null) {
				mat = Materials.head_registry.get(stack.getTagCompound().getString(CROSSGUARD_TAG));
			}
		}
		return mat;
	}

}

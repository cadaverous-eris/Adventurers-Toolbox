package toolbox.common.items.tools;

import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.item.ItemStack;
import toolbox.common.materials.ModMaterials;

public interface IBladeTool {
	
	public static final String BLADE_TAG = "Blade";
	
	public static HeadMaterial getBladeMat(ItemStack stack) {
		HeadMaterial mat = ModMaterials.HEAD_WOOD;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(BLADE_TAG)) {
			if (Materials.head_registry.get(stack.getTagCompound().getString(BLADE_TAG)) != null) {
				mat = Materials.head_registry.get(stack.getTagCompound().getString(BLADE_TAG));
			}
		}
		return mat;
	}

}

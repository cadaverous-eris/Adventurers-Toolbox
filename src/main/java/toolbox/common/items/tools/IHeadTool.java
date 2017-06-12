package toolbox.common.items.tools;

import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.item.ItemStack;
import toolbox.common.materials.ModMaterials;

public interface IHeadTool {

	public static final String HEAD_TAG = "Head";
	
	public static HeadMaterial getHeadMat(ItemStack stack) {
		HeadMaterial mat = ModMaterials.HEAD_WOOD;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(HEAD_TAG)) {
			if (Materials.head_registry.get(stack.getTagCompound().getString(HEAD_TAG)) != null) {
				mat = Materials.head_registry.get(stack.getTagCompound().getString(HEAD_TAG));
			}
		}
		return mat;
	}
	
}

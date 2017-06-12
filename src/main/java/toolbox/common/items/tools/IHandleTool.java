package toolbox.common.items.tools;

import api.materials.HandleMaterial;
import api.materials.Materials;
import net.minecraft.item.ItemStack;
import toolbox.common.materials.ModMaterials;

public interface IHandleTool {
	
	public static final String HANDLE_TAG = "Handle";
	
	public static HandleMaterial getHandleMat(ItemStack stack) {
		HandleMaterial mat = ModMaterials.HANDLE_WOOD;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(HANDLE_TAG)) {
			if (Materials.handle_registry.get(stack.getTagCompound().getString(HANDLE_TAG)) != null) {
				mat = Materials.handle_registry.get(stack.getTagCompound().getString(HANDLE_TAG));
			}
		}
		return mat;
	}

}

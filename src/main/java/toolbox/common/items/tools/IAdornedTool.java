package toolbox.common.items.tools;

import api.materials.AdornmentMaterial;
import api.materials.Materials;
import net.minecraft.item.ItemStack;
import toolbox.common.materials.ModMaterials;

public interface IAdornedTool {
	
	public static final String ADORNMENT_TAG = "ADORNMENT";
	
	public static AdornmentMaterial getAdornmentMat(ItemStack stack) {
		AdornmentMaterial mat = ModMaterials.ADORNMENT_NULL;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ADORNMENT_TAG)) {
			if (Materials.adornment_registry.get(stack.getTagCompound().getString(ADORNMENT_TAG)) != null) {
				mat = Materials.adornment_registry.get(stack.getTagCompound().getString(ADORNMENT_TAG));
			}
		}
		return mat;
	}

}

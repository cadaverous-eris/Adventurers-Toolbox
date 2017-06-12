package toolbox.common.items.parts;

import java.util.ArrayList;
import java.util.List;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import net.minecraft.util.IStringSerializable;
import toolbox.common.items.ItemBase;

public abstract class ItemToolPart extends ItemBase {

	public ItemToolPart(String name) {
		super(name);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

}

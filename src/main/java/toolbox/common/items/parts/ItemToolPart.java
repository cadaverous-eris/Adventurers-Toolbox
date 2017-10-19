package toolbox.common.items.parts;

import toolbox.common.items.ItemBase;

public abstract class ItemToolPart extends ItemBase {

	public ItemToolPart(String name) {
		super(name);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

}

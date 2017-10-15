package toolbox.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import toolbox.Toolbox;

public class ItemBase extends Item {

	public ItemBase(String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(Toolbox.MODID + "." + name);
		setCreativeTab(Toolbox.partsTab);
	}

	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().toString()));
	}

}

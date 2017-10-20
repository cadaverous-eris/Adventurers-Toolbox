package toolbox.common.items;

import java.util.UUID;

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
	
	public static UUID getAttackDamageUUID() {
		return Item.ATTACK_DAMAGE_MODIFIER;
	}
	
	public static UUID getAttackSpeedUUID() {
		return Item.ATTACK_SPEED_MODIFIER;
	}

}

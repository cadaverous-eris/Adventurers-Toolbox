package toolbox.common.items.parts;

import java.util.ArrayList;
import java.util.List;

import api.materials.HandleMaterial;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolHandle extends ItemToolPart {
	
	private List<String> subItems;

	public ItemToolHandle() {
		super("handle");
		subItems = new ArrayList<String>();
		subItems.add("cloth");
		subItems.add("leather");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + subItems.get(stack.getMetadata());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < this.subItems.size(); i++) {
			ItemStack stack = new ItemStack(this, 1, i);
			if (isInCreativeTab(tab)) {
				subItems.add(stack);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		for (int i = 0; i < subItems.size(); i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(getRegistryName().toString() + "_" + subItems.get(i), "inventory"));
		}
	}

}

package toolbox.common.items.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import toolbox.Toolbox;
import toolbox.common.Config;
import toolbox.common.recipes.ModRecipes;

public class ItemToolHead extends ItemToolPart {

	// This could be a list, but I made it a map because later I want to add an
	// int ID to all materials
	public Map<Integer, HeadMaterial> meta_map = new HashMap<Integer, HeadMaterial>();

	protected void initMats() {
		int i = 0;
		for (HeadMaterial mat : Materials.head_registry.values()) {
			meta_map.put(i, mat);
			i++;
		}
	}

	public ItemToolHead(String name) {
		super(name);
		initMats();
		registerMatRecipes();
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < meta_map.size(); i++) {
			if (!Config.HIDE_UNCRAFTABLE_HEADS || (OreDictionary.getOres(meta_map.get(i).getCraftingItem()).size() > 0
					&& OreDictionary.getOres(meta_map.get(i).getSmallCraftingItem()).size() > 0)) {
				ItemStack stack = new ItemStack(this, 1, i);
				subItems.add(stack);
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		HeadMaterial mat = meta_map.get(stack.getMetadata());

		if (mat == null) {
			return super.getItemStackDisplayName(stack);
		}

		return I18n.translateToLocal(mat.getName() + ".name") + " " + super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		for (int i : meta_map.keySet()) {
			String domain = meta_map.get(i).getModId();
			String path = "th_" + getRegistryName().getResourcePath();
			ModelLoader.setCustomModelResourceLocation(this, i,
					new ModelResourceLocation(domain + ":" + path + "_" + meta_map.get(i).getName(), "inventory"));
		}
	}

	public HeadMaterial getMatFromMeta(int meta) {
		return meta_map.get(meta);
	}

	public HeadMaterial getMatFromMeta(ItemStack stack) {
		return meta_map.get(stack.getMetadata());
	}

	public void registerMatRecipes() {

		for (int i : meta_map.keySet()) {
			ModRecipes.head_map.put(new ItemStack(this, 1, i), meta_map.get(i));
		}

	}

}

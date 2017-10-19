package toolbox.common.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSchematic extends ItemBase {

	public static final List<String> subtypes = new ArrayList<String>();
	public static final String type_tag = "Type";
	
	public ItemSchematic() {
		super("schematic");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (String type : subtypes) {
			ItemStack stack = new ItemStack(this);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString(type_tag, type);
			stack.setTagCompound(nbt);
			if (isInCreativeTab(tab)) {
				subItems.add(stack);
			}
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(type_tag)) {
			return super.getItemStackDisplayName(stack) + " (" + I18n.translateToLocal("item.toolbox." + stack.getTagCompound().getString(type_tag) + ".name") + ")";
		}
		return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack ret = stack.copy();
		ret.setCount(1);
		return ret;
	}
	
	public ItemStack createStack(String type) {
		ItemStack stack = new ItemStack(this);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString(type_tag, type);
		stack.setTagCompound(nbt);
		return stack;
	}

}

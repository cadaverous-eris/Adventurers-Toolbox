package api.guide;

import api.materials.Materials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.ICrossguardTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;

public class BookPageTool extends BookPageItemDisplay {
	
	Item item;

	public BookPageTool(String title, String desc, Item item) {
		super(title, desc, ItemStack.EMPTY);
		this.item = item;
	}
	
	@Override
	public ItemStack getDisplayStack() {
		ItemStack stack = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		
		if (item instanceof IHeadTool) {
			tag.setString(IHeadTool.HEAD_TAG, Materials.randomHead().getName());
		}
		if (item instanceof IBladeTool) {
			tag.setString(IBladeTool.BLADE_TAG, Materials.randomHead().getName());
		}
		if (item instanceof ICrossguardTool) {
			tag.setString(ICrossguardTool.CROSSGUARD_TAG, Materials.randomHead().getName());
		}
		if (item instanceof IHaftTool) {
			tag.setString(IHaftTool.HAFT_TAG, Materials.randomHaft().getName());
		}
		if (item instanceof IHandleTool) {
			tag.setString(IHandleTool.HANDLE_TAG, Materials.randomHandle().getName());
		}
		if (item instanceof IAdornedTool) {
			tag.setString(IAdornedTool.ADORNMENT_TAG, Materials.randomAdornment().getName());
		}
		stack.setTagCompound(tag);
		return stack;
	}

}

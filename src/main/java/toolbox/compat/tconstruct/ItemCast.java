package toolbox.compat.tconstruct;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.common.items.ItemBase;
import toolbox.common.items.ModItems;
import toolbox.common.items.parts.ItemToolHead;

public class ItemCast extends ItemBase {

	private static final int ingotCost = 144;
	private static final int nuggetCost = ingotCost / 9;

	public ItemCast() {
		super("cast");
	}

	public static ItemStack getStack(EnumType type, int count) {
		return new ItemStack(ModItems.cast, count, type.getMetadata());
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + EnumType.VALUES[stack.getMetadata()].getName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (EnumType type : EnumType.values()) {
				items.add(getStack(type, 1));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		for (EnumType type : EnumType.VALUES) {
			ModelLoader.setCustomModelResourceLocation(this, type.getMetadata(), new ModelResourceLocation(getRegistryName().toString() + "_" + type.getName(), "inventory"));
		}
	}

	public enum EnumType {

		PICKAXE_HEAD(ModItems.pickaxe_head, ingotCost * 3 + nuggetCost * 2),
		AXE_HEAD(ModItems.axe_head, ingotCost * 3 + nuggetCost * 1),
		SHOVEL_HEAD(ModItems.shovel_head, ingotCost * 1 + nuggetCost * 3),
		HOE_HEAD(ModItems.hoe_head, ingotCost * 2 + nuggetCost * 1),
		CLIMBING_PICK_HEAD(ModItems.climbing_pick_head, ingotCost * 2 + nuggetCost * 2),
		HANDPICK_HEAD(ModItems.handpick_head, ingotCost * 1 + nuggetCost * 2),
		HAMMER_HEAD(ModItems.hammer_head, ingotCost * 7 + nuggetCost * 2),
		SWORD_BLADE(ModItems.sword_blade, ingotCost * 2 + nuggetCost * 3),
		SWORD_CROSSGUARD(ModItems.sword_crossguard, ingotCost * 1 + nuggetCost * 3),
		DAGGER_BLADE(ModItems.dagger_blade, ingotCost * 1 + nuggetCost * 3),
		MACE_HEAD(ModItems.mace_head, ingotCost * 5 + nuggetCost * 4);

		public final static EnumType[] VALUES = values();

		private final ItemToolHead item;
		private final int cost;

		EnumType(ItemToolHead item, int nuggetCost) {
			this.item = item;
			this.cost = nuggetCost;
		}

		int getMetadata() {
			return this.ordinal();
		}

		String getName() {
			return this.name().toLowerCase();
		}

		public ItemToolHead getItem() {
			return item;
		}

		public int getCost() {
			return cost;
		}

	}
}

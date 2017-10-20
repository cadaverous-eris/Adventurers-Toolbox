package toolbox.compat.tconstruct;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.common.Config;
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
				if(!Config.DISABLED_TOOLS.contains(type.getTool())) {
					items.add(getStack(type, 1));
				}
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

		PICKAXE_HEAD(ModItems.pickaxe_head, "pickaxe", ingotCost * 3 + nuggetCost * 2),
		AXE_HEAD(ModItems.axe_head, "axe", ingotCost * 3 + nuggetCost * 1),
		SHOVEL_HEAD(ModItems.shovel_head, "shovel", ingotCost * 1 + nuggetCost * 3),
		HOE_HEAD(ModItems.hoe_head, "hoe", ingotCost * 2 + nuggetCost * 1),
		HANDPICK_HEAD(ModItems.handpick_head, "handpick", ingotCost * 1 + nuggetCost * 2),
		HAMMER_HEAD(ModItems.hammer_head, "hammer", ingotCost * 7 + nuggetCost * 2),
		SWORD_BLADE(ModItems.sword_blade, "sword", ingotCost * 2 + nuggetCost * 3),
		SWORD_CROSSGUARD(ModItems.sword_crossguard, "sword", ingotCost * 1 + nuggetCost * 3),
		DAGGER_BLADE(ModItems.dagger_blade, "dagger", ingotCost * 1 + nuggetCost * 3),
		MACE_HEAD(ModItems.mace_head, "mace", ingotCost * 5 + nuggetCost * 4);

		public final static EnumType[] VALUES = values();

		private final ItemToolHead item;
		private final String tool;
		private final int cost;

		EnumType(ItemToolHead item, String tool, int cost) {
			this.item = item;
			this.tool = tool;
			this.cost = cost;
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

		public String getTool() {
			return tool;
		}

		public int getCost() {
			return cost;
		}

	}
}

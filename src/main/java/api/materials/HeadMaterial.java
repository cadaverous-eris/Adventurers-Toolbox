package api.materials;

import java.util.List;

import net.minecraft.item.ItemStack;

public class HeadMaterial extends PartMaterial {
	
	private final int harvestLevel;
	private final int durability;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private ItemStack repairItem;
	private String craftingItem;
	private String craftingItemSmall;
	private List<String> replaceableMaterials;
	
	public HeadMaterial(String name, int har, int dur, float eff, float att, int ench, ItemStack repair, String crafting, String crafting2, List<String> replaceableMaterials, String mod) {
		super(name, mod);
		this.harvestLevel = har;
		this.durability = dur;
		this.efficiency = eff;
		this.attackDamage = att;
		this.enchantability = ench;
		this.repairItem = repair;
		this.craftingItem = crafting;
		this.craftingItemSmall = crafting2;
		this.replaceableMaterials = replaceableMaterials;
	}
	
	public int getHarvestLevel() {
		return this.harvestLevel;
	}
	
	public int getDurability() {
		return this.durability;
	}
	
	public float getEfficiency() {
		return this.efficiency;
	}
	
	public float getAttackDamage() {
		return this.attackDamage;
	}
	
	public int getEnchantability() {
		return this.enchantability;
	}
	
	public ItemStack getRepairItem() {
		return this.repairItem.copy();
	}
	
	public void setRepairItem(ItemStack item) {
		this.repairItem = item.copy();
	}
	
	public String getCraftingItem() {
		return this.craftingItem;
	}
	
	public String getSmallCraftingItem() {
		return this.craftingItemSmall;
	}
	
	public boolean canReplaceMaterial(String materialName) {
		String toolMat = materialName.toLowerCase();
		if (toolMat.indexOf(":") >= 0 && toolMat.indexOf(":") + 1 < toolMat.length()) {
			toolMat = toolMat.substring(toolMat.indexOf(":") + 1);
		}
		for (String mat : this.replaceableMaterials) {
			
			//System.out.println(mat.toLowerCase() + ", " + toolMat);
			
			if (mat.toLowerCase().equals(toolMat)) {
				return true;
			}
		}
		
		if (materialName.equals("DIAMOND")) {
			return true;
		}
		
		return false;
	}

}

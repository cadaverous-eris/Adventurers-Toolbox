package api.materials;

public class AdornmentMaterial extends PartMaterial {
	
	private final int harvestLevelMod;
	private final float durabilityMod;
	private final float efficiencyMod;
	private final float attackDamageMod;
	private final float enchantabilityMod;
	
	public AdornmentMaterial(String name, int har, float dur, float eff, float att, float ench, String mod) {
		super(name, mod);
		this.harvestLevelMod = har;
		this.durabilityMod = dur;
		this.efficiencyMod = eff;
		this.attackDamageMod = att;
		this.enchantabilityMod = ench;
	}
	
	public int getHarvestLevelMod() {
		return this.harvestLevelMod;
	}
	
	public float getDurabilityMod() {
		return this.durabilityMod;
	}
	
	public float getEfficiencyMod() {
		return this.efficiencyMod;
	}
	
	public float getAttackDamageMod() {
		return this.attackDamageMod;
	}
	
	public float getEnchantabilityMod() {
		return this.enchantabilityMod;
	}

}

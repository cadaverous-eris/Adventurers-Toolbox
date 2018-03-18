package api.materials;

public class HandleMaterial extends PartMaterial {
	
	private final float durabilityMod;
	private final float enchantabilityMod;
	private final float efficiencyMod;
	
	public HandleMaterial(String name, float dur, float eff, float ench, String mod) {
		super(name, mod);
		this.durabilityMod = dur;
		this.efficiencyMod = eff;
		this.enchantabilityMod = ench;
	}
	
	public float getDurabilityMod() {
		return this.durabilityMod;
	}
	
	public float getEfficiencyMod() {
		return this.efficiencyMod;
	}
	
	public float getEnchantabilityMod() {
		return this.enchantabilityMod;
	}

}

package api.materials;

public class HaftMaterial extends PartMaterial {
	
	private final float durabilityMod;
	private final float enchantabilityMod;
	
	public HaftMaterial(String name, float dur, float ench, String mod) {
		super(name, mod);
		this.durabilityMod = dur;
		this.enchantabilityMod = ench;
	}
	
	public float getDurabilityMod() {
		return this.durabilityMod;
	}
	
	public float getEnchantabilityMod() {
		return this.enchantabilityMod;
	}

}

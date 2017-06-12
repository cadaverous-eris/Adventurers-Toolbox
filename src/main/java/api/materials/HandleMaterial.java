package api.materials;

public class HandleMaterial extends PartMaterial {
	
	private final float durabilityMod;
	
	public HandleMaterial(String name, float dur, String mod) {
		super(name, mod);
		this.durabilityMod = dur;
	}
	
	public float getDurabilityMod() {
		return this.durabilityMod;
	}

}

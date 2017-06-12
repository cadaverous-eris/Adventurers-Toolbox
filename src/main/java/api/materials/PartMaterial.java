package api.materials;

public class PartMaterial {
	
	//Name of the material
	protected String name;
	//Mod Id of the mod that adds the material. Used in the tool model classes to find textures.
	protected String modid;
	
	protected PartMaterial(String name, String path) {
		this.name = name;
		this.modid = path;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getModId() {
		return this.modid;
	}

}

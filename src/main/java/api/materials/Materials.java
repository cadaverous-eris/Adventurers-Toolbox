package api.materials;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Materials {
	
	private static Random rand = new Random();

	public static Map<String, HeadMaterial> head_registry = new HashMap<String, HeadMaterial>();
	public static Map<String, HaftMaterial> haft_registry = new HashMap<String, HaftMaterial>();
	public static Map<String, HandleMaterial> handle_registry = new HashMap<String, HandleMaterial>();
	public static Map<String, AdornmentMaterial> adornment_registry = new HashMap<String, AdornmentMaterial>();

	public static void registerHeadMat(HeadMaterial mat) {
		head_registry.put(mat.getName(), mat);
	}

	public static void registerHaftMat(HaftMaterial mat) {
		haft_registry.put(mat.getName(), mat);
	}

	public static void registerHandleMat(HandleMaterial mat) {
		handle_registry.put(mat.getName(), mat);
	}

	public static void registerAdornmentMat(AdornmentMaterial mat) {
		adornment_registry.put(mat.getName(), mat);
	}
	
	public static HeadMaterial randomHead() {
		HeadMaterial[] heads = head_registry.values().toArray(new HeadMaterial[head_registry.values().size()]);
		return heads[rand.nextInt(heads.length)];
	}
	
	public static HaftMaterial randomHaft() {
		HaftMaterial[] hafts = haft_registry.values().toArray(new HaftMaterial[haft_registry.values().size()]);
		return hafts[rand.nextInt(hafts.length)];
	}
	
	public static HandleMaterial randomHandle() {
		HandleMaterial[] handles = handle_registry.values().toArray(new HandleMaterial[handle_registry.values().size()]);
		return handles[rand.nextInt(handles.length)];
	}
	
	public static AdornmentMaterial randomAdornment() {
		AdornmentMaterial[] adornments = adornment_registry.values().toArray(new AdornmentMaterial[adornment_registry.values().size()]);
		return adornments[rand.nextInt(adornments.length)];
	}

}

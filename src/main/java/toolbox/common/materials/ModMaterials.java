package toolbox.common.materials;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import toolbox.Toolbox;
import toolbox.common.Config;

public class ModMaterials {

	public static final HeadMaterial HEAD_WOOD = new HeadMaterial("wood", 0, 59, 2.0F, 0.0F, 15,
			new ItemStack(Blocks.PLANKS), "plankWood", "stickWood", Toolbox.MODID);
	public static final HeadMaterial HEAD_STONE = new HeadMaterial("stone", 1, 131, 4.0F, 1.0F, 5,
			new ItemStack(Blocks.COBBLESTONE), "cobblestone", "pebble", Toolbox.MODID);
	public static final HeadMaterial HEAD_IRON = new HeadMaterial("iron", 2, 250, 6.0F, 2.0F, 14,
			new ItemStack(Items.IRON_INGOT), "ingotIron", "nuggetIron", Toolbox.MODID);
	public static final HeadMaterial HEAD_GOLD = new HeadMaterial("gold", 0, 32, 12.0F, 0.0F, 22,
			new ItemStack(Items.GOLD_INGOT), "ingotGold", "nuggetGold", Toolbox.MODID);

	public static final HeadMaterial HEAD_COPPER = new HeadMaterial("copper", 1, 181, 5.4F, 1.5F, 16, ItemStack.EMPTY,
			"ingotCopper", "nuggetCopper", Toolbox.MODID);
	public static final HeadMaterial HEAD_TIN = new HeadMaterial("tin", 1, 145, 4.9F, 1.0F, 12, ItemStack.EMPTY,
			"ingotTin", "nuggetTin", Toolbox.MODID);
	public static final HeadMaterial HEAD_BRONZE = new HeadMaterial("bronze", 2, 300, 6.5F, 2.0F, 14, ItemStack.EMPTY,
			"ingotBronze", "nuggetBronze", Toolbox.MODID);
	public static final HeadMaterial HEAD_ALUMINUM = new HeadMaterial("aluminum", 1, 161, 5.2F, 1.5F, 14,
			ItemStack.EMPTY, "ingotAluminum", "nuggetAluminum", Toolbox.MODID);
	public static final HeadMaterial HEAD_NICKEL = new HeadMaterial("nickel", 1, 181, 6.4F, 1.5F, 8, ItemStack.EMPTY,
			"ingotNickel", "nuggetNickel", Toolbox.MODID);
	public static final HeadMaterial HEAD_LEAD = new HeadMaterial("lead", 2, 168, 6.0F, 2.0F, 4, ItemStack.EMPTY,
			"ingotLead", "nuggetLead", Toolbox.MODID);
	public static final HeadMaterial HEAD_SILVER = new HeadMaterial("silver", 0, 64, 8.0F, 0.5F, 18, ItemStack.EMPTY,
			"ingotSilver", "nuggetSilver", Toolbox.MODID);
	public static final HeadMaterial HEAD_STEEL = new HeadMaterial("steel", 2, 500, 7.0F, 2.5F, 14, ItemStack.EMPTY,
			"ingotSteel", "nuggetSteel", Toolbox.MODID);
	public static final HeadMaterial HEAD_ELECTRUM = new HeadMaterial("electrum", 1, 96, 8.0F, 1.0F, 26,
			ItemStack.EMPTY, "ingotElectrum", "nuggetElectrum", Toolbox.MODID);
	public static final HeadMaterial HEAD_SOULFORGED_STEEL = new HeadMaterial("soulforged_steel", 4, 600, 9.0F, 3F, 22, ItemStack.EMPTY,
			"ingotSoulforgedSteel", "nuggetSoulforgedSteel", Toolbox.MODID);

	public static final HaftMaterial HAFT_WOOD = new HaftMaterial("wood", 1.0F, 1.0F, Toolbox.MODID);
	public static final HaftMaterial HAFT_BONE = new HaftMaterial("bone", 0.8F, 1.5F, Toolbox.MODID);
	public static final HaftMaterial HAFT_BLAZE_ROD = new HaftMaterial("blaze_rod", 1.125F, 1.2F, Toolbox.MODID);
	public static final HaftMaterial HAFT_END_ROD = new HaftMaterial("end_rod", 1.25F, 1.5F, Toolbox.MODID);
	public static final HaftMaterial HAFT_IMPROVED = new HaftMaterial("refined", 1.3F, 0.8F, Toolbox.MODID);

	public static final HandleMaterial HANDLE_WOOD = new HandleMaterial("wood", 1.0F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_BONE = new HandleMaterial("bone", 0.9375F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_CLOTH = new HandleMaterial("cloth", 1.125F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_LEATHER = new HandleMaterial("leather", 1.25F, Toolbox.MODID);

	public static final AdornmentMaterial ADORNMENT_NULL = new AdornmentMaterial("null", 0, 1.0F, 1.0F, 0.0F, 1.0F,
			Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_DIAMOND = new AdornmentMaterial("diamond", 1, 6.25F, 1.4F, 1F, 0.7F,
			Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_EMERALD = new AdornmentMaterial("emerald", 1, 5.75F, 1.3F, 1F, 0.9F,
			Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_QUARTZ = new AdornmentMaterial("quartz", 0, 2F, 2F, 2F, 0.8F,
			Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_PRISMARINE = new AdornmentMaterial("prismarine", 0, 2.5F, 1F, 1F,
			1.1F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_ENDER_PEARL = new AdornmentMaterial("ender_pearl", 0, 3F, 1F, 0F,
			2F, Toolbox.MODID);

	public static void init() {
		initHeadMaterials();
		initHaftMaterials();
		initHandleMaterials();
		initAdornmentMaterials();
	}

	private static void initHeadMaterials() {
		List<HeadMaterial> headMaterials = new ArrayList<HeadMaterial>();

		headMaterials.add(HEAD_WOOD);
		headMaterials.add(HEAD_STONE);
		headMaterials.add(HEAD_IRON);
		headMaterials.add(HEAD_GOLD);

		headMaterials.add(HEAD_COPPER);
		headMaterials.add(HEAD_TIN);
		headMaterials.add(HEAD_BRONZE);
		headMaterials.add(HEAD_ALUMINUM);
		headMaterials.add(HEAD_NICKEL);
		headMaterials.add(HEAD_LEAD);
		headMaterials.add(HEAD_SILVER);
		headMaterials.add(HEAD_STEEL);
		headMaterials.add(HEAD_ELECTRUM);
		headMaterials.add(HEAD_SOULFORGED_STEEL);

		for (HeadMaterial mat : headMaterials) {
			if (!Config.DISABLED_MATERIALS.contains(mat.getName())) {
				Materials.registerHeadMat(mat);
			}
		}
	}

	private static void initHaftMaterials() {
		Materials.registerHaftMat(HAFT_WOOD);
		Materials.registerHaftMat(HAFT_BONE);
		Materials.registerHaftMat(HAFT_BLAZE_ROD);
		Materials.registerHaftMat(HAFT_END_ROD);
		if(Loader.isModLoaded("betterwithmods")) Materials.registerHaftMat(HAFT_IMPROVED);
	}

	private static void initHandleMaterials() {
		Materials.registerHandleMat(HANDLE_WOOD);
		Materials.registerHandleMat(HANDLE_BONE);
		Materials.registerHandleMat(HANDLE_CLOTH);
		Materials.registerHandleMat(HANDLE_LEATHER);
	}

	private static void initAdornmentMaterials() {
		Materials.registerAdornmentMat(ADORNMENT_NULL);
		Materials.registerAdornmentMat(ADORNMENT_DIAMOND);
		Materials.registerAdornmentMat(ADORNMENT_EMERALD);
		Materials.registerAdornmentMat(ADORNMENT_QUARTZ);
		Materials.registerAdornmentMat(ADORNMENT_PRISMARINE);
		Materials.registerAdornmentMat(ADORNMENT_ENDER_PEARL);
	}

	public static void initHeadRepairItems() {
		for (HeadMaterial mat : Materials.head_registry.values()) {
			if (mat.getRepairItem().isEmpty()) {
				if (OreDictionary.doesOreNameExist(mat.getCraftingItem())) {
					NonNullList<ItemStack> ores = OreDictionary.getOres(mat.getCraftingItem());
					if (ores.size() > 0) {
						ItemStack repairItem = OreDictionary.getOres(mat.getCraftingItem()).get(0);
						mat.setRepairItem(repairItem);
					}
				}
			}
		}
	}

}

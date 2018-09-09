package toolbox.common.materials;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import toolbox.Toolbox;
import toolbox.common.Config;

public class ModMaterials {
	
	public static ToolMaterial TOOL_MAT_TOOLBOX;

	public static final HeadMaterial HEAD_WOOD = new HeadMaterial("wood", 0, 59, 2.0F, 0.0F, 15,
			new ItemStack(Blocks.PLANKS), "plankWood", "stickWood", Lists.<String>newArrayList("wood"), Toolbox.MODID);
	public static final HeadMaterial HEAD_FLINT = new HeadMaterial("flint", 0, 59, 2.0F, 0.0F, 15,
			new ItemStack(Items.FLINT), "flint", "gravel", Lists.<String>newArrayList("wood", "flint"), Toolbox.MODID);
	public static final HeadMaterial HEAD_STONE = new HeadMaterial("stone", 1, 131, 4.0F, 1.0F, 5,
			new ItemStack(Blocks.COBBLESTONE), "cobblestone", "pebble", Lists.<String>newArrayList("stone"), Toolbox.MODID);
	public static final HeadMaterial HEAD_IRON = new HeadMaterial("iron", 2, 250, 6.0F, 2.0F, 14,
			new ItemStack(Items.IRON_INGOT), "ingotIron", "nuggetIron", Lists.<String>newArrayList("iron"), Toolbox.MODID);
	public static final HeadMaterial HEAD_GOLD = new HeadMaterial("gold", 0, 32, 12.0F, 0.0F, 22,
			new ItemStack(Items.GOLD_INGOT), "ingotGold", "nuggetGold", Lists.<String>newArrayList("gold"), Toolbox.MODID);

	public static final HeadMaterial HEAD_COPPER = new HeadMaterial("copper", 1, 181, 5.4F, 1.5F, 16, ItemStack.EMPTY,
			"ingotCopper", "nuggetCopper", Lists.<String>newArrayList("copper"), Toolbox.MODID);
	public static final HeadMaterial HEAD_TIN = new HeadMaterial("tin", 1, 145, 4.9F, 1.0F, 12, ItemStack.EMPTY,
			"ingotTin", "nuggetTin", Lists.<String>newArrayList("tin"), Toolbox.MODID);
	public static final HeadMaterial HEAD_BRONZE = new HeadMaterial("bronze", 2, 300, 6.5F, 2.0F, 14, ItemStack.EMPTY,
			"ingotBronze", "nuggetBronze", Lists.<String>newArrayList("bronze"), Toolbox.MODID);
	public static final HeadMaterial HEAD_ALUMINUM = new HeadMaterial("aluminum", 1, 161, 5.2F, 1.5F, 14,
			ItemStack.EMPTY, "ingotAluminum", "nuggetAluminum", Lists.<String>newArrayList("aluminum", "aluminium"), Toolbox.MODID);
	public static final HeadMaterial HEAD_NICKEL = new HeadMaterial("nickel", 1, 181, 6.4F, 1.5F, 8, ItemStack.EMPTY,
			"ingotNickel", "nuggetNickel", Lists.<String>newArrayList("nickel"), Toolbox.MODID);
	public static final HeadMaterial HEAD_LEAD = new HeadMaterial("lead", 2, 168, 6.0F, 2.0F, 4, ItemStack.EMPTY,
			"ingotLead", "nuggetLead", Lists.<String>newArrayList("lead"), Toolbox.MODID);
	public static final HeadMaterial HEAD_SILVER = new HeadMaterial("silver", 0, 64, 8.0F, 0.5F, 20, ItemStack.EMPTY,
			"ingotSilver", "nuggetSilver", Lists.<String>newArrayList("silver"), Toolbox.MODID);
	public static final HeadMaterial HEAD_STEEL = new HeadMaterial("steel", 2, 500, 7.0F, 2.5F, 14, ItemStack.EMPTY,
			"ingotSteel", "nuggetSteel", Lists.<String>newArrayList("steel"), Toolbox.MODID);
	public static final HeadMaterial HEAD_ELECTRUM = new HeadMaterial("electrum", 1, 96, 8.0F, 1.0F, 26,
			ItemStack.EMPTY, "ingotElectrum", "nuggetElectrum", Lists.<String>newArrayList("electrum"), Toolbox.MODID);
	public static final HeadMaterial HEAD_SOULFORGED_STEEL = new HeadMaterial("soulforged_steel", 4, 600, 9.0F, 3F, 22, ItemStack.EMPTY,
			"ingotSoulforgedSteel", "nuggetSoulforgedSteel", Lists.<String>newArrayList("soulforged_steel"), Toolbox.MODID);
	public static final HeadMaterial HEAD_DAWNSTONE = new HeadMaterial("dawnstone", 2, 644, 7.5F, 2.5F, 18, ItemStack.EMPTY,
			"ingotDawnstone", "nuggetDawnstone", Lists.<String>newArrayList("dawnstone"), Toolbox.MODID);
	public static final HeadMaterial HEAD_CONSTANTAN = new HeadMaterial("constantan", 2, 215, 6F, 2.0F, 14, ItemStack.EMPTY,
			"ingotConstantan", "nuggetConstantan", Lists.<String>newArrayList("constantan"), Toolbox.MODID);
	
	public static final HeadMaterial HEAD_COBALT = new HeadMaterial("cobalt", 3, 825, 12.0F, 2.1F, 16, ItemStack.EMPTY,
			"ingotCobalt", "nuggetCobalt", Lists.<String>newArrayList("cobalt"), Toolbox.MODID);
	public static final HeadMaterial HEAD_ARDITE = new HeadMaterial("ardite", 3, 1035, 3.5F, 1.5F, 12, ItemStack.EMPTY,
			"ingotArdite", "nuggetArdite", Lists.<String>newArrayList("ardite"), Toolbox.MODID);
	public static final HeadMaterial HEAD_MANYULLYN = new HeadMaterial("manyullyn", 3, 865, 7.0F, 3.0F, 18, ItemStack.EMPTY,
			"ingotManyullyn", "nuggetManyullyn", Lists.<String>newArrayList("manyullyn"), Toolbox.MODID);
	
	public static final HeadMaterial HEAD_THAUMIUM = new HeadMaterial("thaumium", 3, 500, 7.0f, 2.5f, 22, ItemStack.EMPTY,
			"ingotThaumium", "nuggetThaumium", Lists.<String>newArrayList("thaumium"), Toolbox.MODID);
	public static final HeadMaterial HEAD_VOID = new HeadMaterial("void", 4, 150, 8.0f, 3.0f, 10, ItemStack.EMPTY,
			"ingotVoid", "nuggetVoid", Lists.<String>newArrayList("void"), Toolbox.MODID);
	
	public static final HaftMaterial HAFT_WOOD = new HaftMaterial("wood", 0, 1.0F, 1.0F, 0.0F, 1.0F, Toolbox.MODID);
	public static final HaftMaterial HAFT_BONE = new HaftMaterial("bone", 0, 0.8F, 1.0F, 0.5F, 1.3F, Toolbox.MODID);
	public static final HaftMaterial HAFT_BLAZE_ROD = new HaftMaterial("blaze_rod", 0, 1.125F, 1.0F, 0.5F, 1.2F, Toolbox.MODID);
	public static final HaftMaterial HAFT_END_ROD = new HaftMaterial("end_rod", 1, 1.5F, 1.25F, 0.0F, 1.2F, Toolbox.MODID);
	public static final HaftMaterial HAFT_IMPROVED = new HaftMaterial("refined", 0, 1.3F, 1.0F, 0.0F, 0.8F, Toolbox.MODID);
	public static final HaftMaterial HAFT_WITHER_BONE = new HaftMaterial("wither_bone", 0, 2.0F, 1.0F, 0.0F, 0.8F, Toolbox.MODID);
	public static final HaftMaterial HAFT_TREATED_WOOD = new HaftMaterial("treated_wood", 0, 1.125F, 1.1F, 0.0F, 0.875F, Toolbox.MODID);
	public static final HaftMaterial HAFT_WITHERED_BONE = new HaftMaterial("withered_bone", 0, 1.125F, 1.05F, 0.5F, 1.25F, Toolbox.MODID);
	
	public static final HandleMaterial HANDLE_WOOD = new HandleMaterial("wood", 1.0F, 1.0F, 1.0F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_BONE = new HandleMaterial("bone", 0.9375F, 1.1F, 1.3F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_CLOTH = new HandleMaterial("cloth", 1.125F, 1.0F, 0.8F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_LEATHER = new HandleMaterial("leather", 1.25F, 1.0f, 0.9F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_TREATED_WOOD = new HandleMaterial("treated_wood", 1.125F, 1.1F, 0.875F, Toolbox.MODID);
	public static final HandleMaterial HANDLE_WITHERED_BONE = new HandleMaterial("withered_bone", 1.25F, 1.15F, 1.25F, Toolbox.MODID);
	
	public static final AdornmentMaterial ADORNMENT_NULL = new AdornmentMaterial("null", 0, 1.0F, 1.0F, 0.0F, 1.0F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_DIAMOND = new AdornmentMaterial("diamond", 1, 6.25F, 1.4F, 1F, 0.7F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_EMERALD = new AdornmentMaterial("emerald", 1, 5.75F, 1.3F, 1F, 0.8F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_QUARTZ = new AdornmentMaterial("quartz", 0, 2F, 2F, 2F, 0.8F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_PRISMARINE = new AdornmentMaterial("prismarine", 0, 2.5F, 1F, 1F, 1.1F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_ENDER_PEARL = new AdornmentMaterial("ender_pearl", 0, 3F, 1F, 0F, 1.75F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_LAPIS = new AdornmentMaterial("lapis", 0, 1F, 1F, 0F, 2.25F, Toolbox.MODID);
	
	public static final AdornmentMaterial ADORNMENT_BIOTITE = new AdornmentMaterial("biotite", 1, 3F, 1.75F, 1.5F, 1.125F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_AMETHYST = new AdornmentMaterial("amethyst", 1, 6.5F, 1.6F, 1.5F, 0.9F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_RUBY = new AdornmentMaterial("ruby", 1, 5.5F, 1.4F, 1F, 0.9F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_PERIDOT = new AdornmentMaterial("peridot", 0, 4.5F, 1.5F, 0.5F, 0.75F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_TOPAZ = new AdornmentMaterial("topaz", 0, 4.5F, 1.3F, 1.0F, 0.8F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_TANZANITE = new AdornmentMaterial("tanzanite", 0, 4.25F, 1.2F, 0.5F, 1.1F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_MALACHITE = new AdornmentMaterial("malachite", 0, 4.25F, 1.3F, 0.5F, 0.85F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_SAPPHIRE = new AdornmentMaterial("sapphire", 1, 5.25F, 1.2F, 1F, 1.25F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_AMBER = new AdornmentMaterial("amber", 0, 1.75F, 1.2F, 0.5F, 0.75F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_OBSIDIAN = new AdornmentMaterial("obsidian", 1, 7F, 1.5F, 1F, 1.1F, Toolbox.MODID);
	public static final AdornmentMaterial ADORNMENT_AQUAMARINE = new AdornmentMaterial("aquamarine", 0, 1.5F, 1.1F, 0.5F, 0.75F, Toolbox.MODID);
	
	public static List<HeadMaterial> headMaterials = new ArrayList<HeadMaterial>();
	
	public static void init() {
		TOOL_MAT_TOOLBOX = EnumHelper.addToolMaterial(Toolbox.MODID + ":toolbox", 1, 1, 1, 1, 1);
		
		initHeadMaterials();
		initHaftMaterials();
		initHandleMaterials();
		initAdornmentMaterials();
	}

	private static void initHeadMaterials() {
		OreDictionary.registerOre("flint", Items.FLINT);
		OreDictionary.registerOre("gravel", Blocks.GRAVEL);
		
		headMaterials.add(HEAD_WOOD);
		headMaterials.add(HEAD_FLINT);
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
		headMaterials.add(HEAD_DAWNSTONE);
		headMaterials.add(HEAD_CONSTANTAN);
		headMaterials.add(HEAD_COBALT);
		headMaterials.add(HEAD_ARDITE);
		headMaterials.add(HEAD_MANYULLYN);
		headMaterials.add(HEAD_THAUMIUM);
		headMaterials.add(HEAD_VOID);
		
		for (HeadMaterial mat : headMaterials) {
			if (!Config.DISABLED_MATERIALS.contains(mat.getName())) {
				System.out.println("Registering " + mat.getName());
				Materials.registerHeadMat(mat);
			}
		}
	}

	private static void initHaftMaterials() {
		Materials.registerHaftMat(HAFT_WOOD);
		Materials.registerHaftMat(HAFT_BONE);
		Materials.registerHaftMat(HAFT_BLAZE_ROD);
		Materials.registerHaftMat(HAFT_END_ROD);
		if (Loader.isModLoaded("betterwithmods")) Materials.registerHaftMat(HAFT_IMPROVED);
		if (Loader.isModLoaded("nex")) Materials.registerHaftMat(HAFT_WITHER_BONE);
		Materials.registerHaftMat(HAFT_TREATED_WOOD);
		Materials.registerHaftMat(HAFT_WITHERED_BONE);
	}

	private static void initHandleMaterials() {
		Materials.registerHandleMat(HANDLE_WOOD);
		Materials.registerHandleMat(HANDLE_BONE);
		Materials.registerHandleMat(HANDLE_CLOTH);
		Materials.registerHandleMat(HANDLE_LEATHER);
		Materials.registerHandleMat(HANDLE_TREATED_WOOD);
		Materials.registerHandleMat(HANDLE_WITHERED_BONE);
	}

	private static void initAdornmentMaterials() {
		Materials.registerAdornmentMat(ADORNMENT_NULL);
		Materials.registerAdornmentMat(ADORNMENT_DIAMOND);
		Materials.registerAdornmentMat(ADORNMENT_EMERALD);
		Materials.registerAdornmentMat(ADORNMENT_QUARTZ);
		Materials.registerAdornmentMat(ADORNMENT_PRISMARINE);
		Materials.registerAdornmentMat(ADORNMENT_ENDER_PEARL);
		Materials.registerAdornmentMat(ADORNMENT_LAPIS);
		Materials.registerAdornmentMat(ADORNMENT_BIOTITE);
		Materials.registerAdornmentMat(ADORNMENT_RUBY);
		Materials.registerAdornmentMat(ADORNMENT_AMETHYST);
		Materials.registerAdornmentMat(ADORNMENT_PERIDOT);
		Materials.registerAdornmentMat(ADORNMENT_TOPAZ);
		Materials.registerAdornmentMat(ADORNMENT_TANZANITE);
		Materials.registerAdornmentMat(ADORNMENT_MALACHITE);
		Materials.registerAdornmentMat(ADORNMENT_SAPPHIRE);
		Materials.registerAdornmentMat(ADORNMENT_AMBER);
		Materials.registerAdornmentMat(ADORNMENT_OBSIDIAN);
		Materials.registerAdornmentMat(ADORNMENT_AQUAMARINE);
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

package toolbox.compat.tconstruct;

import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.mantle.util.RecipeMatch;
import toolbox.compat.tconstruct.ItemCast.EnumType;

public class TConstructCompat {

	public static void preInit() {
		//Add soulforged steel as a liquid
		if (Loader.isModLoaded("betterwithmods")) {
			Fluid soulforgedSteel = new Fluid("soulforged_steel", new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
			FluidRegistry.registerFluid(soulforgedSteel);
			FluidRegistry.addBucketForFluid(soulforgedSteel);
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("fluid", soulforgedSteel.getName());
			tag.setString("ore", "SoulforgedSteel");
			tag.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
		}
	}

	public static void init() {
		slimeknights.tconstruct.library.smeltery.CastingRecipe recipe;
		for (EnumType type : ItemCast.EnumType.VALUES) {
			int i = 0;
			for (HeadMaterial mat : Materials.head_registry.values()) {
				ItemStack head = new ItemStack(type.getItem(), 1, i);
				Fluid fluid = FluidRegistry.getFluid(mat.getName());
				if (fluid != null) {
					recipe = new slimeknights.tconstruct.library.smeltery.CastingRecipe(head, RecipeMatch.of(ItemCast.getStack(type, 1)), fluid, type.getCost());
					slimeknights.tconstruct.library.TinkerRegistry.registerTableCasting(recipe);
				}
				i++;
			}
		}
	}
}

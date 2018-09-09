package toolbox.common.items.tools;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import api.materials.Materials;
import betterwithmods.module.hardcore.creatures.HCEnchanting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.items.IWarpingGear;
import toolbox.Toolbox;
import toolbox.common.Config;
import toolbox.common.materials.ModMaterials;

@Optional.Interface(iface = "thaumcraft.api.items.IWarpingGear", modid = "thaumcraft")
public class ItemATPickaxe extends ItemPickaxe implements IWarpingGear, IHeadTool, IHaftTool, IHandleTool, IAdornedTool {

	private String name = "pickaxe";
	public static final String DAMAGE_TAG = "Damage";

	public ItemATPickaxe() {
		super(ModMaterials.TOOL_MAT_TOOLBOX);

		setRegistryName(name);
		setUnlocalizedName(Toolbox.MODID + "." + name);
		this.maxStackSize = 1;
		setCreativeTab(Toolbox.toolsTab);
		this.setMaxDamage(0);
	}

	public int getHarvestLevel(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getHarvestLevel()
				+ IAdornedTool.getAdornmentMat(stack).getHarvestLevelMod()
				+ IHaftTool.getHaftMat(stack).getHarvestLevelMod();
	}

	public int getDurability(ItemStack stack) {
		return (int) (IHeadTool.getHeadMat(stack).getDurability()
				* IHaftTool.getHaftMat(stack).getDurabilityMod()
				* IHandleTool.getHandleMat(stack).getDurabilityMod()
				* IAdornedTool.getAdornmentMat(stack).getDurabilityMod());
	}

	public float getEfficiency(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getEfficiency()
				* IAdornedTool.getAdornmentMat(stack).getEfficiencyMod()
				* IHaftTool.getHaftMat(stack).getEfficiencyMod()
				* IHandleTool.getHandleMat(stack).getEfficiencyMod();
	}

	public float getAttackDamage(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getAttackDamage()
				+ IAdornedTool.getAdornmentMat(stack).getAttackDamageMod()
				+ IHaftTool.getHaftMat(stack).getAttackDamageMod();
	}

	public int getEnchantability(ItemStack stack) {
		return (int) (IHeadTool.getHeadMat(stack).getEnchantability()
				* IHaftTool.getHaftMat(stack).getEnchantabilityMod()
				* IAdornedTool.getAdornmentMat(stack).getEnchantabilityMod()
				* IHandleTool.getHandleMat(stack).getEnchantabilityMod());
	}

	public ItemStack getRepairItem(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getRepairItem();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		for (String type : getToolClasses(stack)) {
			if (state.getBlock().isToolEffective(type, state) || state.getMaterial() == Material.ROCK
					|| state.getMaterial() == Material.IRON) {
				return getEfficiency(stack);
			}
		}
		return 1.0F;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return getEnchantability(stack);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		ItemStack mat = getRepairItem(toRepair);
		if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) {
			return true;
		}
		if (toRepair.getItem() == this) {
			if (OreDictionary.containsMatch(false, OreDictionary.getOres(IHeadTool.getHeadMat(toRepair).getCraftingItem()), repair)) return true;
		}
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass,
			@javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player,
			@javax.annotation.Nullable IBlockState blockState) {
		if (toolClass != null && getToolClasses(stack).contains(toolClass)) {
			return this.getHarvestLevel(stack);
		} else {
			return super.getHarvestLevel(stack, toolClass, player, blockState);
		}
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot,
			ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
					"Tool modifier", (double) 1.0F + this.getAttackDamage(stack), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) -2.8F, 0));
		}

		return multimap;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double) getDamage(stack) / (double) getDurability(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return getDurability(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (!Config.DISABLED_TOOLS.contains("pickaxe")) {
			ItemStack stack1 = new ItemStack(this);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(HEAD_TAG, Materials.randomHead().getName());
			tag.setString(HAFT_TAG, Materials.randomHaft().getName());
			tag.setString(HANDLE_TAG, Materials.randomHandle().getName());
			tag.setString(ADORNMENT_TAG, Materials.randomAdornment().getName());
			stack1.setTagCompound(tag);
			if (isInCreativeTab(tab)) {
				subItems.add(stack1);
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal(IHeadTool.getHeadMat(stack).getName() + ".name") + " "
				+ super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		if (IHeadTool.getHeadMat(stack) == ModMaterials.HEAD_SOULFORGED_STEEL) {
			if (Loader.isModLoaded("betterwithmods") && !HCEnchanting.canEnchantSteel(enchantment)) {
				return false;
			}
		}

		return enchantment.type.canEnchantItem(stack.getItem()) || enchantment.type == EnumEnchantmentType.DIGGER;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}

	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().toString()));
	}
	
	@Override
	public int getWarp(ItemStack stack, EntityPlayer player) {
		return IHeadTool.getHeadMat(stack) == ModMaterials.HEAD_VOID ? 1 : 0;
	}

}

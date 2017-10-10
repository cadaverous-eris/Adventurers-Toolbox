package toolbox.common.items.tools;

import java.util.List;

import com.google.common.collect.Multimap;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSword extends ItemWeaponBase implements IBladeTool, ICrossguardTool, IHandleTool, IAdornedTool {

	public ItemSword() {
		super("sword");
		this.setMaxDamage(0);
	}

	public int getDurability(ItemStack stack) {
		return (int) ((((IBladeTool.getBladeMat(stack).getDurability() * 2F) + ICrossguardTool.getCrossguardMat(stack).getDurability()) / 3F)
				* IHandleTool.getHandleMat(stack).getDurabilityMod() * IAdornedTool.getAdornmentMat(stack).getDurabilityMod());
	}

	public float getEfficiencyMod(ItemStack stack) {
		return (IAdornedTool.getAdornmentMat(stack).getEfficiencyMod() - 1F) / 2F;
	}

	public float getAttackDamage(ItemStack stack) {
		return IBladeTool.getBladeMat(stack).getAttackDamage() + IAdornedTool.getAdornmentMat(stack).getAttackDamageMod();
	}

	public int getEnchantability(ItemStack stack) {
		return (int) ((((IBladeTool.getBladeMat(stack).getEnchantability() * 2F) + ICrossguardTool.getCrossguardMat(stack).getEnchantability())
				/ 3F) * IAdornedTool.getAdornmentMat(stack).getEnchantabilityMod());
	}

	public ItemStack getRepairItem(ItemStack stack) {
		return IBladeTool.getBladeMat(stack).getRepairItem();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		Block block = state.getBlock();

		if (block == Blocks.WEB) {
			return 15.0F;
		} else {
			Material material = state.getMaterial();
			return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL
					&& material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return blockIn.getBlock() == Blocks.WEB;
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
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot,
			ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
					"Weapon modifier", (double) 3.0F + this.getAttackDamage(stack), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER,
					"Weapon modifier", (double) (-2.4000000953674316D + getEfficiencyMod(stack)), 0));
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
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			if (!flagIn.isAdvanced() || !stack.hasTagCompound() || !stack.getTagCompound().hasKey(DAMAGE_TAG)) {
				tooltip.add(I18n.translateToLocal("desc.durability.name") + ": "
						+ (getDurability(stack) - getDamage(stack)) + " / " + getDurability(stack));
			}
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		ItemStack stack1 = new ItemStack(this);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(BLADE_TAG, Materials.randomHead().getName());
		tag.setString(CROSSGUARD_TAG, Materials.randomHead().getName());
		tag.setString(HANDLE_TAG, Materials.randomHandle().getName());
		tag.setString(ADORNMENT_TAG, Materials.randomAdornment().getName());
		stack1.setTagCompound(tag);
		subItems.add(stack1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal(IBladeTool.getBladeMat(stack).getName() + ".name") + " "
				+ super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		return enchantment.type.canEnchantItem(stack.getItem()) || enchantment.type == EnumEnchantmentType.WEAPON;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		super.hitEntity(stack, target, attacker);

		if (attacker instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) attacker;

			float f2 = player.getCooledAttackStrength(0.5F);
			boolean flag = f2 > 0.9F;
			boolean flag1 = player.isSprinting() && flag;
			boolean flag2 = flag && player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder()
					&& !player.isInWater() && !player.isPotionActive(MobEffects.BLINDNESS) && !player.isRiding()
					&& target instanceof EntityLivingBase;
			flag2 = flag2 && !player.isSprinting();
			double d0 = (double) (player.distanceWalkedModified - player.prevDistanceWalkedModified);

			if (!flag2 && !flag1 && player.onGround && d0 < (double) player.getAIMoveSpeed()) {

				float f = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
				float f1;

				if (target instanceof EntityLivingBase) {
					f1 = EnchantmentHelper.getModifierForCreature(stack,
							((EntityLivingBase) target).getCreatureAttribute());
				} else {
					f1 = EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
				}

				f = f * (0.2F + f2 * f2 * 0.8F);
				f1 = f1 * f2;

				if (f > 0.0F || f1 > 0.0F) {
					if (flag2) {
						f *= 1.5F;
					}

					f = f + f1;

					float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * f;

					for (EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class,
							target.getEntityBoundingBox().expand(1.0D, 0.25D, 1.0D))) {
						if (entitylivingbase != player && entitylivingbase != target
								&& !player.isOnSameTeam(entitylivingbase)
								&& player.getDistanceSq(entitylivingbase) < 9.0D) {
							entitylivingbase.knockBack(player, 0.4F,
									(double) MathHelper.sin(player.rotationYaw * 0.017453292F),
									(double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
							entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), f3);
						}
					}

					player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ,
							SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
					player.spawnSweepParticles();

				}

			}

		}

		return true;
	}

}

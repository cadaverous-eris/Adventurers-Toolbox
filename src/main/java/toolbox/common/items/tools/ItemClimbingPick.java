package toolbox.common.items.tools;

import java.util.List;

import com.google.common.collect.Multimap;

import api.materials.Materials;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemClimbingPick extends ItemToolBase implements IHeadTool, IHaftTool, IHandleTool {

	public ItemClimbingPick() {
		super("climbing_pick");
		this.toolClass = "pickaxe";
		this.setMaxDamage(0);
	}

	public int getHarvestLevel(ItemStack stack) {
		return 0;
	}

	public int getDurability(ItemStack stack) {
		return (int) (IHeadTool.getHeadMat(stack).getDurability() * IHaftTool.getHaftMat(stack).getDurabilityMod()
				* IHandleTool.getHandleMat(stack).getDurabilityMod());
	}

	public float getEfficiency(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getEfficiency() * 0.5F;
	}

	public float getAttackDamage(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getAttackDamage();
	}

	public int getEnchantability(ItemStack stack) {
		return (int) (IHeadTool.getHeadMat(stack).getEnchantability()
				* IHaftTool.getHaftMat(stack).getEnchantabilityMod());
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
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass,
			@javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player,
			@javax.annotation.Nullable IBlockState blockState) {
		int level = super.getHarvestLevel(stack, toolClass, player, blockState);
		if (level == -1 && toolClass.equals(this.toolClass)) {
			return getHarvestLevel(stack);
		} else {
			return level;
		}
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot,
			ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
					"Tool modifier", (double) 3.0F + this.getAttackDamage(stack), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) -3F, 0));
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
		tag.setString(HEAD_TAG, Materials.randomHead().getName());
		tag.setString(HAFT_TAG, Materials.randomHaft().getName());
		tag.setString(HANDLE_TAG, Materials.randomHandle().getName());
		stack1.setTagCompound(tag);
		subItems.add(stack1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal(IHeadTool.getHeadMat(stack).getName() + ".name") + " "
				+ super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		return enchantment.type.canEnchantItem(stack.getItem());
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult rt = this.rayTrace(worldIn, playerIn, false);
		if (rt == null || rt.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		}
		BlockPos pos = rt.getBlockPos();

		if (playerIn.getDistance(pos.getX() + 0.5, pos.getY() - 1.0, pos.getZ() + 0.5) < 1.5) {
			playerIn.setActiveHand(handIn);
			return new ActionResult(EnumActionResult.SUCCESS, itemstack);
		}

		return new ActionResult(EnumActionResult.FAIL, itemstack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entitylivingbase, int count) {
		World world = entitylivingbase.getEntityWorld();

		if (count % 2 != 0) {
			return;
		}

		if (entitylivingbase instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entitylivingbase;
			RayTraceResult rt = this.rayTrace(world, player, false);
			if (rt != null && rt.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos pos = rt.getBlockPos();
				if (player.getDistance(pos.getX() + 0.5, pos.getY() - 1.0, pos.getZ() + 0.5) < 1.5) {
					if (player.motionY < -0.08) {
						player.motionY *= 0.1;
						player.fallDistance = 0;
						player.velocityChanged = true;
						if (this.itemRand.nextFloat() < 0.1F) {
							this.damageItem(stack, 1, player);
						}
					}
				} else {
					player.stopActiveHand();
				}
			}
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		if (!slotChanged && oldStack.getItem() == newStack.getItem()) {
			return false;
		}
		System.out.println("awdawd");
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

}

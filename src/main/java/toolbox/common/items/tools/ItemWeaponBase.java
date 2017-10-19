package toolbox.common.items.tools;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.Toolbox;
import toolbox.common.items.ItemBase;

public class ItemWeaponBase extends ItemBase {

	public static final String DAMAGE_TAG = "Damage";

	public ItemWeaponBase(String name) {
		super(name);
		this.maxStackSize = 1;
		setCreativeTab(Toolbox.weaponsTab);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		damageItem(stack, 1, attacker);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && (double) state.getBlockHardness(worldIn, pos) != 0.0D) {
			damageItem(stack, 2, entityLiving);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public int getDamage(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(DAMAGE_TAG)) {
			return stack.getTagCompound().getInteger(DAMAGE_TAG);
		}
		return 0;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDamage(stack) > 0;
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return getDamage(stack) > 0;
	}

	public void setDamage(ItemStack stack, int damage) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		if (damage < 0) {
			damage = 0;
		}

		stack.getTagCompound().setInteger(DAMAGE_TAG, damage);
	}

	public void damageItem(ItemStack stack, int amount, EntityLivingBase entityIn) {
		if (!(entityIn instanceof EntityPlayer) || !((EntityPlayer) entityIn).capabilities.isCreativeMode) {
			if (this.isDamageable()) {
				if (this.attemptDamageItem(stack, amount, entityIn.getRNG())) {
					entityIn.renderBrokenItemStack(stack);
					stack.shrink(1);

					if (entityIn instanceof EntityPlayer) {
						EntityPlayer entityplayer = (EntityPlayer) entityIn;
						entityplayer.addStat(StatList.getObjectBreakStats(this));
					}

					this.setDamage(stack, 0);
				}
			}
		}
	}

	public boolean attemptDamageItem(ItemStack stack, int amount, Random rand) {
		if (!this.isDamageable()) {
			return false;
		} else {
			if (amount > 0) {
				int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
				int j = 0;

				for (int k = 0; i > 0 && k < amount; ++k) {
					if (EnchantmentDurability.negateDamage(stack, i, rand)) {
						++j;
					}
				}

				amount -= j;

				if (amount <= 0) {
					return false;
				}
			}

			setDamage(stack, getDamage(stack) + amount); // Redirect through
															// Item's
															// callback if
															// applicable.
			return getDamage(stack) > getMaxDamage(stack);
		}
	}

	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
		return false;
	}

}

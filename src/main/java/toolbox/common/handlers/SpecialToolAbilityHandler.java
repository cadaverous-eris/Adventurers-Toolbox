package toolbox.common.handlers;

import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.common.items.ItemBase;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.ItemATAxe;
import toolbox.common.items.tools.ItemATDagger;
import toolbox.common.items.tools.ItemATHammer;
import toolbox.common.items.tools.ItemATHandpick;
import toolbox.common.items.tools.ItemATHoe;
import toolbox.common.items.tools.ItemATMace;
import toolbox.common.items.tools.ItemATPickaxe;
import toolbox.common.items.tools.ItemATShovel;
import toolbox.common.items.tools.ItemATSword;
import toolbox.common.materials.ModMaterials;

public class SpecialToolAbilityHandler {

	private Random rand = new Random();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		if (event.getItemStack() != null && event.getEntityPlayer() != null) {
			ItemStack stack = event.getItemStack();
			Item item = stack.getItem();
			if (item instanceof ItemATAxe || item instanceof ItemATDagger || item instanceof ItemATHammer
					|| item instanceof ItemATHandpick || item instanceof ItemATHoe || item instanceof ItemATMace
					|| item instanceof ItemATPickaxe || item instanceof ItemATShovel || item instanceof ItemATSword) {
				boolean shift = GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak);
				boolean advanced = event.getFlags().isAdvanced();
				List<String> tooltip = event.getToolTip();

				tooltip.clear();

				String s = stack.getDisplayName();
				s = s + TextFormatting.RESET;

				if (advanced) {
					String s1 = "";

					if (!s.isEmpty()) {
						s = s + " (";
						s1 = ")";
					}

					int i = Item.getIdFromItem(item);

					if (stack.getHasSubtypes()) {
						s = s + String.format("#%04d/%d%s", i, stack.getItemDamage(), s1);
					} else {
						s = s + String.format("#%04d%s", i, s1);
					}
				}
				tooltip.add(s);
				int i1 = 0;

				if (stack.hasTagCompound() && stack.getTagCompound().hasKey("HideFlags", 99)) {
					i1 = stack.getTagCompound().getInteger("HideFlags");
				}

				if ((i1 & 32) == 0) {
					item.addInformation(stack, event.getEntityPlayer() == null ? null : event.getEntityPlayer().world,
							tooltip, event.getFlags());
				}

				if (stack.hasTagCompound()) {
					if ((i1 & 1) == 0) {
						NBTTagList nbttaglist = stack.getEnchantmentTagList();

						for (int j = 0; j < nbttaglist.tagCount(); ++j) {
							NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
							int k = nbttagcompound.getShort("id");
							int l = nbttagcompound.getShort("lvl");
							Enchantment enchantment = Enchantment.getEnchantmentByID(k);

							if (enchantment != null) {
								tooltip.add(enchantment.getTranslatedName(l));
							}
						}
					}

					if (stack.getTagCompound().hasKey("display", 10)) {
						NBTTagCompound nbttagcompound1 = stack.getTagCompound().getCompoundTag("display");

						if (nbttagcompound1.getTagId("Lore") == 9) {
							NBTTagList nbttaglist3 = nbttagcompound1.getTagList("Lore", 8);

							if (!nbttaglist3.hasNoTags()) {
								for (int l1 = 0; l1 < nbttaglist3.tagCount(); ++l1) {
									tooltip.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC
											+ nbttaglist3.getStringTagAt(l1));
								}
							}
						}
					}
				}

				Multimap<String, AttributeModifier> multimap = stack
						.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
				if ((i1 & 2) == 0) {
					tooltip.add("");

					if (item instanceof ItemATPickaxe || item instanceof ItemATHandpick || item instanceof ItemATHammer
							|| item instanceof ItemATAxe || item instanceof ItemATShovel) {
						int harvestLvl = -1;
						float efficiency = 0F;
						if (item instanceof ItemATPickaxe) {
							harvestLvl = ((ItemATPickaxe) item).getHarvestLevel(stack);
							efficiency = ((ItemATPickaxe) item).getEfficiency(stack);
						} else if (item instanceof ItemATHandpick) {
							harvestLvl = ((ItemATHandpick) item).getHarvestLevel(stack);
							efficiency = ((ItemATHandpick) item).getEfficiency(stack);
						} else if (item instanceof ItemATHammer) {
							harvestLvl = ((ItemATHammer) item).getHarvestLevel(stack);
							efficiency = ((ItemATHammer) item).getEfficiency(stack);
						} else if (item instanceof ItemATAxe) {
							harvestLvl = ((ItemATAxe) item).getHarvestLevel(stack);
							efficiency = ((ItemATAxe) item).getEfficiency(stack);
						} else if (item instanceof ItemATShovel) {
							harvestLvl = ((ItemATShovel) item).getHarvestLevel(stack);
							efficiency = ((ItemATShovel) item).getEfficiency(stack);
						}
						tooltip.add(I18n.translateToLocal("desc.harvest_level.name") + ": " + harvestLvl);
						tooltip.add(I18n.translateToLocal("desc.efficiency.name") + ": " + efficiency);
					}

					for (Entry<String, AttributeModifier> entry : multimap.entries()) {
						AttributeModifier attributemodifier = entry.getValue();
						double d0 = attributemodifier.getAmount();

						if (event.getEntityPlayer() != null && (shift || item instanceof ItemSword)) {
							if (attributemodifier.getID() == ItemBase.getAttackDamageUUID()) {
								d0 = d0 + event.getEntityPlayer()
										.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
								d0 = d0 + (double) EnchantmentHelper.getModifierForCreature(stack,
										EnumCreatureAttribute.UNDEFINED);

								double d1 = d0 * 100.0D;
								;
								if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
									d1 = d0;
								}

								tooltip.add(I18n.translateToLocal("desc.attack_damage.name") + ": "
										+ ItemStack.DECIMALFORMAT.format(d1));
							} else if (attributemodifier.getID() == ItemBase.getAttackSpeedUUID()) {
								d0 += event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED)
										.getBaseValue();

								double d1 = d0 * 100.0D;
								;
								if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
									d1 = d0;
								}

								tooltip.add(I18n.translateToLocal("desc.attack_speed.name") + ": "
										+ ItemStack.DECIMALFORMAT.format(d1));
							}
						}
						// tooltip.add(I18n.translateToLocalFormatted("attribute.modifier.equals."
						// + attributemodifier.getOperation(),
						// ItemStack.DECIMALFORMAT.format(d1),
						// I18n.translateToLocal("attribute.name." +
						// (String)entry.getKey())));
					}

					if (shift && stack.isItemEnchantable()) {
						tooltip.add(I18n.translateToLocal("desc.enchantability.name") + ": "
								+ item.getItemEnchantability(stack));
					}

					if (stack.isItemDamaged() && stack.isItemStackDamageable()) {
						tooltip.add(I18n.translateToLocal("desc.durability.name") + ": "
								+ (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
					} else if (stack.isItemStackDamageable()) {
						tooltip.add(I18n.translateToLocal("desc.durability.name") + ": " + stack.getMaxDamage());
					}
				}

				if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("Unbreakable") && (i1 & 4) == 0) {
					tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("item.unbreakable"));
				}

				if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CanDestroy", 9) && (i1 & 8) == 0) {
					NBTTagList nbttaglist1 = stack.getTagCompound().getTagList("CanDestroy", 8);

					if (!nbttaglist1.hasNoTags()) {
						tooltip.add("");
						tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("item.canBreak"));

						for (int j1 = 0; j1 < nbttaglist1.tagCount(); ++j1) {
							Block block = Block.getBlockFromName(nbttaglist1.getStringTagAt(j1));

							if (block != null) {
								tooltip.add(TextFormatting.DARK_GRAY + block.getLocalizedName());
							} else {
								tooltip.add(TextFormatting.DARK_GRAY + "missingno");
							}
						}
					}
				}

				if (advanced) {
					tooltip.add(TextFormatting.DARK_GRAY
							+ ((ResourceLocation) Item.REGISTRY.getNameForObject(item)).toString());
					tooltip.add(TextFormatting.DARK_GRAY + I18n.translateToLocalFormatted("item.nbt_tags",
							stack.getTagCompound().getKeySet().size()));
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEndRodToolHarvestEvent(BlockEvent.HarvestDropsEvent event) {
		if (event.getHarvester() == null) {
			return;
		}

		ItemStack stack = event.getHarvester().getHeldItemMainhand();

		boolean flag = false;

		if (stack.getItem() instanceof IHaftTool) {
			if (IHaftTool.getHaftMat(stack) == ModMaterials.HAFT_END_ROD) {
				flag = true;
			}
		}

		if (flag) {
			if (!event.getWorld().isRemote) {
				BlockPos pos = event.getPos();
				for (ItemStack itemstack : event.getDrops()) {
					if (rand.nextFloat() < event.getDropChance()) {
						EntityItem entity = new EntityItem(event.getWorld(), pos.getX() + 0.5, pos.getY() + 0.25,
								pos.getZ() + 0.5, itemstack);
						entity.setNoGravity(true);
						entity.motionX = 0;
						entity.motionY = 0;
						entity.motionZ = 0;
						entity.velocityChanged = true;
						event.getWorld().spawnEntity(entity);
					}
				}
			}
			event.getDrops().clear();
		}
	}

	@SubscribeEvent
	public void onBlazeRodToolRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();

		if (!event.getEntityPlayer().isSneaking()) {
			return;
		}

		boolean flag = false;

		if (stack.getItem() instanceof IHaftTool) {
			if (IHaftTool.getHaftMat(stack) == ModMaterials.HAFT_BLAZE_ROD) {
				flag = true;
			}
		}

		if (flag && event.getEntityPlayer().canPlayerEdit(event.getPos(), event.getFace(), stack)) {
			BlockPos pos = event.getPos().offset(event.getFace());

			if (event.getWorld().isAirBlock(pos)) {
				if (!event.getWorld().isRemote) {
					event.getWorld().setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
				}
				event.getWorld().playSound(event.getEntityPlayer(), pos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
						SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
				event.getEntityPlayer().swingArm(event.getHand());
			}
		}
	}

	@SubscribeEvent
	public void onPrismarineAdornedToolMine(PlayerEvent.BreakSpeed event) {

		if (event.getEntityPlayer() == null) {
			return;
		}

		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();

		if (stack.getItem() instanceof IAdornedTool
				&& IAdornedTool.getAdornmentMat(stack) == ModMaterials.ADORNMENT_PRISMARINE && player.isInWater()) {

			event.setNewSpeed(event.getOriginalSpeed() * 2F);

		}

	}

}

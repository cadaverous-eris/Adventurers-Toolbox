package toolbox.common.handlers;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.research.ResearchManager;
import toolbox.common.CommonProxy;
import toolbox.common.items.ItemBase;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.ICrossguardTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
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
	@SubscribeEvent(priority = EventPriority.HIGH)
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
				
				if (shift) {
					tooltip.add("");
					if (item instanceof IHeadTool) {
						HeadMaterial mat = IHeadTool.getHeadMat(stack);
						tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("desc.head.name") + ": " + I18n.translateToLocal("guide.mat." + mat.getName() + ".name"));
					}
					if (item instanceof IBladeTool) {
						HeadMaterial mat = IBladeTool.getBladeMat(stack);
						tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("desc.blade.name") + ": " + I18n.translateToLocal("guide.mat." + mat.getName() + ".name"));
					}
					if (item instanceof ICrossguardTool) {
						HeadMaterial mat = ICrossguardTool.getCrossguardMat(stack);
						tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("desc.crossguard.name") + ": " + I18n.translateToLocal("guide.mat." + mat.getName() + ".name"));
					}
					if (item instanceof IHaftTool) {
						HaftMaterial mat = IHaftTool.getHaftMat(stack);
						tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("desc.haft.name") + ": " + I18n.translateToLocal("guide.mat." + mat.getName() + ".name"));
					}
					if (item instanceof IHandleTool) {
						HandleMaterial mat = IHandleTool.getHandleMat(stack);
						tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("desc.handle.name") + ": " + I18n.translateToLocal("guide.mat." + mat.getName() + ".name"));
					}
					if (item instanceof IAdornedTool) {
						AdornmentMaterial mat = IAdornedTool.getAdornmentMat(stack);
						if (!mat.getName().equals("null")) {
							tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("desc.adornment.name") + ": " + I18n.translateToLocal("guide.mat." + mat.getName() + ".name"));
						}
					}
					tooltip.add("");
				}

				if (stack.hasTagCompound() && stack.getTagCompound().hasKey("HideFlags", 99)) {
					i1 = stack.getTagCompound().getInteger("HideFlags");
				}

				if ((i1 & 32) == 0) {
					item.addInformation(stack, event.getEntityPlayer() == null ? null : event.getEntityPlayer().world,
							tooltip, event.getFlags());
				}

				if (item instanceof ItemSword && isVoidTool(stack) && CommonProxy.thaumcraftLoaded) {
					tooltip.add(TextFormatting.GOLD + I18n.translateToLocal("enchantment.special.sapless"));
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
					if (!tooltip.get(tooltip.size() - 1).isEmpty()) {
						tooltip.add("");
					}

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
						tooltip.add(I18n.translateToLocal("desc.efficiency.name") + ": " + ItemStack.DECIMALFORMAT.format(efficiency));
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
					}

					if (shift) {
						tooltip.add(I18n.translateToLocal("desc.enchantability.name") + ": "
								+ item.getItemEnchantability(stack));
					}

					if (stack.isItemDamaged() && stack.isItemStackDamageable()) {
						tooltip.add(I18n.translateToLocal("desc.durability.name") + ": "
								+ (stack.getMaxDamage() - stack.getItemDamage()) + " / " + ItemStack.DECIMALFORMAT.format(stack.getMaxDamage()));
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
							stack.hasTagCompound() ? stack.getTagCompound().getKeySet().size() : 0));
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
	
	@SubscribeEvent
	public void onLapisToolEnchant(EnchantmentLevelSetEvent event) {
		ItemStack stack = event.getItem();

		if (stack.getItem() instanceof IAdornedTool
				&& IAdornedTool.getAdornmentMat(stack) == ModMaterials.ADORNMENT_LAPIS) {
			event.setLevel((int) (event.getLevel() * (5f / 3f)));
		}
	}
	
	@SubscribeEvent
	public void onLapisToolMine(BlockEvent.BreakEvent event) {
		if (event.getPlayer() == null) return;
		
		ItemStack stack = event.getPlayer().getHeldItemMainhand();

		if (stack.getItem() instanceof IAdornedTool
				&& IAdornedTool.getAdornmentMat(stack) == ModMaterials.ADORNMENT_LAPIS) {
			if (rand.nextInt(4) == 0) event.setExpToDrop(event.getExpToDrop() + event.getState().getBlock().getExpDrop(event.getState(), event.getWorld(), event.getPos(), 0));
		}
	}
	
	@SubscribeEvent
	public void onLapisToolKill(LivingExperienceDropEvent event) {
		if (event.getAttackingPlayer() == null) return;
		
		ItemStack stack = event.getAttackingPlayer().getHeldItemMainhand();

		if (stack.getItem() instanceof IAdornedTool
				&& IAdornedTool.getAdornmentMat(stack) == ModMaterials.ADORNMENT_LAPIS) {
			int exp = event.getDroppedExperience();
			if (exp > 0 && rand.nextBoolean()) event.setDroppedExperience(exp + 1 + rand.nextInt(exp + 1));
		}
	}
	
	private static final UUID BLOCK_REACH_MODIFIER = UUID.fromString("DF472D1A-4281-2854-D26C-BDBE9785CC93");
	private static final AttributeModifier enderPearlReachModifier = new AttributeModifier(BLOCK_REACH_MODIFIER, "ender pearl reach", 1D, 0);
	
	@SubscribeEvent
	public void onToolSwitch(LivingEquipmentChangeEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			if (event.getSlot() == EntityEquipmentSlot.MAINHAND) {
				ItemStack from = event.getFrom();
				ItemStack to = event.getTo();
				
				if (isEnderPearlTool(from)) {
					Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
					multimap.put(EntityPlayer.REACH_DISTANCE.getName(), enderPearlReachModifier);
					
					event.getEntityLiving().getAttributeMap().removeAttributeModifiers(multimap);
				}
				if (isEnderPearlTool(to)) {
					Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
					multimap.put(EntityPlayer.REACH_DISTANCE.getName(), enderPearlReachModifier);
					
					event.getEntityLiving().getAttributeMap().applyAttributeModifiers(multimap);
				}
			}
		}
	}
	
	private boolean isEnderPearlTool(ItemStack stack) {
		return (stack.getItem() instanceof IAdornedTool && IAdornedTool.getAdornmentMat(stack) == ModMaterials.ADORNMENT_ENDER_PEARL);
	}
	
	private boolean isVoidTool(ItemStack stack) {
		Item item = stack.getItem();
		
		if (item instanceof IHeadTool && IHeadTool.getHeadMat(stack) == ModMaterials.HEAD_VOID) return true;
		if (item instanceof IBladeTool && IBladeTool.getBladeMat(stack) == ModMaterials.HEAD_VOID) return true;
		if (item instanceof ICrossguardTool && ICrossguardTool.getCrossguardMat(stack) == ModMaterials.HEAD_VOID) return true;
		
		return false;
	}
	
	@SubscribeEvent
	public void onToolUpdate(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			
			if (player.ticksExisted % 20 == 0) {
				for (ItemStack stack : player.inventory.mainInventory) {
					if (stack.isItemDamaged() && isVoidTool(stack)) {
						stack.damageItem(-1, player);
					}
				}
				for (ItemStack stack : player.inventory.offHandInventory) {
					if (stack.isItemDamaged() && isVoidTool(stack)) {
						stack.damageItem(-1, player);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		Entity targetEntity = event.getTarget();
		
		ItemStack weapon = player.getHeldItemMainhand();
		
		if (targetEntity instanceof EntityLivingBase) {
			EntityLivingBase target = (EntityLivingBase) targetEntity;
			if (!player.world.isRemote && isVoidTool(weapon)) {
				target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, weapon.getItem() instanceof ItemSword ? 60 : 80));
			}
		}
	}
	
	@SubscribeEvent
	public void onWitheredBoneToolCrit(CriticalHitEvent event) {
		if (event.getEntityPlayer() == null || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase) || event.getResult() == Event.Result.DENY || (!event.isVanillaCritical() && event.getResult() != Event.Result.ALLOW)) return;
		
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
		
		int i = 0;
		if (stack.getItem() instanceof IHaftTool && IHaftTool.getHaftMat(stack) == ModMaterials.HAFT_WITHERED_BONE) i++;
		if (stack.getItem() instanceof IHandleTool && IHandleTool.getHandleMat(stack) == ModMaterials.HANDLE_WITHERED_BONE) i++;
		
		if (i > 0) {
			((EntityLivingBase) event.getTarget()).addPotionEffect(new PotionEffect(MobEffects.WITHER, 160, i - 1));
		}	
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onWitherBoneHarvestEvent(BlockEvent.HarvestDropsEvent event) {
		if (event.getHarvester() == null) {
			return;
		}

		EntityPlayer player = event.getHarvester();
		ItemStack stack = player.getHeldItemMainhand();

		boolean flag = false;

		if (stack.getItem() instanceof IHaftTool) {
			if (IHaftTool.getHaftMat(stack) == ModMaterials.HAFT_WITHER_BONE) {
				flag = true;
			}
		}

		if (flag) {
			if (player.dimension != DimensionType.NETHER.getId()) {
				stack.damageItem(7, player);
			}
		}
	}
	
	@SubscribeEvent
	public void onWitherBoneAttackEvent(AttackEntityEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = player.getHeldItemMainhand();
		
		boolean flag = false;
		
		if (stack.getItem() instanceof IHaftTool) {
			if (IHaftTool.getHaftMat(stack) == ModMaterials.HAFT_WITHER_BONE) {
				flag = true;
			}
		}

		if (flag) {
			if (player.dimension != DimensionType.NETHER.getId()) {
				stack.damageItem(7, player);
			}
		}
	}
}

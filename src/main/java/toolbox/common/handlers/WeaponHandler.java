package toolbox.common.handlers;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toolbox.common.items.ModItems;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.materials.ModMaterials;

public class WeaponHandler {
	
	@SubscribeEvent
	public void onMaceCrit(CriticalHitEvent event) {
		if (event.getEntityPlayer() == null || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase) || event.getResult() == Event.Result.DENY || (!event.isVanillaCritical() && event.getResult() != Event.Result.ALLOW)) return;
		
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
		EntityLivingBase target = (EntityLivingBase) event.getTarget();
		EntityPlayer player = event.getEntityPlayer();
		
		if (stack.getItem() == ModItems.mace) {
			int duration = 20 + target.getRNG().nextInt(20);
			
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, 4));
			target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, duration, 4));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, duration, 4));
			
			if (player.world instanceof WorldServer) {
				((WorldServer) player.world).spawnParticle(EnumParticleTypes.SPELL_INSTANT, target.posX, target.posY + (double) (target.height * 0.5F), target.posZ, 7 + target.getRNG().nextInt(3), 0.1D, 0.0D, 0.1D, 0.05D);
			}
		}	
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		Entity targetEntity = event.getTarget();
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.getItem() == ModItems.dagger || stack.getItem() == ModItems.mace) {
			event.setCanceled(true);
			if (stack.getItem() == ModItems.dagger && player.getDistance(targetEntity) > 2.5F) {
				return;
			}
			
			if (targetEntity.canBeAttackedWithItem()) {
				if (!targetEntity.hitByEntity(player)) {
					float f = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
					float f1;

					if (targetEntity instanceof EntityLivingBase) {
						f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((EntityLivingBase) targetEntity).getCreatureAttribute());
					} else {
						f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), EnumCreatureAttribute.UNDEFINED);
					}

					float f2 = player.getCooledAttackStrength(0.5F);
					f = f * (0.2F + f2 * f2 * 0.8F);
					f1 = f1 * f2;
					player.resetCooldown();

					if (f > 0.0F || f1 > 0.0F) {
						boolean flag = f2 > 0.9F;
						boolean flag1 = false;
						int i = 0;
						i = i + EnchantmentHelper.getKnockbackModifier(player);

						if (player.isSprinting() && flag) {
							player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, player.getSoundCategory(), 1.0F, 1.0F);
							++i;
							flag1 = true;
						}

						boolean flag2 = flag && player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(MobEffects.BLINDNESS) && !player.isRiding() && targetEntity instanceof EntityLivingBase;
						flag2 = flag2 && !player.isSprinting();

						net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
						flag2 = hitResult != null;
						if (flag2) {
							f *= hitResult.getDamageModifier();
						}

						f = f + f1;
						boolean flag3 = false;
						double d0 = (double) (player.distanceWalkedModified - player.prevDistanceWalkedModified);

						float f4 = 0.0F;
						boolean flag4 = false;
						int j = EnchantmentHelper.getFireAspectModifier(player);

						if (targetEntity instanceof EntityLivingBase) {
							f4 = ((EntityLivingBase) targetEntity).getHealth();

							if (j > 0 && !targetEntity.isBurning()) {
								flag4 = true;
								targetEntity.setFire(1);
							}
						}

						double d1 = targetEntity.motionX;
						double d2 = targetEntity.motionY;
						double d3 = targetEntity.motionZ;
						boolean flag5 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), f);

						if (flag5) {
							if (i > 0) {
								float f3 = player.getHeldItemMainhand().getItem() == ModItems.dagger ? 0.25F : 0.5F;
								if (targetEntity instanceof EntityLivingBase) {
									((EntityLivingBase) targetEntity).knockBack(player, (float) i * f3, (double) MathHelper.sin(player.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
								} else {
									targetEntity.addVelocity((double) (-MathHelper.sin(player.rotationYaw * 0.017453292F) * (float) i * f3), 0.1D, (double) (MathHelper.cos(player.rotationYaw * 0.017453292F) * (float) i * f3));
								}

								player.motionX *= 0.6D;
								player.motionZ *= 0.6D;
								player.setSprinting(false);
							}

							if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
								((EntityPlayerMP) targetEntity).connection.sendPacket(new SPacketEntityVelocity(targetEntity));
								targetEntity.velocityChanged = false;
								targetEntity.motionX = d1;
								targetEntity.motionY = d2;
								targetEntity.motionZ = d3;
							}

							if (flag2) {
								player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
								player.onCriticalHit(targetEntity);
							}

							if (!flag2 && !flag3) {
								if (flag) {
									player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, player.getSoundCategory(), 1.0F, 1.0F);
								} else {
									player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, player.getSoundCategory(), 1.0F, 1.0F);
								}
							}

							if (f1 > 0.0F) {
								player.onEnchantmentCritical(targetEntity);
							}

							player.setLastAttackedEntity(targetEntity);

							if (targetEntity instanceof EntityLivingBase) {
								EnchantmentHelper.applyThornEnchantments((EntityLivingBase) targetEntity, player);
							}

							EnchantmentHelper.applyArthropodEnchantments(player, targetEntity);
							ItemStack itemstack1 = player.getHeldItemMainhand();
							Entity entity = targetEntity;

							if (targetEntity instanceof MultiPartEntityPart) {
								IEntityMultiPart ientitymultipart = ((MultiPartEntityPart) targetEntity).parent;

								if (ientitymultipart instanceof EntityLivingBase) {
									entity = (EntityLivingBase) ientitymultipart;
								}
							}

							if (!itemstack1.isEmpty() && entity instanceof EntityLivingBase) {
								ItemStack beforeHitCopy = itemstack1.copy();
								itemstack1.hitEntity((EntityLivingBase) entity, player);

								if (itemstack1.isEmpty()) {
									net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, beforeHitCopy, EnumHand.MAIN_HAND);
									player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
								}
							}

							if (targetEntity instanceof EntityLivingBase) {
								float f5 = f4 - ((EntityLivingBase) targetEntity).getHealth();
								player.addStat(StatList.DAMAGE_DEALT, Math.round(f5 * 10.0F));

								if (j > 0) {
									targetEntity.setFire(j * 4);
								}

								if (player.world instanceof WorldServer && f5 > 2.0F) {
									int k = (int) ((double) f5 * 0.5D);
									((WorldServer) player.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.posX, targetEntity.posY + (double) (targetEntity.height * 0.5F), targetEntity.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D);
								}
							}

							player.addExhaustion(0.1F);
						} else {
							player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory(), 1.0F, 1.0F);

							if (flag4) {
								targetEntity.extinguish();
							}
						}
					}
				}
			}
		}
	}
}
